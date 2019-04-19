package view;

public class AccountScreen extends Screen {

    public static void showAccountCreationError() {
        System.out.println("the account already exists. Try again");
    }

    public static void showScanPassword() {
        System.out.println("Enter password");
    }

}
