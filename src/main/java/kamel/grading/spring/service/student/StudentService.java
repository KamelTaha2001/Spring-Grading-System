package kamel.grading.spring.service.student;

import kamel.grading.spring.model.grade.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Grade> getGrades(int studentId);
    double getAverage(List<Integer> grades);
}
