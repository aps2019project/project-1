package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.Player;

import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.MINION;

public class Minion extends Army {
    private static ArrayList<Minion> minions = new ArrayList<>();
    private int mana;
    private SPTime spTime;
    private Race race;
    private boolean haveDeathCurse;

    Minion(int number, String name, int price, int hp
            , int ap, int ar, int mana, AttackType attackType
            , Race race, SPTime spTime, String description) {
        super(number, name, price, description, hp, ap, ar, attackType, MINION);
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

    public boolean HaveDeathCurse() {
        return haveDeathCurse;
    }

    public void setHaveDeathCurse(boolean haveDeathCurse) {
        this.haveDeathCurse = haveDeathCurse;
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

    public static void scanMinions(ArrayList<String[]> data) {
        for (String[] line : data) {
            SPTime spTime = null;

            if (!line[9].equals("-")) {
                spTime = SPTime.valueOf(line[9].toUpperCase().replace(" ", "_"));
            }

            Race race = null;
            if (!line[10].equals("-")) {
                race = Race.valueOf(line[10].toUpperCase());
            }

            new Minion(Integer.parseInt(line[0])
                    ,line[1]
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
    public String toString() {
        return "mana=" + mana +
                ", spTime=" + spTime +
                ", race=" + race +
                ", ap=" + ap +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type;
    }

    public void PersianSwordsmanOnAttack(Army army) {
        army.addBuff(new Stun(1, 1, NORMAL));

    }

    public void TuranianSpyOnAttack(Army army) {
        army.addBuff(new Disarm(1, 1, NORMAL));
        army.addBuff(new Poison(4, NORMAL));
    }

    public void EagleOnSpawn(Player player, Cell cell) {
        this.addBuff(new Power(10, HP, 1, CONTINUOUS));
    }

    public void OneEyedGiantOnDeath(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesAround(cell).getArmy();
        for (Army army : array) {
            army.getDamaged(2, this);
        }
    }

    public void PoisonousSnakeOnAttack(Army army) {
        army.addBuff(new Poison(3, NORMAL));
    }

    public void HugeSnakeOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesInDistance2(cell).getArmy();
        for (Army army : array) {
            army.addBuff(new Unholy(1, PERMANENT));
        }
    }

    public void WhiteWolfOnAttack(Army army) {
        ArrayList<Integer> bleed = new ArrayList<Integer>();
        bleed.add(6);
        bleed.add(4);
        army.addBuff(new Bleeding(bleed));
    }

    public void PanterOnAttack(Army army) {
        ArrayList<Integer> bleed = new ArrayList<Integer>();
        bleed.add(8);
        army.addBuff(new Bleeding(bleed));
    }

    public void WolfOnAttack(Army army) {
        ArrayList<Integer> bleed = new ArrayList<Integer>();
        bleed.add(6);
        army.addBuff(new Bleeding(bleed));
    }

    public void WitchPassive(Player player, Cell cell) {
        ArrayList<Army> array = player.getFriendsAround(cell).getArmy();
        array.add(this);
        for (Army army : array) {
            army.addBuff(new Power(2, AP, 1, NORMAL));
            army.addBuff(new Weakness(1, HP, 1, NORMAL));
        }
    }

    public void GrandWitchPassive(Player player, Cell cell) {
        ArrayList<Army> array = player.getFriendsAround(cell).getArmy();
        array.add(this);
        for (Army army : array) {
            army.addBuff(new Power(2, AP, 1, NORMAL));
            army.addBuff(new Holy(1, 1, CONTINUOUS));
        }
    }

    public void GoblinOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getInGameCards();
        for (Army army : array) {
            if (army instanceof Hero) continue;
            army.addBuff(new Power(1, AP, 1, CONTINUOUS));
        }
    }

    public boolean WildHogOnDefend(Buff buff) {
        return buff instanceof Disarm;
    }

    public boolean PiranOnDefend(Buff buff) {
        return buff instanceof Poison;
    }

    public boolean GivOnDefend(Buff buff) {
        return buff.getBuffType() == BuffType.NEGATIVE;
    }

    public void BahmanOnSpawn(Player player, Cell cell) {
        while (true) {
            Army army = Army.getRandomArmy(player.getEnemyPlayer().getInGameCards());
            if (army instanceof Hero) continue;
            army.setHp(army.getHp() - 16);

        }
    }

    public boolean AshkbousOnDefend(Army army) {
        return army.getAp() < this.getAp();
    }

    public void TwoHeadedGiantOnAttack(Army army) {
        army.deleteBuffs(POSITIVE);
    }

    public void NaneSarmaOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesAround(cell).getArmy();
        for(Army army : array) {
            if(army instanceof Hero) continue;
            army.addBuff(new Stun(1, 1, NORMAL));
        }
    }

    public void FuladZerehOnSpawn(Player player, Cell cell) {
        this.addBuff(new Holy(12, 1, CONTINUOUS));
    }

    public void  SiavashOnDeath(Player player, Cell cell) {
        player.getEnemyPlayer().getHero().getDamaged(6, null);
    }

    public void checkOnSpawn(Player player, Cell cell) {
        if (this.getSpTime() == SPTime.ON_SPAWN){
            try{
                Minion.class.getDeclaredMethod(this.getName() +"OnSpawn", Player.class, Cell.class).invoke(this, player, cell);
            } catch (Exception n){}
        }
        String itemName = this.getPlayer().getUsableItem().getName();
        switch (itemName){
            case "AssassinationDagger":
                player.getEnemyPlayer().getHero().getDamaged(1, null);
                break;
            case "Baptism":
                this.addBuff(new Holy(1, 2, NORMAL));
                break;
        }
    }

    public void chekcOnDeath(Player player, Cell cell) {
        if (this.getSpTime() == SPTime.ON_DEATH){
            try{
                Minion.class.getDeclaredMethod(this.getName() +"OnDeath", Player.class, Cell.class).invoke(this, player, cell);
            } catch (Exception n){}
        }
        String itemName = this.getPlayer().getUsableItem().getName();
        switch (itemName){
            case "SoulEater":
                Army.getRandomArmy(player.getInGameCards()).addBuff(new Power(1, AP, PERMANENT));
                break;
        }
        if(this.haveDeathCurse){
            Army army = player.getNearestEnemy(cell);
            army.getDamaged(8, null);
        }
    }

}
