package model.cards;

import java.util.ArrayList;

public class Hero extends Army {
    private static ArrayList<Hero> heroes = new ArrayList<>();

    private int mp, coolDown;

    Hero(String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType, String description) {
        super(name, price, description, hp, ap, ar, attackType);
        this.mp = mp;
        this.coolDown = coolDown;
        heroes.add(this);
        cards.add(this);
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public int getMp() {
        return mp;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public static void scanHeroes(ArrayList<String[]> data){
        for(String[] line : data){
            new Hero(line[1]
                    , Integer.parseInt(line[2])
                    , Integer.parseInt(line[3])
                    , Integer.parseInt(line[4])
                    , Integer.parseInt(line[6])
                    , Integer.parseInt(line[8])
                    , Integer.parseInt(line[9])
                    , AttackType.valueOf(line[5].toUpperCase())
                    , line[7]);
        }
    }
    @Override
    public String toString(){
        return "Name : " + this.getName() +
                " - AP : " + this.getAp() +
                " - HP : " + this.getHp() +
                " - Class : " + this.getAttackType() +
                " - Special Power : " + this.description;
    }
}
