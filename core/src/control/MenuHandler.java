package control;

import model.other.Account;
import view.MenuScreen;

import static control.HandlerType.*;

public class MenuHandler extends Handler {

    MenuHandler() {
        MenuScreen.showWelcomeLine(Account.getCurrentAccount().getUsername());
        MenuScreen.options();
    }

    @Override
    public HandlerType handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            if (command.matches("enter collection")) {
                return COLLECTION;
            } else if (command.matches("enter shop")) {
                return SHOP;
            } else if (command.matches("enter battle")) {
                return BATTLE;
            } else if(command.matches("create custom card")){
                return CUSTOM;
            }
            else if (command.matches("exit")) {
                Account.setCurrentAccount(null);
                return ACCOUNT;
            } else if (command.matches("help")) {
                MenuScreen.options();
            } else {
                MenuScreen.invalidCommand();
                MenuScreen.options();
            }
        }
        return null;
    }
}
