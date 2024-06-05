package kamel.grading.spring.service.instructor;

import kamel.grading.spring.data.course.CoursesDao;
import kamel.grading.spring.data.grade.GradesDao;
import kamel.grading.spring.data.user.UsersDao;
import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.grade.Grade;
import kamel.grading.spring.model.grade.GradeDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Service("InstructorServiceImpl")
public class InstructorServiceImpl implements InstructorService {

    private final CoursesDao coursesDao;
    private final GradesDao gradesDao;
    private final UsersDao usersDao;

    public InstructorServiceImpl(
            @Qualifier("JdbcUsersDao") UsersDao usersDao,
            @Qualifier("JdbcCoursesDao") CoursesDao coursesDao,
            @Qualifier("JdbcGradesDao") GradesDao gradesDao
    ) {
        this.coursesDao = coursesDao;
        this.gradesDao = gradesDao;
        this.usersDao = usersDao;
    }

    @Override
    public List<Course> getCourses(int instructorId) {
        List<Course> courses = new LinkedList<>();
        try {
            courses.addAll(coursesDao.findByInstructorId(instructorId));
        } catch (SQLException e) {
            courses.clear();
        }
        return courses;
    }

    @Override
    public List<Grade> getCourseGrades(int courseID) {
        List<Grade> grades = new LinkedList<>();
        try {
            grades.addAll(
                    gradesDao.getGradesForCourse(courseID).stream()
                            .map(g -> {
                                try {
                                    String courseName = coursesDao.findById(courseID).getName();
                                    String studentName = usersDao.findById(g.getStudentId()).getName();
                                    return new Grade(courseName, studentName, g);
                                } catch (Exception e) {
                                    return new Grade("N/A", "N/A", g);
                                }
                            })
                            .toList()
            );
        } catch (SQLException e) {
            grades.clear();
        }
        return grades;
    }

    @Override
    public boolean editGrade(GradeDTO grade) {
        try {
            return gradesDao.editGrade(grade);
        } catch (SQLException e) {
            return false;
        }
    }
}
