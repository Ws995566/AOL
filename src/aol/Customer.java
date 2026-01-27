package aol;

public class Customer extends User {

    public Customer(String email) {
        super(email, "CUSTOMER");
    }

    @Override
    public void showMenu() {
        new CustPage().custhomepg();
    }
}
