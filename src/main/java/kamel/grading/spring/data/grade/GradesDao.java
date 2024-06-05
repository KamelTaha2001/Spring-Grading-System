package kamel.grading.spring.data.grade;


import kamel.grading.spring.model.grade.GradeDTO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface GradesDao {
    List<GradeDTO> getGradesForCourse(int courseId) throws SQLException;
    List<GradeDTO> getGradesForStudent(int studentId) throws SQLException;
    boolean insertGrade(GradeDTO grade) throws SQLException;
    boolean editGrade(GradeDTO grade) throws SQLException;
}
