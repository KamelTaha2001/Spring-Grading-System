package kamel.grading.spring.model.user;


import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private UserRole role;
    private String password;

    public User(int id, String email, String name, String password, UserRole role) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.role = role;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPassword() { return password; }

    @Override
    public String toString() {
        return id + ": " + name + " (" + role.name() + ")";
    }
}
