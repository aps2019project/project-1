package model.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.cards.Card;
import model.game.Deck;
import model.game.MatchResult;
import model.variables.CardsArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Account {

    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account currentAccount = null;

    private String username;
    private String password;
    private CardsArray collection = new CardsArray();
    private ArrayList<Deck> allDecks = new ArrayList<>();
    private ArrayList<MatchResult> matchHistory = new ArrayList<>();
    private Deck mainDeck = new Deck();
    private int daric = 15000;
    private StoryProgress storyProgress;

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

    public Deck findDeck(String name) {
        for (Deck deck : allDecks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public int getDaric() {
        return daric;
    }

    public StoryProgress getStoryProgress() {
        return storyProgress;
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
        accounts.sort((Account a1, Account a2) -> {
            if (a1.getWonGames() == a2.getWonGames())
                return a1.username.compareTo(a2.password);
            return a2.getWonGames() - a1.getWonGames();
        });
    }

    public static void saveAccountDetails() {
        Gson gson = new GsonBuilder().create();
        try {
            Writer writer = new FileWriter("Files/Data/Accounts.json");
            writer.write(gson.toJson(Account.getAccounts()));
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAccountDetails() {
        Type type = new TypeToken<List<Account>>() {}.getType();
        Gson gson = new GsonBuilder().create();
        Scanner reader;
        String str = "";

        try {
            reader = new Scanner(new File("Files/Data/Accounts.json"));
        }
        catch (IOException e) {
            return;
        }

        while (reader.hasNext()){
            str = reader.nextLine();
        }

        List<Account> data = gson.fromJson(str, type);
        if (data != null)
            accounts.addAll(data);
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

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", daric=" + daric +
                ", storyProgress=" + storyProgress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getDaric() == account.getDaric() &&
                getUsername().equals(account.getUsername()) &&
                getPassword().equals(account.getPassword()) &&
                Objects.equals(getCollection(), account.getCollection()) &&
                Objects.equals(getAllDecks(), account.getAllDecks()) &&
                Objects.equals(getMatchHistory(), account.getMatchHistory()) &&
                Objects.equals(getMainDeck(), account.getMainDeck()) &&
                getStoryProgress() == account.getStoryProgress();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getCollection(), getAllDecks(), getMatchHistory(), getMainDeck(), getDaric(), getStoryProgress());
    }
}
