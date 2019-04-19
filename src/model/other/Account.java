package model.other;

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

    public static ArrayList<Account> getAccounts() {
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
}
