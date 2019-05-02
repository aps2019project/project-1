package model.cards;

import model.Buff.*;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.Iterator;

public class Army extends Card {
    protected int hp, ap, ar;
    protected AttackType attackType;
    protected ArrayList<Buff> buffs = new ArrayList<>();
    protected boolean isStuned;
    protected boolean isDisarmed;

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
            Iterator iterator = ((Army) card).getBuffs().iterator();
            while (iterator.hasNext()) {
                Buff buff = (Buff) iterator.next();
                buff.decreaseTurns();
                if (buff.getTurns() == 0 && buff.getBuffTImeType() != BuffTImeType.CONTINUOUS) {
                    iterator.remove();
                }
            }
        }
    }

    public static void ActivateContinuousBuffs(CardsArray array) {
        for (Card card : array.getAllCards()) {
            Army army = (Army) card;
            for (Buff buff : army.getBuffs()) {
                if (buff.getBuffTImeType() == BuffTImeType.CONTINUOUS) {
                    buff.setTurns(1);
                }
            }
        }
    }

    public void addBuff(Buff buff) {
        this.buffs.add(buff);
        this.activateBuff(buff);
    }

    public int haveBuff(Class buffClass) {
        int sum = 0;
        for (Buff buff : this.getBuffs()) {
            if (buff.getClass() == buffClass) {
                sum += buff.getNumber();
            }
        }
        return sum;
    }

    public void deleteBuffs(BuffType buffType) {
        Iterator iterator = this.getBuffs().iterator();
        while (iterator.hasNext()) {
            Buff buff = (Buff) iterator.next();
            if (buff.getBuffType() != buffType) {
                continue;
            }
            if (buff.getBuffTImeType() == BuffTImeType.CONTINUOUS) {
                buff.setTurns(0);
            } else {
                iterator.remove();
            }
        }
    }

    public void activateBuff(Buff buff) {
        if (buff instanceof Disarm) {
            this.isDisarmed = true;
        } else if (buff instanceof Stun) {
            this.isStuned = true;
        } else if (buff instanceof Power) {
            switch (((Power) buff).getType()){
                case AP:
                    this.ap += buff.getNumber();
                    break;
                case HP:
                    this.hp += buff.getNumber();
                    break;
            }
        }
    }

    public void deactivateBuff(Buff buff) {

    }

}
