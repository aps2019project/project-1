package model.cards;

import model.Buff.Disarm;

import java.util.ArrayList;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private int mana;
    private SpellTarget spellTarget;

    Spell(String name, int price, int mana, String description, SpellTarget spellTarget) {
        super(name, price, description);
        this.mana = mana;
        this.spellTarget = spellTarget;
        spells.add(this);
        cards.add(this);
    }

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public int getMana() {
        return mana;
    }

    public static void scanSpells(ArrayList<String[]> data) {
        for (String[] line : data) {
            new Spell(line[1], Integer.parseInt(line[2])
                    , Integer.parseInt(line[3])
                    , line[5]
                    , SpellTarget.valueOf(line[4].toUpperCase().replace(" ", "_")));
        }
    }

    @Override
    public String toString() {
        return "Type : Spell" +
                " - Name : " + this.getName() +
                " - MP : " + this.getMana() +
                " - Desc : " + this.description;
    }

    public SpellTarget getSpellTarget() {
        return spellTarget;
    }

    public static void TotalDisarmEffect(Army army) {
        army.addBuff(new Disarm(1, -1));
    }

}