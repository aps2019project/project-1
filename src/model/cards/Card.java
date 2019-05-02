package model.cards;

import control.CsvReader;
import model.game.Flag;
import model.game.Player;
import model.other.Account;
import model.variables.*;
import java.util.ArrayList;

public class Card implements Cloneable{
    protected static CardsArray cards = new CardsArray();
    protected model.variables.ID ID;
    protected String name;
    protected int price;
    protected Account account;
    protected Flag flag;
    protected int NeededManaToMove;
    protected String description;

    Card(String name, int price, String description){
        this.name = name;
        this.price = price;
        this.ID = new ID();
        this.description = description;
    }

    public static CardsArray getCards() {
        return cards;
    }

    public Flag getFlag() {
        return flag;
    }

    public boolean addFlag(Flag flag) {
        if(this.flag != null) return false;
        this.flag = flag;
        return true;
    }

    public Account getAccount() {
        return account;
    }

    public int getNeededManaToMove() {
        return NeededManaToMove;
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

    public void showCard() {
    }


}
