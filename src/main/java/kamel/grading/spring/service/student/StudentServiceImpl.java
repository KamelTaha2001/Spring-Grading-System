package kamel.grading.spring.service.student;

import kamel.grading.spring.data.course.CoursesDao;
import kamel.grading.spring.data.grade.GradesDao;
import kamel.grading.spring.data.user.UsersDao;
import kamel.grading.spring.model.grade.Grade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

@Service("StudentServiceImpl")
public class StudentServiceImpl implements StudentService {

    private final GradesDao gradesDao;
    private final CoursesDao coursesDao;
    private final UsersDao usersDao;

    public StudentServiceImpl(
            @Qualifier("JdbcUsersDao") UsersDao usersDao,
            @Qualifier("JdbcCoursesDao") CoursesDao coursesDao,
            @Qualifier("JdbcGradesDao") GradesDao gradesDao
    ) {
        this.gradesDao = gradesDao;
        this.coursesDao = coursesDao;
        this.usersDao = usersDao;
    }

    @Override
    public List<Grade> getGrades(int studentId) {
        List<Grade> grades = new LinkedList<>();
        try {
            grades.addAll(
                    gradesDao.getGradesForStudent(studentId)
                    .stream()
                    .map(g -> {
                        try {
                            String courseName = coursesDao.findById(g.getCourseId()).getName();
                            String studentName = usersDao.findById(g.getStudentId()).getName();
                            return new Grade(courseName, studentName, g);
                        } catch (Exception e) {
                            return new Grade("N/A", "N/A", g);
                        }
                    }).toList());
        } catch (SQLException e) {
            grades.clear();
        }
        return grades;
    }

    @Override
    public double getAverage(List<Integer> grades) {
        OptionalDouble avg = grades.stream()
                .mapToInt(Integer::intValue)
                .average();
        if (avg.isEmpty()) return 0;
        return avg.getAsDouble();
    }
}
