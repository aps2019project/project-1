package model.cards;

import control.CsvReader;
import model.variables.*;
import java.util.ArrayList;

public class Card implements Cloneable{
    protected static ArrayList<Card> cards = new ArrayList<>();
    protected model.variables.ID ID;
    protected String name;
    protected int price;
    protected String description;

    Card(String name, int price, String description){
        this.name = name;
        this.price = price;
        this.ID = new ID();
        this.description = description;
    }

    public static ArrayList<Card> getCards() {
        return cards;
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
        return this.ID.isSameAs(card.ID);
    }
    public boolean isSameAs(String ID) {
        return this.ID.isSameAs(ID);
    }

    public static void scanAllCards(){
        Hero.scanHeroes(CsvReader.readCards("Heroes"));
        Minion.scanMinions(CsvReader.readCards("Minions"));
        Item.scanItems(CsvReader.readCards("Items"));
        Spell.scanSpells(CsvReader.readCards("Spells"));
    }

    public Card clone() throws CloneNotSupportedException{
        Card temp = (Card)super.clone();
        temp.ID = new ID();
        return temp;
    }

}
