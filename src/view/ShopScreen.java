package view;

public class ShopScreen extends Screen {
    public static void options() {
        System.out.println("Exit\nShow Collection\nShow\nHelp\nSearch Card Name\n" +
                "Search collection Card Name\nBuy Card Name\nSell Card ID");
    }

    public static void showNoCardWithThisName(){
        System.out.println("There isn't any cards with this name in Shop");
    }

    public static void showID(String id){
        System.out.println("Card ID : " + id);
    }
}
