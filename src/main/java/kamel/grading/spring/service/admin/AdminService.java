package kamel.grading.spring.service.admin;


import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<User> getUsers();
    List<Course> getCourses();
    boolean createCourse(Course course);
    boolean createUser(User user);
    boolean addStudentToCourse(int studentId, int courseId);
}
