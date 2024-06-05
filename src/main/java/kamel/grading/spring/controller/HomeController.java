package kamel.grading.spring.controller;

import kamel.grading.spring.model.user.UserRole;
import kamel.grading.spring.service.HomeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.NameNotFoundException;

@Controller
@RequestMapping("/dashboard")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails) throws NameNotFoundException {
        int userId = Integer.parseInt(userDetails.getUsername());
        UserRole role = homeService.getUser(userId).getRole();
        switch (role) {
            case ADMIN -> {
                return  "redirect:/admin_dashboard/" + userId;
            }
            case INSTRUCTOR -> {
                return "redirect:/instructor_dashboard/" + userId;
            }
            case STUDENT -> {
                return "redirect:/student_dashboard/" + userId;
            }
            default -> {
                return "error";
            }
        }
    }
}
