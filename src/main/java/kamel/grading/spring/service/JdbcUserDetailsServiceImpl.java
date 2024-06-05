package kamel.grading.spring.service;

import kamel.grading.spring.data.user.UsersDao;
import kamel.grading.spring.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDetailsServiceImpl implements UserDetailsService {

    private final JdbcTemplate template;

    @Autowired
    @Qualifier("JdbcUsersDao")
    private UsersDao usersDao;

    public JdbcUserDetailsServiceImpl(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        user = getUser(username);
        return user;
    }

    private User getUser(String email) throws UsernameNotFoundException {
        String sql = "SELECT id, password, role FROM USERS WHERE email=?";
        List<User> users = template.query(sql, statement -> statement.setString(1, email),
                (rs, rowNum) -> {
                    String id = String.valueOf(rs.getInt("id"));
                    return new User(
                            id,
                            rs.getString("password"),
                            true,
                            true,
                            true,
                            true,
                            getAuthority(UserRole.valueOf(rs.getString("role"))));
                });

        if (users.isEmpty()) throw new UsernameNotFoundException("Not found!");
        return users.get(0);
    }

    private List<SimpleGrantedAuthority> getAuthority(UserRole type) {
        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(type.toString()));
        return auth;
    }
}
