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

    public static void showWrongCommand() {
        System.out.println("Invalid command. try again");
    }

    public static void showWelcomeLine() {
        System.out.println("\t\t__________Welcome to Duelyst_________");
        System.out.println("\t\tSelect or Create account to continue.");
    }

    public static void showHelpMenu(){
        System.out.println(" Things you can do here ______________________________");
        System.out.println("|                                                     |");
        System.out.println("| Create account [username]:    *to creat new account |");
        System.out.println("| Login [username]:             *to login account     |");
        System.out.println("| Show LeaderBoard:             *to view all accounts |");
        System.out.println("| Help:                         *to see this menu     |");
        System.out.println("| Exit:                         *to terminate game    |");
        System.out.println("|_____________________________________________________|");
    }
}
