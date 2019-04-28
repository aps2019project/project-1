package view;

public class MenuScreen extends Screen {

    public static void options() {
        System.out.println("Collection\nShop\nBattle\nSave\nLogout\nExit\nHelp");
    }

    public static void invalidCommand(){
        System.out.println("Invalid command please try again");
    }
}
