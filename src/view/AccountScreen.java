package view;

import model.other.Account;

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

    public static void showAccountDetail(Account account, int number) {
        System.out.printf("%d-\tUserName: %s -\t Wins: %d\n", number, account.getUsername(), account.getWonGames());
    }

    public static void showLogoutConfirm() {
        System.out.println("logout complete...");
    }

    public static void showHelpMenu(){
        System.out.println("create account [username]:\t creating new account with username -> [username]");
        System.out.println("login [username]:\t login account with username -> [username]");
        System.out.println("showleaderboard:\t view all accounts in order (wins)");
        System.out.println("logout:\t logout current account");
    }

    public static void showWrongCommand() {
        System.out.println("Wrong command... try again");
    }
}
