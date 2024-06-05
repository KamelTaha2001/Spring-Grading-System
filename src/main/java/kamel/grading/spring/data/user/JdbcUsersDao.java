package kamel.grading.spring.data.user;


import kamel.grading.spring.model.user.User;
import kamel.grading.spring.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;

@Component("JdbcUsersDao")
public class JdbcUsersDao implements UsersDao{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userMapper = (rs, row) -> {
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String role = rs.getString("role");
            return new User(id, email, name, password, UserRole.valueOf(role));
    };

    @Autowired
    public JdbcUsersDao(JdbcTemplate template) {
        jdbcTemplate = template;
    }

    @Override
    public User findById(int userId) throws NoSuchElementException, SQLException {
        try {
            String sql = "SELECT * FROM USERS WHERE id=?";
            List<User> users = jdbcTemplate.query(sql, s -> s.setInt(1, userId), userMapper);
            if (users.isEmpty()) throw new SQLException();
            return users.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public User findByEmail(String userEmail) throws NoSuchElementException, SQLException {
        String sql = "SELECT * FROM USERS WHERE email=?";
        List<User> users = jdbcTemplate.query(sql, s -> s.setString(1, userEmail), userMapper);
        if (users.isEmpty()) throw new SQLException();
        return users.get(0);
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS ORDER BY role";
        return jdbcTemplate.query(sql, userMapper);
    }

    @Override
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO USERS (email, name, password, role) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql, statement -> {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            statement.setString(4, user.getRole().toString());
        }) > 0;
    }
}
