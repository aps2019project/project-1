package model.cards;

import model.Buff.*;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static model.cards.AttackType.*;
import static model.cards.SPTime.*;

public class Army extends Card {
    protected int hp, ap, ar;
    protected AttackType attackType;
    protected ArrayList<Buff> buffs = new ArrayList<>();
    protected boolean isStunned;
    protected boolean isDisarmed;

    public Army(String name, int price, String description, int hp, int ap, int ar, AttackType attackType, CardType cardType) {
        super(name, price, description, cardType);
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public boolean isStuned() {
        return isStunned;
    }

    public boolean isDisarmed() {
        return isDisarmed;
    }

    public void decreaseHp(int decreaseNumber) {
        hp -= decreaseNumber;
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
            if (buff.getClass() == buffClass && buff.getTurns() != 0) {
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
            this.deactivateBuff(buff);
        }
    }

    public void activateBuff(Buff buff) {
        if (buff instanceof Disarm) {
            this.isDisarmed = true;
        } else if (buff instanceof Stun) {
            this.isStunned = true;
        } else if (buff instanceof Power) {
            switch (((Power) buff).getType()) {
                case AP:
                    this.ap += buff.getNumber();
                    break;
                case HP:
                    this.hp += buff.getNumber();
                    break;
            }
        } else if (buff instanceof Weakness) {
            switch (((Weakness) buff).getType()) {
                case AP:
                    this.ap -= buff.getNumber();
                    break;
                case HP:
                    this.hp -= buff.getNumber();
                    break;
            }
        }
    }

    public void deactivateBuff(Buff buff) {
        if (buff instanceof Disarm && this.haveBuff(Disarm.class) == 0) {
            this.isDisarmed = false;
        } else if (buff instanceof Stun && this.haveBuff(Stun.class) == 0) {
            this.isStunned = false;
        } else if (buff instanceof Power) {
            switch (((Power) buff).getType()) {
                case AP:
                    this.ap -= buff.getNumber();
                    break;
                case HP:
                    this.hp -= buff.getNumber();
                    break;
            }
        } else if (buff instanceof Weakness) {
            switch (((Weakness) buff).getType()) {
                case AP:
                    this.ap += buff.getNumber();
                    break;
                case HP:
                    this.hp += buff.getNumber();
                    break;
            }
        }
    }

    public void attack(Army army) {
        if (this.isStunned) return;
        army.getDamaged(this.getAp(), army);
        this.checkOnAttack(army);
    }

    public void checkOnAttack(Army army) {
        if (this.getName().equals("Zahack")) {
            army.addBuff(new Poison(3, BuffTImeType.NORMAL));
        }
        if (this instanceof Minion && ((Minion) this).getSpTime() == ON_ATTACK) {

        }
    }

    public void getDamaged(int number, Army army) {
        int holyBuffs = this.haveBuff(Holy.class);
        try {
            if (army.getName().equals("PredatorLion")) holyBuffs = 0;
        } catch (NullPointerException n){}
        if (holyBuffs < number) {
            number -= holyBuffs;
            this.setHp(this.getHp() - number);
        }
        this.checkOnDefend(army);
    }

    public void checkOnDefend(Army army) {
        if (this instanceof Minion && ((Minion) this).getSpTime() == ON_DEFEND) {
            if (army == null) {

            } else {

            }
        }
    }

    public void counterAttack(Army army) {
        if (this.isDisarmed) return;
        army.getDamaged(this.getAp(), army);
        this.checkOnAttack(army);
    }

    public static Army getRandomArmy(ArrayList<Army> array){
        int random = (new Random()).nextInt(array.size());
        return array.get(random);
    }

}
