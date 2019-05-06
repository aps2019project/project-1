package control;

import model.cards.Card;
import model.game.Deck;
import model.other.Account;
import model.variables.CardsArray;
import view.CollectionScreen;

import static control.HandlerType.MENU;
import static model.cards.CardType.HERO;
import static model.cards.CardType.ITEM;

class CollectionHandler extends Handler{

    CollectionHandler() {
        CollectionScreen.showWelcomeLine();
        CollectionScreen.showOptions();
    }

    @Override
    HandlerType handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim().toLowerCase();
            if (command.matches("exit")) {
                return MENU;
            } else if (command.matches("search \\w+")) {
                searchCard();
            } else if (command.matches("create deck \\w+")) {
                createNewDeck();
            } else if (command.matches("delete deck \\w+")) {
                deleteDeck();
            } else if (command.matches("add [^ ]+ to deck \\w+")) {
                addAndRemoveCard(true);
            } else if (command.matches("remove [^ ]+ from deck \\w+")) {
                addAndRemoveCard(false);
            } else if (command.matches("validate \\w+")) {
                checkDeckValidation();
            } else if (command.matches("select deck \\w+")) {
                changeMainDeck();
            } else if (command.matches("show deck \\w+")) {
                printDeck();
            } else if (command.matches("show all decks")) {
                showAllDecks();
            } else if (command.matches("show")) {
                CollectionScreen.showCardArray(Account.getCurrentAccount().getCollection());
            } else {
                CollectionScreen.shoeInvalidCommand();
                CollectionScreen.showOptions();
            }
        }
        return null;
    }

    private void showAllDecks() {
        CollectionScreen.showMainDeck(Account.getCurrentAccount().getMainDeck());
        for (Deck deck : Account.getCurrentAccount().getAllDecks()) {
            if (deck.equals(Account.getCurrentAccount().getMainDeck()))
                continue;
            CollectionScreen.showDeckDetails(deck);
        }
    }

    private void printDeck() {
        String deckName = command.split(" ")[2];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            CollectionScreen.showDeckNotFound();
        } else {
            CollectionScreen.showDeckDetails(deck);
        }
    }

    private void changeMainDeck() {
        String deckName = command.split(" ")[2];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            CollectionScreen.showDeckNotFound();
        } else {
            Account.getCurrentAccount().changeMainDeck(deck);
            CollectionScreen.showMainDeckChangedSuccessfully();
        }
    }

    private void checkDeckValidation() {
        String deckName = command.split(" ")[1];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            CollectionScreen.showDeckNotFound();
        } else {
            if (deck.checkIfValid())
                CollectionScreen.showDeckIsValid();
            else
                CollectionScreen.showDeckIsInvalid();
        }
    }

    private void addAndRemoveCard(boolean isAdd) {
        String cardId = command.split(" ")[1];
        String deckName = command.split(" ")[4];
        Deck deck = Account.getCurrentAccount().findDeck(deckName);
        if (deck == null) {
            CollectionScreen.showDeckNotFound();
        } else {
            Card card = deck.getCards().find(cardId);
            if (card == null)
                CollectionScreen.showCardNotFound();
            else if (deck.getCards().findByName(card.getName()) == null && !isAdd) {
                CollectionScreen.showCardNotFound();
            }
            else {
                if (isAdd)
                    addCardToDeck(card, deck);
                else
                    removeCardFromDeck(card, deck);

            }
        }
    }

    private void removeCardFromDeck(Card card, Deck deck) {
        if (deck.size() == 0)
            CollectionScreen.showDeckIsFull();
        else if (deck.getHero() != null && card.getType() == HERO)
            CollectionScreen.showCantAddHero();
        else if (deck.getItem() != null && card.getType() == ITEM)
            CollectionScreen.showCantAddItem();
        else {
            deck.deleteCard(card);
            CollectionScreen.showCardRemovedSuccessfully();
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
            try {
                card = card.clone();
            } catch (Exception e) {
                CollectionScreen.showCloneError();
            }
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
