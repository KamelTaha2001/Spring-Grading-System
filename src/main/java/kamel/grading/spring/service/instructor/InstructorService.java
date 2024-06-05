package kamel.grading.spring.service.instructor;

import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.grade.Grade;
import kamel.grading.spring.model.grade.GradeDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface InstructorService {
    List<Course> getCourses(int instructorId);
    List<Grade> getCourseGrades(int courseID);
    boolean editGrade(GradeDTO grade);
}
