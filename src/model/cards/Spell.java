package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.Player;

import static model.Buff.BuffTImeType.*;

import java.util.ArrayList;

import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
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
        army.addBuff(new Disarm(1, PERMANENT));
    }

    public static void AreaDispelEffect(ArrayList<Cell> array, Player player) {
        for (Cell cell : array) {
            Army army = cell.getInsideArmy();
            if (army == null) continue;
            Spell.DispelEffect(army, player);
        }
    }

    public static void EmpowerEffect(Army army) {
        army.setAp(army.getAp() + 2);
    }

    public static void FireballEffect(Army army) {
        army.getDamaged(4);
    }

    public static void GodStrengthEffect(Hero hero) {
        hero.setAp(hero.getAp() + 4);
    }

    public static void HellFireEffect(ArrayList<Cell> array) {

    }

    public static void LightingBoltEffect(Hero hero) {
        hero.getDamaged(8);
    }

    public static void PoisonLakeEffect(ArrayList<Cell> array) {

    }

    public static void MadnessEffect(Army army) {
        army.addBuff(new Holy(4, 3, NORMAL));
        army.addBuff(new Disarm(1, 3, NORMAL));
    }

    public static void AllDisarmEffect(ArrayList<Army> array) {
        for (Army army : array) {
            army.addBuff(new Disarm(1, 1, NORMAL));
        }
    }

    public static void AllPoisonEffect(ArrayList<Army> array) {
        for (Army army : array) {
            army.addBuff(new Poision(4, NORMAL));
        }
    }

    public static void DispelEffect(Army army, Player player) {
        if (player.haveCard(army)) {
            army.deleteBuffs(NEGATIVE);
        } else {
            army.deleteBuffs(POSITIVE);
        }
    }

    public static void HealthWithProfitEffect(Army army) {
        army.addBuff(new Weakness(6, HP, PERMANENT));
        army.addBuff(new Holy(2, 3, NORMAL));
    }

    public static void PowerUpEffect(Army army) {
        army.addBuff(new Power(6, AP, PERMANENT));
    }

    public static void AllPowerEffect(ArrayList<Army> array) {
        for (Army army : array) {
            army.addBuff(new Power(2, AP, PERMANENT));
        }
    }

}