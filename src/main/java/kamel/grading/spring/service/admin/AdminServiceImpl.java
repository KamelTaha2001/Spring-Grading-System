package kamel.grading.spring.service.admin;

import kamel.grading.spring.data.course.CoursesDao;
import kamel.grading.spring.data.grade.GradesDao;
import kamel.grading.spring.data.user.UsersDao;
import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.grade.GradeDTO;
import kamel.grading.spring.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Service("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {

    private final UsersDao usersDao;
    private final CoursesDao coursesDao;
    private final GradesDao gradesDao;

    public AdminServiceImpl(
            @Qualifier("JdbcUsersDao") UsersDao usersDao,
            @Qualifier("JdbcCoursesDao") CoursesDao coursesDao,
            @Qualifier("JdbcGradesDao") GradesDao gradesDao
    ) {
        this.usersDao = usersDao;
        this.coursesDao = coursesDao;
        this.gradesDao = gradesDao;
    }

    @Override
    public List<User> getUsers() {
        try {
            return usersDao.findAll();
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public List<Course> getCourses() {
        try {
            return coursesDao.findAll();
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public boolean createCourse(Course course) {
        try {
            return coursesDao.insertCourse(course);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean createUser(User user) {
        try {
            return usersDao.insertUser(user);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean addStudentToCourse(int studentId, int courseId) {
        try {
            return gradesDao.insertGrade(new GradeDTO(courseId, studentId, 0));
        } catch (SQLException e) {
            return false;
        }
    }
}
