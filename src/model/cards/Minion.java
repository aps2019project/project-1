package model.cards;

import java.util.ArrayList;

public class Minion extends Card {
    private static ArrayList<Minion> minions = new ArrayList<>();

    private int hp, ap, ar, mana;
    private AttackType attackType;
    private SPTime spTime;
    private Race race;

    Minion(String name, int price, int hp
            , int ap, int ar, int mana, AttackType attackType
            , Race race, SPTime spTime) {
        super(name, price);
        this.hp = hp;
        this.ap = ap;
        this.ar = ar;
        this.mana = mana;
        this.attackType = attackType;
        this.spTime = spTime;
        this.race = race;
        minions.add(this);
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

    public static void scanMinions(ArrayList<String[]> data){
        for(String[] line : data){
            SPTime spTime = null;

            if(!line[9].equals("-")){
                spTime = SPTime.valueOf(line[9].toUpperCase().replace(" ", "_"));
            }

            Race race = null;
            if(!line[10].equals("-")){
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
                    , spTime);
        }
    }
}
