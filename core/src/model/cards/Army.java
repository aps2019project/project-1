package model.cards;

import graphic.Others.ArmyAnimation;
import graphic.Others.BattlePopUp;
import graphic.screen.BattleScreen;
import model.Buff.*;
import model.game.Cell;
import model.game.CellEffect;
import model.game.Game;
import model.game.Player;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static model.Buff.BuffTImeType.*;
import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.AttackType.*;
import static model.cards.SPTime.*;

public class Army extends Card {
    protected int hp, ap, ar;
    protected AttackType attackType;
    protected ArrayList<Buff> buffs = new ArrayList<Buff>();
    protected boolean isStunned;
    protected boolean isDisarmed;
//    protected ArmyAnimation animation = new ArmyAnimation("Card/Hero/1.atlas");

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
//                System.out.println(buff.getTurns());
                buff.decreaseTurns();
//                System.out.println(buff.getTurns());
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
                    BattleScreen.getPopUps().add( new BattlePopUp("Continuous", army.whereItIs.getScreenX(), army.whereItIs.getScreenY()));
                    buff.setTurns(1);
                }
            }
        }
    }

    public static void checkPoisonAndBleeding(ArrayList<Army> array) {
        for (Army army : array) {
            for (Buff buff : army.getBuffs()) {
                if (buff.getBuffType() == POISON){
                    army.setHp(army.getHp() - army.haveBuff(BuffType.POISON));
                    BattleScreen.getPopUps().add(new BattlePopUp("Poison", army.whereItIs.getScreenX(), army.whereItIs.getScreenY()));
                }else if (buff.getBuffType() == BLEEDING) {
                    BattleScreen.getPopUps().add(new BattlePopUp("Bleeding", army.whereItIs.getScreenX(), army.whereItIs.getScreenY()));
                    army.setHp(army.getHp() - buff.getFirstBleeding());
                }
            }
            if(army.getWhereItIs().getCellEffect() == CellEffect.POISON){
                army.getWhereItIs().getEffect();
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

    public int haveBuff(BuffType buffType) {
        int sum = 0;
        for (Buff buff : this.getBuffs()) {
            if (buff.getBuffType() == buffType && buff.getTurns() != 0) {
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
        switch (buff.getBuffType()){
            case DISARM:
                BattleScreen.getPopUps().add(new BattlePopUp("Disarm", this.whereItIs.getScreenX(), this.whereItIs.getScreenY()));
                this.isDisarmed = true;
                break;
            case STUN:
                BattleScreen.getPopUps().add(new BattlePopUp("Stun", this.whereItIs.getScreenX(), this.whereItIs.getScreenY()));
                this.isStunned = true;
                break;
            case POWER:
                BattleScreen.getPopUps().add(new BattlePopUp("Power", this.whereItIs.getScreenX(), this.whereItIs.getScreenY()));
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
                BattleScreen.getPopUps().add(new BattlePopUp("Weakness", this.whereItIs.getScreenX(), this.whereItIs.getScreenY()));
                switch (buff.getPowerBuffType()) {
                    case AP:
                        this.ap -= buff.getNumber();
                        if(this.ap < 0) this.ap = 0;
                        break;
                    case HP:
                        this.hp -= buff.getNumber();
                        if(this.hp < 0) this.hp = 0;
                        break;
                }
                break;
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
                        if(this.ap < 0) this.ap = 0;
                        break;
                    case HP:
                        this.hp -= buff.getNumber();
                        if(this.hp < 0) this.hp = 0;
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
                army.addBuff(new Buff(POISON, 3, NORMAL));
            if(hero.getPlayer().getUsableItem() == null) return;
            try {
                String itemName = hero.getPlayer().getUsableItem().getName();
                if ("DamoolArc".equals(itemName)) {
                    if (hero.getAttackType() != MELEE)
                        army.addBuff(new Buff(DISARM, 1, 1, NORMAL));
                } else if ("ShockHammer".equals(itemName)) {
                    army.addBuff(new Buff(DISARM, 1, 1, NORMAL));
                }
            } catch (NullPointerException npe) {}
        }
        if (this instanceof Minion) {
            Minion minion = (Minion) this;
            if(minion.getSpTime() != ON_ATTACK) return;
            BattleScreen.getPopUps().add( new BattlePopUp("ON ATTACK", this.whereItIs.getScreenX(), this.whereItIs.getScreenY()));

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
                    Buff buff = new Buff(WEAKNESS, 2, 1, NORMAL);
                    buff.setPowerBuffType(AP);
                    army.addBuff(buff);
                } else if ("PoisonousDagger".equals(itemName)) {
                    army.addBuff(new Buff(POISON, 1, NORMAL));
                }
            } catch (NullPointerException npe) {}
        }
    }

    public void getDamaged(int number, Army army) {
        if(this.getName().equals("Giv")) return;
        int holyBuffs = this.haveBuff(HOLY);
        if(holyBuffs > 0) {
            BattleScreen.getPopUps().add(new BattlePopUp("Holy", this.whereItIs.getScreenX(), this.whereItIs.getScreenY()));
        }
        int unholyBuffs = this.haveBuff(UNHOLY);
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

    public boolean canMoveTo(Cell cell) {
        ArrayList<Cell> cells = Game.getCurrentGame().getAllCellsWithUniqueDistance(this.getWhereItIs(), 2);
        for(Cell cell1 : cells){
            if(cell1 == cell && cell1.isEmpty())
                return true;
        }
        return false;
    }
}
