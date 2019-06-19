package model.other;

import graphic.main.Main;
import model.cards.Card;
import model.game.Deck;
import model.game.MatchResult;
import model.variables.CardsArray;
import org.omg.PortableInterceptor.ServerRequestInfo;

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
    private StoryProgress storyProgress;

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
        this.storyProgress = account.getStoryProgress();
    }

    public Account getAccount() {
        Account account = new Account(this.username, this.password);
        addCollection(account);
        addAllDecks(account);
        addHistory(account);
        if (!mainDeck.equals(""))
            account.setMainDeck(account.findDeck(mainDeck));
        account.setDaric(daric);
        account.setStoryProgress(storyProgress);
        return account;
    }

    private void addHistory(Account account) {
        for (MatchResult result: matchHistory) {
            account.addMatchResult(result);
        }
    }

    private void addAllDecks(Account account) {
        for (Map.Entry<String, ArrayList<String>> tempDeck: allDecks.entrySet()) {
            String deckName = tempDeck.getKey();
            CardsArray cards = new CardsArray(tempDeck.getValue());
            account.addDeck(new Deck(deckName, cards));
        }
    }

    private void addCollection(Account account) {
        for (String cardName: collection) {
            Card card = Card.getCards().findByName(cardName);
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
}
