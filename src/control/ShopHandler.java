package control;

import model.other.Account;
import model.other.Shop;

import java.util.regex.*;

class ShopHandler extends Handler {
    private static final ShopHandler shopHandler = new ShopHandler();
    private Shop shop = Shop.getInstance();
    private Account account = Account.getCurrentAccount();

    public static ShopHandler getInstance(){
        return shopHandler;
    }

    private ShopHandler(){}


    @Override
    void handleCommands() {
        Matcher matcher;
        while (scanner.hasNext()) {
            String command = scanner.nextLine().toLowerCase().trim();
            if (command.matches("exit")) {
                MenuHandler.getInstance().handleCommands();
            } else if (command.matches("show collection")) {

            } else if (command.matches("show")) {

            } else if (command.matches("help")) {
                help();
            } else if ((matcher = Pattern.compile("search (\\w+)").matcher(command)).matches()) {
                search(matcher.group(1));
            } else if ((matcher = Pattern.compile("search collection (\\w+)").matcher(command)).matches()) {
                searchCollection(matcher.group(1));
            } else if ((matcher = Pattern.compile("buy (\\w+)").matcher(command)).matches()) {
                buy(matcher.group(1));
            } else if ((matcher = Pattern.compile("sell (\\w+)").matcher(command)).matches()) {
                sell(matcher.group(1));
            }
        }
    }

    void help() {
        System.out.println("Exit\nShow Collection\nShow\nHelp\nSearch Card Name\n" +
                "Search collection Card Name\nBuy Card Name\n Sell Card ID");
    }

    void search(String name){

    }

    void searchCollection(String name){

    }

    void buy(String name){

    }

    void sell(String name){

    }
}