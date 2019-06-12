package model.cards;

import model.Buff.*;
import model.game.Game;
import model.game.Player;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static model.Buff.BuffTImeType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.AttackType.*;
import static model.cards.SPTime.*;

public class Army extends Card {
    protected int hp, ap, ar;
    protected AttackType attackType;
    protected ArrayList<Buff> buffs = new ArrayList<Buff>();
    protected boolean isStunned;
    protected boolean isDisarmed;

    public Army(int number, String name, int price, String description, int hp, int ap, int ar, AttackType attackType, CardType cardType, int mana) {
        super(number, name, price, description, cardType, mana);
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

    public Player getPlayer() {
        return Game.getCurrentGame().getPlayer(getAccount());
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

    public boolean isStunned() {
        return isStunned;
    }

    public static void decreaseBuffTurns(ArrayList<Army> array) {
        for (Army army : array) {
            Iterator iterator = army.getBuffs().iterator();
            while (iterator.hasNext()) {
                Buff buff = (Buff) iterator.next();
                buff.decreaseTurns();
                if(buff.getDelay() != 0){
                    buff.decreaseDelay();
                    if(buff.getDelay() == 0)
                        army.activateBuff(buff);
                }
                if (buff.getTurns() == 0 && buff.getBuffTImeType() != BuffTImeType.CONTINUOUS) {
                    army.deactivateBuff(buff);
                    iterator.remove();
                }
            }
        }
    }

    public static void ActivateContinuousBuffs(ArrayList<Army> array) {
        for (Army army : array) {
            for (Buff buff : army.getBuffs()) {
                if (buff.getBuffTImeType() == BuffTImeType.CONTINUOUS) {
                    buff.setTurns(1);
                }
            }
        }
    }

    public static void checkPoisonAndBleeding(ArrayList<Army> array) {
        for (Army army : array) {
            army.setHp(army.getHp() - army.haveBuff(Poison.class));
            for (Buff buff : army.getBuffs()) {
                if (buff instanceof Bleeding) {
                    army.setHp(army.getHp() - ((Bleeding) buff).getFirst());
                }
            }
        }
    }

    public void addBuff(Buff buff) {
        if(this.getName().equals("WildHog") || this.getName().equals("Piran") || this.getName().equals("Giv")){
            try {
                Minion.class.getDeclaredMethod(this.getName() + "OnDefend", Buff.class).invoke(this, buff);
                return;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
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

    public void deleteBuffs(BuffEffectType buffType) {
        Iterator iterator = this.getBuffs().iterator();
        while (iterator.hasNext()) {
            Buff buff = (Buff) iterator.next();
            if (buff.getBuffEffectType() != buffType) {
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
        if(buff.getDelay() != 0) return;
        System.out.println(buff.getBuffType());
        switch (buff.getBuffType()){
            case DISARM:
                this.isDisarmed = true;
                break;
            case STUN:
                this.isStunned = true;
                break;
            case POWER:
                switch (buff.getPowerBuffType()) {
                    case AP:
                        this.ap += buff.getNumber();
                        break;
                    case HP:
                        this.hp += buff.getNumber();
                        break;
                }
                break;
            case WEAKNESS:
                switch (buff.getPowerBuffType()) {
                    case AP:
                        this.ap -= buff.getNumber();
                        break;
                    case HP:
                        this.hp -= buff.getNumber();
                        break;
                }                break;

        }
    }

    public void deactivateBuff(Buff buff) {
        switch (buff.getBuffType()){
            case DISARM:
                this.isDisarmed = false;
                break;
            case STUN:
                this.isStunned = false;
                break;
            case POWER:
                switch (buff.getPowerBuffType()) {
                    case AP:
                        this.ap -= buff.getNumber();
                        break;
                    case HP:
                        this.hp -= buff.getNumber();
                        break;
                }
                break;
            case WEAKNESS:
                switch (buff.getPowerBuffType()) {
                    case AP:
                        this.ap += buff.getNumber();
                        break;
                    case HP:
                        this.hp += buff.getNumber();
                        break;
                }                break;

        }
    }

    public void attack(Army army) {
        if (this.isStunned) return;
        if (army.getName().equals("Ashkbous") && ((Minion)army).AshkbousOnDefend(this)) return;
        army.getDamaged(this.getAp(), army);
        this.checkOnAttack(army);
    }

    public void checkOnAttack(Army army) {
        if (this instanceof Hero) {
            Hero hero = (Hero)this;
            if (hero.getName().equals("Zahack"))
                army.addBuff(new Poison(3, NORMAL));
            if(hero.getPlayer().getUsableItem() == null) return;
            try {
                String itemName = hero.getPlayer().getUsableItem().getName();
                if ("DamoolArc".equals(itemName)) {
                    if (hero.getAttackType() != MELEE)
                        army.addBuff(new Disarm(1, 1, NORMAL));
                } else if ("ShockHammer".equals(itemName)) {
                    army.addBuff(new Disarm(1, 1, NORMAL));
                }
            } catch (NullPointerException npe) {}
        }
        if (this instanceof Minion) {
            Minion minion = (Minion) this;
            if(minion.getSpTime() != ON_ATTACK) return;
            if(minion.getSpecialBuff() != null){
                army.addBuff(minion.getSpecialBuff());
                return;
            }
            try {
                Minion.class.getDeclaredMethod(this.name + "OnAttack", Army.class).invoke(this, army);
            } catch (Exception e){}
            try {
                String itemName = this.getPlayer().getUsableItem().getName();
                if ("TerrorHood".equals(itemName)) {
                    army.addBuff(new Weakness(2, AP, 1, NORMAL));
                } else if ("PoisonousDagger".equals(itemName)) {
                    army.addBuff(new Poison(1, NORMAL));
                }
            } catch (NullPointerException npe) {}
        }
    }

    public void getDamaged(int number, Army army) {
        if(this.getName().equals("Giv")) return;
        int holyBuffs = this.haveBuff(Holy.class);
        int unholyBuffs = this.haveBuff(Unholy.class);
        try {
            if (army.getName().equals("PredatorLion")) holyBuffs = 0;
        } catch (NullPointerException n) {
        }

        if (holyBuffs < number) {
            number -= holyBuffs;
            this.setHp(this.getHp() - number);
        }
        this.setHp(this.getHp() - unholyBuffs);
    }

    public void counterAttack(Army army) {
        if (this.isDisarmed) return;
        army.getDamaged(this.getAp(), army);
        this.checkOnAttack(army);
    }

    public static Army getRandomArmy(ArrayList<Army> array) {
        int random = (new Random()).nextInt(array.size());
        return array.get(random);
    }

    public void attackCombo(Army target, CardsArray array) {
        for(Army army : array.getArmy()) {
            target.getDamaged(army.getAp(), null);
        }
        this.attack(target);
        target.counterAttack(this);
    }
}
