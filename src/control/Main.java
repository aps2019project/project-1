package control;

import model.cards.Card;
import model.other.Account;

public class Main {

    public static void main(String[] args) {
        Card.scanAllCards();
        Account.readAccountDetails();
        HandlerType handler = new AccountHandler().handleCommands();
        while (handler != null) {
            Account.saveAccountDetails();
            switch (handler) {
                case MENU:
                    handler = new MenuHandler().handleCommands();
                    Account.readAccountDetails();
                    break;
                case ACCOUNT:
                    handler = new AccountHandler().handleCommands();
                    Account.readAccountDetails();
                    break;
                case BATTLE:
                    handler = new BattleMenuHandler().handleCommands();
                    break;
                case COLLECTION:
                    handler = new CollectionHandler().handleCommands();
                    Account.readAccountDetails();
                    break;
                case SHOP:
                    handler = new ShopHandler().handleCommands();
                    Account.readAccountDetails();
                    break;
            }
        }
    }
}
