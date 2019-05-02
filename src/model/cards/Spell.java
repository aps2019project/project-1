package model.cards;

import model.Buff.Buff;
import model.Buff.BuffType;
import model.Buff.Disarm;
import model.game.Cell;
import model.game.Player;

import java.util.ArrayList;

import static model.cards.CardType.SPELL;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private int mana;
    private SpellTarget spellTarget;

    Spell(String name, int price, int mana, String description, SpellTarget spellTarget) {
        super(name, price, description, SPELL);
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
        return "Spell{" +
                "mana=" + mana +
                ", spellTarget=" + spellTarget +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }

    public SpellTarget getSpellTarget() {
        return spellTarget;
    }

    public static void TotalDisarmEffect(Army army) {
        army.addBuff(new Disarm(1, -1));
    }

    public static void AreaDispelEffect(ArrayList<Cell> array, Player player) {
        for (Cell cell : array) {
            Card card = cell.getInsideCard();
            if (card == null) continue;
            if (player.haveCard(card)) {
                ((Army)card).deleteBuffs(BuffType.NEGATIVE);
            } else{
                ((Army)card).deleteBuffs(BuffType.POSITIVE);
            }
        }
    }

}