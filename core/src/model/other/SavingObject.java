package model.other;

import graphic.main.Main;
import model.cards.Card;
import model.game.Deck;
import model.game.MatchResult;
import model.other.exeptions.collection.DontHaveCardException;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SavingObject {

    private String username;
    private String password;
    private ArrayList<String> collection;
    private HashMap<String, ArrayList<String>> allDecks;
    private ArrayList<MatchResult> matchHistory = new ArrayList<MatchResult>();
    private String mainDeck = "";
    private int daric;

    public SavingObject(Account account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.collection = getNameArrayListCards(account.getCollection());
        this.allDecks = new HashMap<String, ArrayList<String>>();
        for (Deck deck: account.getAllDecks()) {
            this.allDecks.put(deck.getName(), getNameArrayListCards(deck.getCards()));
        }
        this.matchHistory = account.getMatchHistory();
        if (account.getMainDeck()!= null)
            this.mainDeck = account.getMainDeck().getName();
        this.daric = account.getDaric();
    }

    public Account getAccount() {
        Account account = new Account(this.username, this.password);
        addCollection(account);
        addAllDecks(account);
        addHistory(account);
        if (mainDeck != null)
            account.setMainDeck(account.findDeck(mainDeck));
        account.setDaric(daric);
        return account;
    }

    private void addHistory(Account account) {
        if (matchHistory == null)
            return;
        for (MatchResult result: matchHistory) {
            account.addMatchResult(result);
        }
    }

    private void addAllDecks(Account account) {
        if (allDecks == null)
            return;
        for (Map.Entry<String, ArrayList<String>> tempDeck: allDecks.entrySet()) {
            String deckName = tempDeck.getKey();
            CardsArray cards = new CardsArray(tempDeck.getValue(), this.username);
            try {
                account.addDeck(new Deck(deckName, cards));
            } catch (DontHaveCardException e) {
                e.printStackTrace();
            }
        }
    }

    private void addCollection(Account account) {
        if (collection == null)
            return;
        for (String cardName: collection) {
            Card card = Card.getCards().findByName(cardName);
            card.setUserName(this.username);
            account.addCardToCollection(card);
        }
    }

    private ArrayList<String> getNameArrayListCards(CardsArray cardsArray) {
        ArrayList<String> result = new ArrayList<String>();
        for (Card card: cardsArray.getAllCards()) {
            result.add(card.getName());
        }
        return result;
    }

    public String getUsername() {
        return username;
    }
}
