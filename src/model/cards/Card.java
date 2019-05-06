package model.cards;

import control.CsvReader;
import model.game.Cell;
import model.game.Deck;
import model.game.Flag;
import model.game.Player;
import model.other.Account;
import model.variables.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Card implements Cloneable {

    protected static CardsArray cards = new CardsArray();
    protected int number;
    protected ID ID;
    protected String name;
    protected int price;
    protected String userName;
    protected Flag flag;
    protected Cell whereItIs;
    protected int neededManaToPut;//
    protected String description;
    protected CardType type;

    public void setName(String name) {
        this.name = name;
    }

    Card(int number, String name, int price, String description, CardType type, int mana) {
        this.number = number;
        this.type = type;
        this.name = name;
        this.price = price;
        this.ID = new ID();
        this.neededManaToPut = mana;
        this.description = description;
    }

    public Cell getWhereItIs() {
        return whereItIs;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWhereItIs(Cell whereItIs) {
        this.whereItIs = whereItIs;
    }

    public int getNumber() {
        return number;
    }

    public CardType getType() {
        return type;
    }

    public static CardsArray getCards() {
        return cards;
    }

    public Flag getFlag() {
        return flag;
    }

    public boolean addFlag(Flag flag) {
        if (this.flag != null) return false;
        this.flag = flag;
        return true;
    }

    public Account getAccount() {
        return Account.findAccount(userName);
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ID getID() {
        return ID;
    }

    public boolean isSameAs(Card card) {
        if (card == null)
            return false;
        return this.ID.isSameAs(card.ID);
    }

    public boolean isSameAs(String ID) {
        return this.ID.isSameAs(ID);
    }

    public static void scanAllCards() {
        Hero.scanHeroes(CsvReader.readCards("Heroes"));
        Minion.scanMinions(CsvReader.readCards("Minions"));
        Item.scanItems(CsvReader.readCards("Items"));
        Spell.scanSpells(CsvReader.readCards("Spells"));
    }

    public Card clone() throws CloneNotSupportedException {
        Card temp = (Card) super.clone();
        temp.ID = new ID();
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public int getNeededManaToPut() {
        return neededManaToPut;
    }

    public static void makeStroyDeck(int storyNumber, Account account) throws Exception {
        Deck deck = new Deck("Story " + storyNumber);
        File file = new File("Files/StoryLevels.txt");
        Scanner scanner = new Scanner(file);
        String[] lines = new String[3];
        for (int i = 0; i < 3; i++) {
            lines[i] = scanner.nextLine();
        }
        storyNumber--;
        String[] line = lines[storyNumber].split(",");
        ArrayList<Integer> array = new ArrayList<>();
        for (String s : line) {
            array.add(Integer.parseInt(s));
        }
        Card temp = null;
        for (Hero hero : Hero.getHeroes()) {
            if (hero.getNumber() == array.get(0)) {
                temp = hero.clone();
                temp.setUserName(account.getUsername());
                deck.addCard(temp);
            }
        }
        for (Spell spell : Spell.getSpells()) {
            if (array.subList(1, 8).contains(spell.getNumber())) {
                temp = spell.clone();
                temp.setUserName(account.getUsername());
                deck.addCard(temp);
            }
        }
        for (Minion minion : Minion.getMinions()) {
            if (array.subList(8, 21).contains(minion.getNumber())) {
                temp = minion.clone();
                temp.setUserName(account.getUsername());
                deck.addCard(temp);
            }
        }
        for (Item item : Item.getItems()) {
            if (item.getNumber() == array.get(21)) {
                temp = item.clone();
                temp.setUserName(account.getUsername());
                deck.addCard(temp);
            }
        }
        account.addDeck(deck);
        account.changeMainDeck(deck);
    }

    @Override
    public String toString() {
        switch (this.type) {
            case HERO :
                return "Hero - " + ((Hero)this).toString();
            case MINION :
                return "Minion - " + ((Minion)this).toString();
            case SPELL :
                return "Spell - " + ((Spell)this).toString();
            case ITEM :
                return "Item - " + ((Item)this).toString();
        }
        return null;
    }

}
