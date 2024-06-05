package kamel.grading.spring.data.course;


import kamel.grading.spring.model.course.Course;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public interface CoursesDao {
    Course findById(int id) throws NoSuchElementException, SQLException;
    List<Course> findByInstructorId(int id) throws NoSuchElementException, SQLException;
    List<Course> findAll() throws SQLException;
    boolean insertCourse(Course course) throws SQLException;
}
