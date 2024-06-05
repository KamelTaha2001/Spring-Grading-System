package kamel.grading.spring.controller.instructor;

import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.grade.Grade;
import kamel.grading.spring.model.user.User;
import kamel.grading.spring.service.instructor.InstructorService;
import kamel.util.URLUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/instructor_dashboard")
public class InstructorController {

    private final InstructorService service;
    private final RestTemplate restTemplate;

    public InstructorController(
            @Qualifier("InstructorServiceImpl") InstructorService service,
            RestTemplate restTemplate
    ) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public String showInstructorDashboard(@PathVariable("id") int userId, Model model) {
        Iterable<Course> courses = service.getCourses(userId);
        model.addAttribute("courses", courses);
        return "instructor_dashboard";
    }

    @PostMapping("/courses")
    public String openCourseDetails(@RequestParam("courseId") int courseId, Model model) {
        return showCourseDetails(courseId, model);
    }

    @GetMapping("/courses/{id}")
    public String showCourseDetails(@PathVariable("id") int id, Model model) {
        List<Grade> grades = service.getCourseGrades(id);
        model.addAttribute("courseId", id);
        model.addAttribute("grades", grades);
        return "course_details";
    }
}
