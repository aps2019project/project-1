package control;

import model.cards.Card;
import model.cards.CardType;
import model.cards.Item;
import model.cards.ItemType;
import model.other.Account;
import model.variables.CardsArray;
import view.ShopScreen;

import java.util.regex.*;

import static control.HandlerType.MENU;

class ShopHandler extends Handler {

    ShopHandler() {
        ShopScreen.showWelcomeLine();
        ShopScreen.showOptions();
    }

    @Override
    HandlerType handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            if (command.matches("exit")) {
                return MENU;
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
            } else if (command.matches("sell \\w+")) {
                sell(command.split(" ")[1]);
            } else {
                ShopScreen.showOptions();
            }
        }
        return null;
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
        } else if (Account.getCurrentAccount().getCollection().findByName(card.getName()) != null) {
            ShopScreen.showCardAlreadyInCollection();
            return;
        } else if (account.getDaric() < card.getPrice()) {
            ShopScreen.showOutOfDaric();
            return;
        }


        if (card.getType().equals(CardType.ITEM)) {
            Item item = (Item) card;
            if (item.getItemType().equals(ItemType.COLLECTIBLE)) {
                ShopScreen.showCardNotFound();
                return;
            } else if (account.getCollection().getAllItems().size() >= 3) {
                ShopScreen.showItemIsFull();
                return;
            }
        }
        try {
            card = card.clone();
            card.setUserName(account.getUsername());
        } catch (CloneNotSupportedException e) {
            ShopScreen.showSomethingIsWrong();
        }
        card.setUserName(account.getUsername());
        account.decreaseDaric(card.getPrice());
        account.addCardToCollection(card);
        ShopScreen.showBuyWasSuccessful();

    }

    private void sell(String name) {
        Account account = Account.getCurrentAccount();
        Card card = account.getCollection().findByName(name);
        if (card == null) {
            ShopScreen.showCardNotFound();
            return;
        }

        if (card.getType().equals(CardType.ITEM)) {
            Item item = (Item) card;
            if (item.getItemType().equals(ItemType.COLLECTIBLE)) {
                ShopScreen.showCardNotFound();
                return;
            }
        }
        account.increaseDaric(card.getPrice());
        account.deleteCardFromAllDecks(card.getName());
        account.removeCardFromCollection(card);
        account.getCollection().remove(card);
        ShopScreen.showSellWasSuccessful();
    }

}