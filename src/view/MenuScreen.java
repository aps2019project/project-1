package view;

public class MenuScreen extends Screen {

    public static void options() {
        System.out.println(" _______________________________________________________________________");
        System.out.println("|Things you can do...                                                   |");
        System.out.println("|  Enter Collection:       *to see and edit collection and manage decks |");
        System.out.println("|  Enter Shop:             *to buy or sell cards                        |");
        System.out.println("|  Enter Battle:           *to start a new game                         |");
        System.out.println("|  Create Custom Card:     *to create a card                            |");
        System.out.println("|  Help:                   *to see this menu                            |");
        System.out.println("|  Exit:                   *to logout                                   |");
        System.out.println("|_______________________________________________________________________|");
    }

    public static void invalidCommand(){
        System.out.println("Invalid command please try again");
    }

    public static void showWelcomeLine(String username) {
        System.out.println("Welcome to Main Menu...");
        System.out.println("Current Player: \"" + username + "\"");
    }
}
