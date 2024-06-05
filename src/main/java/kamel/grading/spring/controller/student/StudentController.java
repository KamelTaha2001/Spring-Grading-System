package kamel.grading.spring.controller.student;

import kamel.grading.spring.model.grade.Grade;
import kamel.grading.spring.model.grade.GradeDTO;
import kamel.grading.spring.service.student.StudentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student_dashboard")
public class StudentController {

    private final StudentService service;

    public StudentController(
            @Qualifier("StudentServiceImpl") StudentService service
    ) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public String showStudentDashboard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") int userId, Model model) {
        int id = Integer.parseInt(userDetails.getUsername());
        List<Grade> grades = service.getGrades(id);
        double average = service.getAverage(
                grades.stream().map(g -> g.getDto().getGrade()).toList()
        );
        model.addAttribute("grades", grades);
        model.addAttribute("average", average);
        return "student_dashboard";
    }


}
