package model.other;

import model.cards.Card;
import model.game.Deck;
import model.game.MatchResult;
import model.variables.CardsArray;

import java.util.ArrayList;

public class Account {

    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account currentAccount;
    private String username;
    private String password;
    private CardsArray collection;
    private ArrayList<Deck> allDecks = new ArrayList<>();
    private ArrayList<MatchResult> matchHistory = new ArrayList<>();
    private Deck mainDeck;
    private int daric;
    private int storyProgress;

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

    public Deck getMainDeck() {
        return mainDeck;
    }

    public int getDaric() {
        return daric;
    }

    public int getStoryProgress() {
        return storyProgress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseDaric(int increase) {
        daric += increase;
    }

    public void decreaseDaric(int decrease) {
        daric -= decrease;
    }

    public int getWonGames() {
        return 0;
    }

    public int getLosedGames() {
        return 0;
    }

    public int getDrewGames() {
        return 0;
    }

    public void addMatchResult(MatchResult result) {

    }

    public void addDeck(Deck deck) {
        allDecks.add(deck);
    }

    public void removeDeck(Deck deck) {
        allDecks.remove(deck);
    }

    public void changeMainDeck(Deck deck) {
        mainDeck = deck;
    }

    public void addCardToCollection(Card card) {
        // need conditions
    }

    public void removeCardFromCollection(Card card) {
        // need conditions
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

    }



}
