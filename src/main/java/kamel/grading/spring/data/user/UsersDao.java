package kamel.grading.spring.data.user;


import kamel.grading.spring.model.user.User;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public interface UsersDao {
    User findById(int id) throws NoSuchElementException, SQLException;
    User findByEmail(String email) throws NoSuchElementException, SQLException;
    List<User> findAll() throws SQLException;
    boolean insertUser(User user) throws SQLException;
}
