package kamel.grading.spring.model.grade;

public class Grade {
    private String studentName;
    private String courseName;
    private GradeDTO dto;

    public Grade(String courseName, String studentName, GradeDTO dto) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.dto = dto;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return courseName + "| " + studentName + ":" + dto.getGrade();
    }

    public GradeDTO getDto() {
        return dto;
    }

    public void setDto(GradeDTO dto) {
        this.dto = dto;
    }
}
