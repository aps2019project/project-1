package model.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connection.Client;
import model.cards.*;
import model.game.Deck;
import model.game.MatchResult;
import model.other.exeptions.collection.*;
import model.other.exeptions.shop.*;
import model.variables.CardsArray;
import view.CollectionScreen;

import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

import static model.cards.CardType.HERO;
import static model.cards.CardType.ITEM;

public class Account {

    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static Account currentAccount = null;

    private String username;
    private String password;
    private CardsArray collection = new CardsArray();
    private ArrayList<Deck> allDecks = new ArrayList<Deck>();
    private ArrayList<MatchResult> matchHistory = new ArrayList<MatchResult>();
    private Deck mainDeck = new Deck();
    private int daric = 15000;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        accounts.add(this);
    }

    public static ArrayList<Account> getAccounts() {
        sortAccounts();
        return accounts;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public ArrayList<MatchResult> getMatchHistory() {
        return matchHistory;
    }

    public static void setCurrentAccount(Account currentAccount) {
        Account.currentAccount = currentAccount;
    }

    public CardsArray getCollection() {
        return collection;
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public void setMainDeck(Deck deck) {
        this.mainDeck = deck;
    }

    public Deck findDeck(String name) {
        for (Deck deck : allDecks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public void setDaric(int daric) {
        this.daric = daric;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public int getDaric() {
        return daric;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void increaseDaric(int increase) {
        daric += increase;
    }

    public void decreaseDaric(int decrease) {
        daric -= decrease;
    }

    public int getWonGames() {
        int win = 0;
        for (MatchResult history : matchHistory) {
            if (history.getWinner().equals(this))
                win++;
        }
        return win;
    }

    public int getLosedGames() {
        int lose = 0;
        for (MatchResult history : matchHistory) {
            if (!history.getWinner().equals(this) && history.getWinner() != null)
                lose++;
        }
        return lose;
    }

    public int getDrewGames() {
        int drew = 0;
        for (MatchResult history : matchHistory) {
            if (history.getWinner() == null)
                drew++;
        }
        return drew;
    }

    public void addMatchResult(MatchResult result) {
        matchHistory.add(result);
    }

    public void addDeck(Deck deck) throws DontHaveCardException {
        for (Card card: deck.getCards().getAllCards())
            if (this.collection.findByName(card.getName()) == null)
                throw new DontHaveCardException();
        allDecks.add(deck);
    }

    public void removeDeck(Deck deck) {
        allDecks.remove(deck);
    }

    public void changeMainDeck(Deck deck) {
        mainDeck = deck;
    }

    public void addDeckToCollections(Deck deck) {
        for(Card card : deck.getCards().getAllCards()) {
            this.addCardToCollection(card);
        }
    }
    public void addCardToCollection(Card card) {
        collection.add(card);
    }

    public void removeCardFromCollection(Card card) {
        collection.remove(card);
    }

    public static boolean doesAccountExist(String username) {
        return !(findAccount(username) == null);
    }

    public static boolean checkIfPasswordIsCorrect(String username, String password) {
        Account account = findAccount(username);
        if (account == null)
            return false;
        return account.getPassword().equals(password);
    }

    public static Account findAccount(String username) {
        for (Account account : accounts) {
            if (account.username.equals(username)) {
                return account;
            }
        }
        return null;
    }

    private static void sortAccounts() {
        Collections.sort(accounts, new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                if (a1.getWonGames() == a2.getWonGames())
                    return a1.username.compareTo(a2.username);
                return a1.getWonGames() - a2.getWonGames();
            }
        });
    }

    public static void updateAccount() {
        Client.sendCommand("update account");
        Client.sendData(new SavingObject(Account.getCurrentAccount()));
    }

    public void deleteCardFromAllDecks(String cardName){
        for (Deck deck : allDecks) {
            Card card = deck.getCards().findByName(cardName);
            while (card != null) {
                    deck.deleteCard(card);
                    card = deck.getCards().findByName(cardName);
            }
        }
    }

    public void buyCard(String name) throws ShopExeption{
        Card card = Card.getCards().findByName(name);
        if (card == null)
            throw new CardNotFoundException();
        else if (getCollection().findByName(card.getName()) != null)
            throw new CardAlreadyExistsException();
        else if (getDaric() < card.getPrice())
            throw new NotEnoughDaricException();


        if (card.getType().equals(CardType.ITEM)) {
            Item item = (Item) card;
            if (item.getItemType().equals(ItemType.COLLECTIBLE))
                throw new CantSellCardException();
            else if (getCollection().getAllItems().size() >= 3)
                throw new MoreThanTwoItemException();
        }
        try {
            card = card.clone();
            card.setUserName(getUsername());
        } catch (CloneNotSupportedException ignored) {}
        card.setUserName(getUsername());
        decreaseDaric(card.getPrice());
        addCardToCollection(card);
    }

    public void sellCard(String name) throws ShopExeption {
        Card card = getCollection().findByName(name);
        if (card == null)
            throw new CardNotFoundException();

        if (card.getType().equals(CardType.ITEM)) {
            Item item = (Item) card;
            if (item.getItemType().equals(ItemType.COLLECTIBLE))
                throw new CantSellCardException();
        }
        increaseDaric(card.getPrice());
        deleteCardFromAllDecks(card.getName());
        removeCardFromCollection(card);
        getCollection().remove(card);
    }

    public void addCardToDeck(String cardName, Deck deck) throws CollectionException {
        Card card = Card.getCards().findByName(cardName);
        if (deck.size() >= 20)
            throw new DeckIsFullException();
        else if (deck.getHero() != null && card.getType() == HERO)
            throw new DeckAlreadyHaveHeroException();
        else if (deck.getItem() != null && card.getType() == ITEM)
            throw new DeckAlreadyHaveItemException();
        else {
            try {
                card = card.clone();
            } catch (Exception e) {
                CollectionScreen.showCloneError();
            }
            deck.addCard(card);
        }
    }

    public void removeCardFromDeck(String cardName, Deck deck) throws CollectionException {
        Card card = deck.getCards().findByName(cardName);
        if (deck.size() == 0)
            throw new DeckIsEmptyException();
        else {
            deck.deleteCard(card);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", daric=" + daric +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getDaric() == account.getDaric() &&
                getUsername().equals(account.username)&&
                getPassword().equals(account.password) &&
                getCollection().equals(account.collection) &&
                getAllDecks().equals(account.allDecks) &&
                getMatchHistory().equals(account.matchHistory) &&
                getMainDeck().equals(account.mainDeck);
    }

}
