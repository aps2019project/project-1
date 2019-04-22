package view;

public class AccountScreen extends Screen {

    public static void showAccountCreationError() {
        System.out.println("the account already exists. Try again");
    }

    public static void showScanPassword() {
        System.out.println("Enter password");
    }

    public static void showWrongPassword() {
        System.out.println("Wrong Password. Try Again");
    }

    public static void showWrongUsername() {
        System.out.println("Can't find account with this username");
    }

}
