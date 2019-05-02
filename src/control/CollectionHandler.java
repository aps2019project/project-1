package control;

import model.cards.Card;
import model.cards.CardType;
import model.game.Deck;
import model.other.Account;
import model.variables.CardsArray;
import view.CollectionScreen;

import static model.cards.CardType.HERO;
import static model.cards.CardType.ITEM;

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
            } else if (command.matches("add \\w+ to deck \\w+")) {
                addCard();
            }
        }
    }

    private void addCard() {
        String cardID = command.split(" ")[1];
        String deckName = command.split(" ")[4];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            CollectionScreen.showCardNotFound();
        } else {
            Card card = Account.getCurrentAccount().getCollection().find(cardID);
            if (card == null)
                CollectionScreen.showCardNotFound();
            else {
                addCardToDeck(card, deck);
            }
        }
    }

    private void addCardToDeck(Card card, Deck deck) {
        if (deck.size() >= 20)
            CollectionScreen.showDeckIsFull();
        else if (deck.getHero() != null && card.getType() == HERO)
            CollectionScreen.showCantAddHero();
        else if (deck.getItem() != null && card.getType() == ITEM)
            CollectionScreen.showCantAddItem();
        else {
            deck.addCard(card);
            CollectionScreen.showCardAddedSuccessfully();
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
