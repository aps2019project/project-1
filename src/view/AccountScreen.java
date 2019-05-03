package view;

import model.other.Account;

public class AccountScreen extends Screen {

    public static void showAccountCreationError() {
        System.out.println("This account already exists. Try with another username.");
    }

    public static void showScanPassword() {
        System.out.println("Enter password **Note that password is case sensitive** ");
    }

    public static void showWrongPassword() {
        System.out.println("Wrong Password. Try Again");
    }

    public static void showConfirmPassword() {
        System.out.println("Confirm password: (enter your password again)");
    }

    public static void showConfirmPasswordFail() {
        System.out.println("Passwords you entered are not equals. try again later.");
    }

    public static void showWrongUsername() {
        System.out.println("Can't this username");
    }

    public static void showAccountDetail(Account account, int number) {
        System.out.printf("%d-\tUserName: [\"%s\"] \t Wins: %d\n", number, account.getUsername(), account.getWonGames());
    }

    public static void showLogoutConfirm() {
        System.out.println("logout complete...");
    }

    public static void showHelpMenu(){
        System.out.println("create account [username]:    creating new account with username -> [username]");
        System.out.println("login [username]:             login account with username -> [username]");
        System.out.println("show leaderboard:             view all accounts in order (wins)");
        System.out.println("logout:                       logout current account");
    }

    public static void showWrongCommand() {
        System.out.println("Invalid command. try again");
    }

    public static void showWelcomeLine() {
        System.out.println("Welcome to Duelyst...");
        System.out.println("Select or Create account to continue.");
    }
}
