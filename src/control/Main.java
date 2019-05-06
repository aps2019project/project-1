package control;

import model.cards.Card;
import model.other.Account;

public class Main {

    public static void main(String[] args) {
        Card.scanAllCards();
        Account.readAccountDetails();
        HandlerType handler = new AccountHandler().handleCommands();
        while (handler != null) {
            switch (handler) {
                case MENU:
                    handler = new MenuHandler().handleCommands();
                    break;
                case ACCOUNT:
                    Account.saveAccountDetails();
                    handler = new AccountHandler().handleCommands();
                    break;
                case BATTLE:
                    handler = new BattleMenuHandler().handleCommands();
                    break;
                case COLLECTION:
                    handler = new CollectionHandler().handleCommands();
                    break;
                case SHOP:
                    handler = new ShopHandler().handleCommands();
                    break;
            }
        }
    }
}
