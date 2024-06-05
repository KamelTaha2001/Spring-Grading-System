package kamel.grading.spring.data.grade;


import kamel.grading.spring.model.grade.GradeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component("JdbcGradesDao")
public class JdbcGradesDao implements GradesDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<GradeDTO> gradeMapper = (rs, row) -> {
        int courseId = rs.getInt("courseId");
        int studentId = rs.getInt("studentId");
        int grade = rs.getInt("grade");
        return new GradeDTO(courseId, studentId, grade);
    };

    public JdbcGradesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GradeDTO> getGradesForCourse(int courseId) throws SQLException {
        String sql = "SELECT * FROM GRADES WHERE courseId=?";
        return jdbcTemplate.query(sql, s -> s.setInt(1, courseId), gradeMapper);
    }

    @Override
    public List<GradeDTO> getGradesForStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM GRADES WHERE studentId=?";
        return jdbcTemplate.query(sql, s -> s.setInt(1, studentId), gradeMapper);
    }

    @Override
    public boolean insertGrade(GradeDTO grade) throws SQLException {
        String sql = "INSERT INTO GRADES (courseId, studentId, grade) VALUES (?,?,?)";
        return jdbcTemplate.update(sql, statement -> {
            statement.setInt(1, grade.getCourseId());
            statement.setInt(2, grade.getStudentId());
            statement.setInt(3, grade.getGrade());
        }) > 0;
    }

    @Override
    public boolean editGrade(GradeDTO grade) throws SQLException {
        String sql = "UPDATE GRADES SET grade=? WHERE courseId=? and studentId=?";
        return jdbcTemplate.update(sql, statement -> {
            statement.setInt(1, grade.getGrade());
            statement.setInt(2, grade.getCourseId());
            statement.setInt(3, grade.getStudentId());
        }) > 0;
    }
}
