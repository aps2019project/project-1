package model.cards;

import control.CsvReader;

public class Card {
    private model.variables.ID ID;
    protected String name;
    protected int price;

    Card(String name, int price){
        this.name = name;
        this.price = price;
        this.ID = new model.variables.ID();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public model.variables.ID getID() {
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

}
