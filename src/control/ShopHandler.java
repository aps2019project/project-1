package control;

import model.cards.Card;
import model.cards.CardType;
import model.cards.Item;
import model.cards.ItemType;
import model.other.Account;
import model.other.Shop;
import model.variables.CardsArray;
import model.variables.ID;
import view.ShopScreen;

import java.util.regex.*;

class ShopHandler extends Handler {

    @Override
    void handleCommands() {
        Matcher matcher;
        while (scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            if (command.matches("exit")) {
                new MenuHandler();
            } else if (command.matches("show collection")) {
                ShopScreen.showCardArray(Account.getCurrentAccount().getCollection(), "Sell");
            } else if (command.matches("show")) {
                ShopScreen.showCardArray(Card.getCards(), "Buy");
            } else if (command.matches("search \\w+")) {
                search(Card.getCards(), command.split(" ")[1]);
            } else if (command.matches("search collection \\w+")) {
                search(Account.getCurrentAccount().getCollection(), command.split(" ")[2]);
            } else if (command.matches("buy \\w+")) {
                buy(command.split(" ")[1]);
            } else if ((matcher = Pattern.compile("sell (\\w+)").matcher(command)).matches()) {
                sell(matcher.group(1));
            }
        }
    }

    private void search(CardsArray cardsArray, String name) {
        Card card = cardsArray.findByName(name);
        if (card == null)
            ShopScreen.showCardNotFound();
        else
            ShopScreen.showCardInfo(card);
    }

    private void buy(String name) {
        Card card = Card.getCards().findByName(name);
        Account account = Account.getCurrentAccount();
        if (card == null) {
            ShopScreen.showCardNotFound();
            return;
        } else if (account.getDaric() < card.getPrice()) {
            ShopScreen.showOutOfDaric();
            return;
        }

        if (card.getType().equals(CardType.ITEM)) {
            Item item = (Item) card;
            if (item.getItemType().equals(ItemType.COLLECTABLE)) {
                ShopScreen.showCardNotFound();
                return;
            } else if (account.getCollection().getAllItems().size() >= 3) {
                ShopScreen.showItemIsFull();
                return;
            }
        }
        try {
            card = card.clone();
        } catch (CloneNotSupportedException e) {
            ShopScreen.showSomethingIsWrong();
        }
        account.increaseDaric(card.getPrice());
        account.addCardToCollection(card);

    }

    private void sell(String name) {

    }
}