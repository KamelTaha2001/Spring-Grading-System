package kamel.grading.spring.service;


import kamel.grading.spring.data.user.UsersDao;
import kamel.grading.spring.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;

@Service
public class HomeService {

    private final UsersDao usersDao;

    public HomeService(@Qualifier("JdbcUsersDao") UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public User getUser(int userId) throws NameNotFoundException {
        try {
            return usersDao.findById(userId);
        } catch (Exception e) {
            throw new NameNotFoundException("User not found!");
        }
    }
}
