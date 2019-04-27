package control;

import model.other.Account;
import model.other.Shop;
import model.variables.ID;
import view.ShopScreen;

import java.util.regex.*;

class ShopHandler extends Handler {
    private static final ShopHandler shopHandler = new ShopHandler();
    private Shop shop = Shop.getInstance();
    private Account account = Account.getCurrentAccount();

    public static ShopHandler getInstance() {
        return shopHandler;
    }

    private ShopHandler() {
    }


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
                ShopScreen.options();
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

    private void search(String name) {
        ID id = shop.search(name);
        if (id != null){
            ShopScreen.showID(id.getValue());
        } else{
            ShopScreen.showNoCardWithThisName();
        }
    }

    private void searchCollection(String name) {

    }

    private void buy(String name) {

    }

    private void sell(String name) {

    }
}