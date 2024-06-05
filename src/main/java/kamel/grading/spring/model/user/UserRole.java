package kamel.grading.spring.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum UserRole implements Serializable {
    STUDENT("STUDENT"),
    INSTRUCTOR("INSTRUCTOR"),
    ADMIN("ADMIN");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @JsonValue
    public String getValue() {
        return this.name;
    }
}
