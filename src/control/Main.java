package control;

import model.cards.Card;

public class Main {

    public static void main(String[] args) {
        Card.scanAllCards();
        HandlerType handler = new AccountHandler().handleCommands();
        while (handler != null) {
            switch (handler) {
                case MENU:
                    handler = new MenuHandler().handleCommands();
                    break;
                case ACCOUNT:
                    handler = new AccountHandler().handleCommands();
                    break;
                case BATTLE:
                    handler = new BattleHandler().handleCommands();
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
