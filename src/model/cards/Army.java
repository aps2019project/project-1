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
    protected ArrayList<Buff> buffs = new ArrayList<>();
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
                if (buff.getTurns() == 0 && buff.getBuffTImeType() != BuffTImeType.CONTINUOUS) {
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
                if ((boolean) Minion.class.getDeclaredMethod(this.getName() + "OnDefend", Buff.class).invoke(this, buff))
                    return;
            } catch (Exception e){}
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
            String itemName = hero.getPlayer().getUsableItem().getName();
            switch (itemName) {
                case "DamoolArc":
                    if (hero.getAttackType() != MELEE)
                        army.addBuff(new Disarm(1, 1, NORMAL));
                    break;
                case "ShockHammer":
                    army.addBuff(new Disarm(1, 1, NORMAL));
                    break;
            }
        }
        if (this instanceof Minion && ((Minion) this).getSpTime() == ON_ATTACK) {
            try {
                Minion.class.getDeclaredMethod(this.name + "OnAttack", Army.class).invoke(this, army);
            } catch (Exception e){}
            String itemName = this.getPlayer().getUsableItem().getName();
            switch (itemName) {
                case "TerrorHood":
                    army.addBuff(new Weakness(2, AP, 1, NORMAL));
                    break;
                case "PoisonousDagger":
                    army.addBuff(new Poison(1, NORMAL));
                    break;
            }
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
    }
}
