package model.cards;

import java.util.ArrayList;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private int mana;

    Spell(String name, int price, int mana){
        super(name, price);
        this.mana = mana;
    }

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public int getMana() {
        return mana;
    }

    public static void scanSpells(ArrayList<String[]> data){
        for(String[] line : data){
            new Spell(line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]));
        }
    }
}
