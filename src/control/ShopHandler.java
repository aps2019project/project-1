package control;

import model.other.Account;
import model.other.Shop;
import model.variables.ID;
import view.ShopScreen;

import java.util.regex.*;

class ShopHandler extends Handler {

    private Account account = Account.getCurrentAccount();

    @Override
    void handleCommands() {
        Matcher matcher;
        while (scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            if (command.matches("exit")) {
                new MenuHandler();
            } else if (command.matches("show collection")) {
                ShopScreen.showCollection(Account.getCurrentAccount().getCollection());
            } else if (command.matches("show")) {

            } else if (command.matches("help")) {
                ShopScreen.showOptions();
            } else if (command.matches("search \\w+")) {
                search(command.split(" ")[1]);
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

    }

    private void searchCollection(String name) {

    }

    private void buy(String name) {

    }

    private void sell(String name) {

    }
}