package control;

import model.other.Account;
import view.CollectionScreen;
import view.MenuScreen;

public class MenuHandler extends Handler {

    MenuHandler() {
        MenuScreen.showWelcomeLine(Account.getCurrentAccount().getUsername());
        MenuScreen.options();
        handleCommands();
    }

    @Override
    public void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            if (command.matches("enter collection")) {
                new CollectionHandler();
            } else if (command.matches("enter shop")) {
                new ShopHandler();
            } else if (command.matches("enter battle")) {
                new BattleHandler();
            } else if (command.matches("exit")) {
                Account.setCurrentAccount(null);
                new AccountHandler();
            } else if (command.matches("help")) {
                MenuScreen.options();
            } else {
                MenuScreen.invalidCommand();
                MenuScreen.options();
            }
        }
    }
}
