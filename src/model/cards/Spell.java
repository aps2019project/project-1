package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.Player;

import static model.Buff.BuffTImeType.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.SPELL;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private int mana;
    private String target;

    Spell(int number, String name, int price, int mana, String description, String target) {
        super(number, name, price, description, SPELL);
        this.mana = mana;
        spells.add(this);
        cards.add(this);
        this.target = target;
    }

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public int getMana() {
        return mana;
    }

    public static void scanSpells(ArrayList<String[]> data) {
        for (String[] line : data) {
            new Spell(Integer.parseInt(line[0])
                    ,line[1]
                    , Integer.parseInt(line[2])
                    , Integer.parseInt(line[3])
                    , line[5]
                    , line[4]);

        }
    }

    @Override
    public String toString() {
        return  "mana=" + mana +
                ", spellTarget=" + target +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type;
    }


    public static void useSpell(Player player, String spellname) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Spell.class.getDeclaredMethod(spellname + "Effect", Player.class).invoke(null, player);
    }

    public static void TotalDisarmEffect(Player player) {
        Army army = player.getOneEnemy();
        army.addBuff(new Disarm(1, PERMANENT));
    }

    public static void AreaDispelEffect(Player player) {
        ArrayList<Cell> array = player.getSquare2();
        for (Cell cell : array) {
            Army army = cell.getInsideArmy();
            if (army == null) continue;
            if (player.haveCard(army)) {
                army.deleteBuffs(NEGATIVE);
            } else {
                army.deleteBuffs(POSITIVE);
            }
        }
    }

    public static void EmpowerEffect(Player player) {
        Army army = player.getOneFriend();
        army.setAp(army.getAp() + 2);
    }

    public static void FireballEffect(Player player) {
        Army army = player.getOneEnemy();
        army.getDamaged(4, null);
    }

    public static void GodStrengthEffect(Player player) {
        Hero hero = player.getHero();
        hero.setAp(hero.getAp() + 4);
    }

    public static void HellFireEffect(Player player) {
        ArrayList<Cell> array = player.getSquare2();

    }

    public static void LightingBoltEffect(Player player) {
        Hero hero = player.getEnemyPlayer().getHero();
        hero.getDamaged(8, null);
    }

    public static void PoisonLakeEffect(Player player) {
        ArrayList<Cell> array = player.getSquare3();
    }

    public static void MadnessEffect(Player player) {
        Army army = player.getOneFriend();
        army.addBuff(new Holy(4, 3, NORMAL));
        army.addBuff(new Disarm(1, 3, NORMAL));
    }

    public static void AllDisarmEffect(Player player) {
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for (Army army : array) {
            army.addBuff(new Disarm(1, 1, NORMAL));
        }
    }

    public static void AllPoisonEffect(Player player) {
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for (Army army : array) {
            army.addBuff(new Poison(4, NORMAL));
        }
    }

    public static void DispelEffect(Player player) {
        Army army = player.getOneEnemyOrFriend();
        if (player.haveCard(army)) {
            army.deleteBuffs(NEGATIVE);
        } else {
            army.deleteBuffs(POSITIVE);
        }
    }

    public static void HealthWithProfitEffect(Player player) {
        Army army = player.getOneFriend();
        army.addBuff(new Weakness(6, HP, PERMANENT));
        army.addBuff(new Holy(2, 3, NORMAL));
    }

    public static void PowerUpEffect(Player player) {
        Army army = player.getOneFriend();
        army.addBuff(new Power(6, AP, PERMANENT));
    }

    public static void AllPowerEffect(Player player) {
        ArrayList<Army> array = player.getInGameCards();
        for (Army army : array) {
            army.addBuff(new Power(2, AP, PERMANENT));
        }
    }

    public static void AllAttackEffect(Player player) {
        ArrayList<Army> array = player.getAllEnemiesInOneColumn().getArmy();
        for (Army army : array) {
            army.getDamaged(6, null);
        }
    }

    public static void WeakeningEffect(Player player) {
        Army army = player.getOneEnemyMinion();
        army.addBuff(new Weakness(4, AP, PERMANENT));
    }

    public static void SacrificeEffect(Player player){
        Army army = player.getOneFriendMinion();
        army.addBuff(new Weakness(6, HP, PERMANENT));
        army.addBuff(new Power(8, AP, PERMANENT));
    }

    public static void KingsGuardEffect(Player player){
        Army army = player.getRandomMinionAroundFriendHero();
        army.setHp(0);
    }

    public static void ShockEffect(Player player) {
        Army army = player.getOneEnemy();
        army.addBuff(new Stun(1, 2, NORMAL));
    }

}