package kamel.grading.spring.controller.instructor;

import kamel.grading.spring.model.grade.GradeDTO;
import kamel.grading.spring.service.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor_dashboard")
public class InstructorRESTController {

    private final InstructorService service;

    public InstructorRESTController(@Qualifier("InstructorServiceImpl") InstructorService service) {
        this.service = service;
    }

    @PostMapping("/edit_grade")
    public ResponseEntity<String> editGrade(@RequestBody GradeDTO request) {
        GradeDTO grade = new GradeDTO(
                request.getCourseId(),
                request.getStudentId(),
                request.getGrade()
        );
        service.editGrade(grade);
        return ResponseEntity.ok("Done");
    }
}
