package model.cards;

import model.Buff.Buff;
import model.variables.CardsArray;

import java.util.ArrayList;

public class Army extends Card {
    protected int hp, ap, ar;
    protected AttackType attackType;
    protected ArrayList<Buff> buffs = new ArrayList<>();

    public Army(String name, int price, String description, int hp, int ap, int ar, AttackType attackType) {
        super(name, price, description);
        this.hp = hp;
        this.ap = ap;
        this.ar = ar;
        this.attackType = attackType;
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

    public AttackType getAttackType() {
        return attackType;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public static void decreaseBuffTurns(CardsArray array) {
        for (Card card : array.getAllCards()) {
            for (Buff buff : ((Army) card).getBuffs()) {
                buff.decreaseTurns();
            }
        }
    }

    public static void callPassiveMinionsSP(CardsArray array) {
        for (Card card : array.getAllCards()) {
            try {
                Minion minion = (Minion)card;
                if ( minion.getSpTime() == SPTime.PASSIVE ){
                    for(Buff buff : minion.getPassiveBuffs()){
                        buff.setTurns(1);
                    }
                }
            } catch (ClassCastException cce){ continue;}
        }
    }

    public void addBuff(Buff buff){
        this.buffs.add(buff);
    }
}
