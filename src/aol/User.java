package aol;

public abstract class User {
    protected String email;
    protected String role;

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public abstract void showMenu();
}
