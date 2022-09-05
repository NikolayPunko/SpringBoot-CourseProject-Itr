package itransition.punko.CourseProject.models;


public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String label;

    UserRole(String role) {
        this.label = role;
    }

    public String getLabel() {
        return label;
    }
}
