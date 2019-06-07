package control;

import model.other.Account;
import view.MenuScreen;

import static control.HandlerType.*;

public class CustomCardHandler extends Handler {
    CustomCardHandler() {
        MenuScreen.showWelcomeLine(Account.getCurrentAccount().getUsername());
        MenuScreen.options();
    }

    @Override
    public HandlerType handleCommands() {

        return null;
    }
}
