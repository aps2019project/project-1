package model.cards;

import java.util.ArrayList;

public class Hero extends Card {
    private static ArrayList<Hero> heroes = new ArrayList<>();

    private String name;
    private int price, hp, ap, ar, mp, coolDown;
    private AttackType attackType;

    public Hero(String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType) {
        this.name = name;
        this.price = price;
        this.hp = hp;
        this.ap = ap;
        this.ar = ar;
        this.mp = mp;
        this.coolDown = coolDown;
        this.attackType = attackType;
        heroes.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }
}
