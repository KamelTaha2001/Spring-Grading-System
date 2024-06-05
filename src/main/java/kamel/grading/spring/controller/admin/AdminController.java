package kamel.grading.spring.controller.admin;

import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.user.User;
import kamel.grading.spring.service.admin.AdminService;
import kamel.util.URLUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin_dashboard")
public class AdminController {

    private final AdminService service;

    public AdminController(@Qualifier("AdminServiceImpl") AdminService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public String showInstructorDashboard(@PathVariable("id") int userId, Model model) {
        model.addAttribute("userId", userId);
        List<User> users = service.getUsers();
        Iterable<Course> courses = service.getCourses();
        model.addAttribute("users", users);
        model.addAttribute("courses", courses);
        return "admin_dashboard";
    }
}
