package kamel.grading.spring.data.course;


import kamel.grading.spring.model.course.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;

@Component("JdbcCoursesDao")
public class JdbcCoursesDao implements CoursesDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Course> courseMapper = (rs, row) -> {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int instructorId = rs.getInt("instructorId");
        return new Course(id, name, instructorId);
    };

    public JdbcCoursesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course findById(int id) throws NoSuchElementException, SQLException {
        String sql = "SELECT * FROM COURSES WHERE id=?";
        List<Course> courses = jdbcTemplate.query(sql, s -> s.setInt(1, id), courseMapper);
        if (courses.isEmpty()) throw new SQLException();
        return courses.get(0);
    }

    @Override
    public List<Course> findByInstructorId(int id) throws NoSuchElementException, SQLException {
        String sql = "SELECT * FROM COURSES WHERE instructorId=?";
        return jdbcTemplate.query(sql, s -> s.setInt(1, id), courseMapper);
    }

    @Override
    public List<Course> findAll() throws SQLException {
        String sql = "SELECT * FROM COURSES";
        return jdbcTemplate.query(sql, courseMapper);
    }

    @Override
    public boolean insertCourse(Course course) throws SQLException {
        String sql = "INSERT INTO COURSES (name, instructorId) VALUES (?,?)";
        return jdbcTemplate.update(sql, statement -> {
            statement.setString(1, course.getName());
            statement.setInt(2, course.getInstructorId());
        }) > 0;
    }
}
