package aol;

public class Admin extends User {

    public Admin(String email) {
        super(email, "ADMIN");
    }

    @Override
    public void showMenu() {
        new AdminPage().admhomepg();
    }
}
