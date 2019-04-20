package model.cards;

import java.util.ArrayList;

public class Hero extends Card {
    private static ArrayList<Hero> heroes = new ArrayList<>();

    private int hp, ap, ar, mp, coolDown;
    private AttackType attackType;

    public Hero(String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType) {
        super(name, price);
        this.hp = hp;
        this.ap = ap;
        this.ar = ar;
        this.mp = mp;
        this.coolDown = coolDown;
        this.attackType = attackType;
        heroes.add(this);
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

    public int getMp() {
        return mp;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public static void scanHeroes(ArrayList<String[]> array){

    }
}
