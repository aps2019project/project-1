package control;

import model.cards.Card;
import model.game.Deck;
import model.other.Account;
import model.variables.CardsArray;
import view.CollectionScreen;

class CollectionHandler extends Handler{

    @Override
    void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim().toLowerCase();
            if (command.matches("exit")) {
                new MenuHandler();
            } else if (command.matches("search \\w+")) {
                searchCard();
            } else if (command.matches("create deck \\w+")) {
                createNewDeck();
            } else if (command.matches("delete deck \\w+")) {
                deleteDeck();
            }
        }
    }

    private void deleteDeck() {
        String deckName = command.split(" ")[2];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            CollectionScreen.showDeckNotFound();
        } else {
            Account.getCurrentAccount().removeDeck(deck);
            CollectionScreen.showSuccessfulDeckRemoval();
        }
    }

    private void createNewDeck() {
        String deckName = command.split(" ")[2];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            Account.getCurrentAccount().addDeck(new Deck(deckName));
            CollectionScreen.showSuccessfulDeckCreation();
        } else {
            CollectionScreen.showDeckExists();
        }
    }

    private void searchCard() {
        String name = command.split(" ")[1];
        CardsArray collection = Account.getCurrentAccount().getCollection();
        Card card = collection.findByName(name);
        if (card == null) {
            CollectionScreen.showCardNotFound();
        } else {
            CollectionScreen.showFoundCard(card);
        }
    }


}
