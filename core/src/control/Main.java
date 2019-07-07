package control;

import model.cards.Card;
import model.other.Account;

public class Main extends Thread{

    @Override
    public void run(){
        Card.scanAllCards();
        HandlerType handler = new AccountHandler().handleCommands();
        while (handler != null) {
            switch (handler) {
                case MENU:
                    handler = new MenuHandler().handleCommands();
                    break;
                case ACCOUNT:
                    Account.updateAccount();
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
                case CUSTOM:
                    handler = new CustomCardHandler().handleCommands();
                    break;
            }
        }
    }
}
