package kamel.grading.spring.controller.admin;

import kamel.grading.spring.model.course.Course;
import kamel.grading.spring.model.user.User;
import kamel.grading.spring.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin_dashboard")
public class AdminRESTController {

    private final AdminService service;

    public AdminRESTController(@Qualifier("AdminServiceImpl") AdminService service) {
        this.service = service;
    }

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody User request) {
        System.out.println(request.getRole());
        User user = new User(request.getId(), request.getEmail(), request.getName(), request.getPassword(), request.getRole());
        try {
            service.createUser(user);
            return ResponseEntity.ok("User created!");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user!");
        }
    }

    @PostMapping("/create_course")
    public ResponseEntity<String> createCourse(@RequestBody Map<String, String> request) {
        String courseName = request.get("name");
        int instructorId = Integer.parseInt(request.get("instructorId"));
        try {
            boolean success = service.createCourse(new Course(-1, courseName, instructorId));
            if (success)
                return ResponseEntity.ok("Course created!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create course!");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create course!");
        }
    }

    @PostMapping("/add_student_to_course")
    public ResponseEntity<String> addStudentToCourse(@RequestBody Map<String, String> request) {
        int studentId = Integer.parseInt(request.get("studentId"));
        int instructorId = Integer.parseInt(request.get("courseId"));

        try {
            boolean success = service.addStudentToCourse(studentId, instructorId);
            if (success)
                return ResponseEntity.ok("Student added!");
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student!");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student!");
        }
    }
}
