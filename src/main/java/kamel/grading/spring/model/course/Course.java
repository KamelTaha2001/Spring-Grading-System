package kamel.grading.spring.model.course;

public class Course {
    private int id;
    private String name;
    private int instructorId;

    public Course(int id, String name, int instructorId) {
        this.id = id;
        this.name = name;
        this.instructorId = instructorId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }


    @Override
    public String toString() {
        return id + ": " + name;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
}
