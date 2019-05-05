package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.Player;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.MINION;

public class Minion extends Army {
    private static ArrayList<Minion> minions = new ArrayList<>();

    private int mana;
    private SPTime spTime;
    private Race race;

    Minion(String name, int price, int hp
            , int ap, int ar, int mana, AttackType attackType
            , Race race, SPTime spTime, String description) {
        super(name, price, description, hp, ap, ar, attackType, MINION);
        this.mana = mana;
        this.spTime = spTime;
        this.race = race;
        minions.add(this);
        cards.add(this);
    }

    public static ArrayList<Minion> getMinions() {
        return minions;
    }

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }

    public int getAr() {
        return ar;
    }

    public int getMana() {
        return mana;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public SPTime getSpTime() {
        return spTime;
    }

    public Race getRace() {
        return race;
    }

    public static void scanMinions(ArrayList<String[]> data) {
        for (String[] line : data) {
            SPTime spTime = null;

            if (!line[9].equals("-")) {
                spTime = SPTime.valueOf(line[9].toUpperCase().replace(" ", "_"));
            }

            Race race = null;
            if (!line[10].equals("-")) {
                race = Race.valueOf(line[10].toUpperCase());
            }

            new Minion(line[1]
                    , Integer.parseInt(line[2])
                    , Integer.parseInt(line[4])
                    , Integer.parseInt(line[5])
                    , Integer.parseInt(line[7])
                    , Integer.parseInt(line[3])
                    , AttackType.valueOf(line[6].toUpperCase())
                    , race
                    , spTime
                    , line[8]);
        }
    }

   /* @Override
    public String toString() {
        return "Minion{" +
                "mana=" + mana +
                ", spTime=" + spTime +
                ", race=" + race +
                ", specialBuffs=" + specialBuffs +
                ", passiveBuffs=" + passiveBuffs +
                ", ap=" + ap +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }*/

    public void PersianSwordsmanOnAttack(Army army) {
        army.addBuff(new Stun(1, 1, NORMAL));

    }

    public void PersianPahlevanOnAttack(Army army) {

    }

    public void TuranianSpyOnAttack(Army army) {
        army.addBuff(new Disarm(1, 1, NORMAL));
        army.addBuff(new Poison(4, NORMAL));
    }

    public void EagleOnSpawn(Player player, Cell cell) {
        this.addBuff(new Power(10, HP, 1, CONTINUOUS));
    }

    public void OneEyedGiantOnDeath(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesAround(cell);
        for(Army army : array) {
            army.getDamaged(2, this);
        }
    }

    public void PoisonousSnakeOnAttack(Army army) {
        army.addBuff(new Poison(3, NORMAL));
    }

    public void HugeSnakeOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesInDistance2(cell);

    }

    public void WhiteWolfOnAttack(Army army) {

    }

    public void PanterOnAttack(Army army) {

    }

    public void WolfOnAttack(Army army) {

    }

    public void WitchPassive(Player player, Cell cell) {
        ArrayList<Army> array = player.getFriendsAround(cell);
        array.add(this);
        for(Army army : array) {
            army.addBuff(new Power(2, AP, 1, NORMAL));
            army.addBuff(new Weakness(1, HP, 1, NORMAL));
        }
    }

    public void GrandWitchPassive(Player player, Cell cell) {
        ArrayList<Army> array = player.getFriendsAround(cell);
        array.add(this);
        for(Army army : array) {
            army.addBuff(new Power(2, AP, 1, NORMAL));
            army.addBuff(new Holy(1, 1, CONTINUOUS));
        }
    }

    public void GoblinOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getInGameCards();
        for(Army army : array) {
            if(army instanceof Hero) continue;
            army.addBuff(new Power(1, AP, 1, CONTINUOUS));
        }
    }


}
