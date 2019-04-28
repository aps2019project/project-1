package model.cards;

import java.util.ArrayList;

public class Minion extends Army {
    private static ArrayList<Minion> minions = new ArrayList<>();

    private int mana;
    private SPTime spTime;
    private Race race;

    Minion(String name, int price, int hp
            , int ap, int ar, int mana, AttackType attackType
            , Race race, SPTime spTime, String description) {
        super(name, price, description, hp, ap, ar, attackType);
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
                    , spTime
                    , line[8]);
        }
    }

    @Override
    public String toString(){
        return "Type : Minion"+
                " - Name : " + this.getName() +
                " - Class : " + this.getAttackType() +
                " - AP : " + this.getAp() +
                " - HP : " + this.getHp() +
                " - MP : " + this.getMana() +
                " - Special Power : " + this.description;
    }
}
