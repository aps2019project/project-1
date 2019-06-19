package model.cards;

import graphic.Others.ArmyAnimation;
import model.Buff.*;
import model.game.Cell;
import model.game.Player;
import model.other.Account;

import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.BuffEffectType.*;
import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.MINION;

public class Minion extends Army {
    private static ArrayList<Minion> minions = new ArrayList<Minion>();
    private static int lastNumber = 0;
    private int mana;
    private SPTime spTime;
    private boolean haveDeathCurse;
    private Buff specialBuff = null;

    Minion(int number, String name, int price, int hp
            , int ap, int ar, int mana, AttackType attackType
            , SPTime spTime, String description) {
        super(number, name, price, description, hp, ap, ar, attackType, MINION, mana);
        this.mana = mana;
        this.spTime = spTime;
        minions.add(this);
        cards.add(this);
        lastNumber = number;
//        this.animation = new ArmyAnimation("Card/Hero/2.atlas");
    }

    public static int getLastNumber() {
        return lastNumber;
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

    public static void scanMinions(ArrayList<String[]> data) {
        for (String[] line : data) {
            createMinion(line);
        }
    }

    public static void createMinion(String[] line) {
        SPTime spTime = null;

        if (!line[9].equals("-")) {
            spTime = SPTime.valueOf(line[9].toUpperCase().replace(" ", "_"));
        }

        Minion minion = new Minion(Integer.parseInt(line[0])
                ,line[1]
                , Integer.parseInt(line[2])
                , Integer.parseInt(line[4])
                , Integer.parseInt(line[5])
                , Integer.parseInt(line[7])
                , Integer.parseInt(line[3])
                , AttackType.valueOf(line[6].toUpperCase())
                , spTime
                , line[8]);
        if(minion.getNumber() > 40) {
            int col = 10;
            String powerBuffType = null;
            String buffType = line[col++];
            if(buffType.equals("power") || buffType.equals("weakness"))
                powerBuffType = line[col++];
            int value = Integer.parseInt(line[col++]);
            int delay = Integer.parseInt(line[col++]);
            int last = Integer.parseInt(line[col++]);
            TargetType targetType = TargetType.valueOf(line[col++].toUpperCase());
            Buff buff = new Buff(BuffType.valueOf(buffType.toUpperCase()), value, delay, last, targetType);
            if(powerBuffType != null)
                buff.setPowerBuffType(PowerBuffType.valueOf(powerBuffType.toUpperCase()));
            minion.setSpecialBuff(buff);
            minions.add(minion);
            cards.add(minion);
            if(Account.getCurrentAccount() != null) {
                minion.setUserName(Account.getCurrentAccount().getUsername());
                Account.getCurrentAccount().addCardToCollection(minion);
            }
        }
    }

    @Override
    public String toString() {
        return "mana=" + mana +
                ", spTime=" + spTime +
                ", ap=" + ap +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type;
    }

    public void PersianSwordsmanOnAttack(Army army) {
        army.addBuff(new Buff(STUN, 1, 1, NORMAL));

    }

    public void TuranianSpyOnAttack(Army army) {
        Buff disarm = new Buff(DISARM, 1, 1 , NORMAL);
        army.addBuff(disarm);
        Buff poison = new Buff(POISON, 4, NORMAL);
        army.addBuff(poison);
    }

    public void EagleOnSpawn(Player player, Cell cell) {
        Buff buff = new Buff(POWER, 10, 1, CONTINUOUS);
        buff.setPowerBuffType(HP);
        this.addBuff(buff);
    }

    public void OneEyedGiantOnDeath(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesAround(cell).getArmy();
        for (Army army : array) {
            army.getDamaged(2, this);
        }
    }

    public void PoisonousSnakeOnAttack(Army army) {
        Buff poison = new Buff(POISON, 3, NORMAL);
        army.addBuff(poison);
    }

    public void HugeSnakeOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getEnemiesInDistance2(cell).getArmy();
        for (Army army : array) {
            army.addBuff(new Buff(UNHOLY, 1, PERMANENT));
        }
    }

    public void WhiteWolfOnAttack(Army army) {
        ArrayList<Integer> bleed = new ArrayList<Integer>();
        bleed.add(6);
        bleed.add(4);
        Buff buff = new Buff(BLEEDING, 1, bleed.size(), NORMAL);
        buff.setBleeding(bleed);
        army.addBuff(buff);
    }

    public void PanterOnAttack(Army army) {
        ArrayList<Integer> bleed = new ArrayList<Integer>();
        bleed.add(8);
        Buff buff = new Buff(BLEEDING, 1, bleed.size(), NORMAL);
        buff.setBleeding(bleed);
    }

    public void WolfOnAttack(Army army) {
        ArrayList<Integer> bleed = new ArrayList<Integer>();
        bleed.add(6);
        Buff buff = new Buff(BLEEDING, 1, bleed.size(), NORMAL);
        buff.setBleeding(bleed);
    }

    public void WitchPassive(Player player, Cell cell) {
        ArrayList<Army> array = player.getFriendsAround(cell).getArmy();
        array.add(this);
        for (Army army : array) {
            Buff buff = new Buff(POWER, 2, 1, NORMAL);
            buff.setPowerBuffType(AP);
            army.addBuff(buff);
            Buff buff1 = new Buff(WEAKNESS, 1, 1, NORMAL);
            buff1.setPowerBuffType(HP);
            army.addBuff(buff1);
        }
    }

    public void GrandWitchPassive(Player player, Cell cell) {
        ArrayList<Army> array = player.getFriendsAround(cell).getArmy();
        array.add(this);
        for (Army army : array) {
            Buff buff = new Buff(POWER, 2, 1, NORMAL);
            buff.setPowerBuffType(AP);
            army.addBuff(buff);
            army.addBuff(new Buff(HOLY, 1, 1, CONTINUOUS));
        }
    }

    public void GoblinOnSpawn(Player player, Cell cell) {
        ArrayList<Army> array = player.getInGameCards();
        for (Army army : array) {
            if (army instanceof Hero) continue;
            Buff buff = new Buff(POWER, 1, 1, CONTINUOUS);
            buff.setPowerBuffType(AP);
            army.addBuff(buff);
        }
    }

    public boolean WildHogOnDefend(Buff buff) {
        return buff.getBuffType() == DISARM;
    }

    public boolean PiranOnDefend(Buff buff) {
        return buff.getBuffType() == POISON;
    }

    public boolean GivOnDefend(Buff buff) {
        return buff.getBuffEffectType() == NEGATIVE;
    }

    public void BahmanOnSpawn(Player player, Cell cell) {
            Army army = Army.getRandomArmy(player.getEnemyPlayer().getInGameCards());
            army.setHp(army.getHp() - 16);
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
            army.addBuff(new Buff(STUN, 1, 1, NORMAL));
        }
    }

    public void FuladZerehOnSpawn(Player player, Cell cell) {
        this.addBuff(new Buff(HOLY, 12, 1, CONTINUOUS));
    }

    public void  SiavashOnDeath(Player player, Cell cell) {
        player.getEnemyPlayer().getHero().getDamaged(6, null);
    }

    public void checkOnSpawn(Player player, Cell cell) {
        if(specialBuff != null && spTime == SPTime.ON_SPAWN){
            this.addBuff(specialBuff);
            return;
        }
        try{
            Minion.class.getDeclaredMethod(this.getName() +"OnSpawn", Player.class, Cell.class).invoke(this, player, cell);
        } catch (Exception n){}
        if(this.getPlayer().getUsableItem() == null) return;
        String itemName = this.getPlayer().getUsableItem().getName();
        if ("AssassinationDagger".equals(itemName)) {
            player.getEnemyPlayer().getHero().getDamaged(1, null);
        } else if ("Baptism".equals(itemName)) {
            this.addBuff(new Buff(HOLY, 1, 2, NORMAL));
        }
    }

    public void checkPassive(Player player, Cell cell) {
        if(spTime != SPTime.PASSIVE) return;
        if(specialBuff != null){
            this.addBuff(specialBuff);
        }else {
            try{
                Minion.class.getDeclaredMethod(this.getName() +"OnSpawn", Player.class, Cell.class).invoke(this, player, cell);
            } catch (Exception n){ n.printStackTrace();}
        }
    }

    public void chekcOnDeath(Player player, Cell cell) {
        if (this.getSpTime() == SPTime.ON_DEATH){
            try{
                Minion.class.getDeclaredMethod(this.getName() +"OnDeath", Player.class, Cell.class).invoke(this, player, cell);
            } catch (Exception n){}
        }
        if(this.getPlayer().getUsableItem() != null) {
            String itemName = this.getPlayer().getUsableItem().getName();
            if ("SoulEater".equals(itemName)) {
                Buff buff = new Buff(POWER, 1, PERMANENT);
                buff.setPowerBuffType(AP);
                Army.getRandomArmy(player.getInGameCards()).addBuff(buff);
            }
        }
        if(this.haveDeathCurse){
            Army army = player.getNearestEnemy(cell);
            army.getDamaged(8, null);
        }
    }

    public void checkOnDefend(Army army){
        if(this.getSpTime() != SPTime.ON_DEFEND) return;
        if(this.specialBuff != null){
            army.addBuff(this.specialBuff);
        }
    }

    public Buff getSpecialBuff() {
        return specialBuff;
    }

    public void setSpecialBuff(Buff specialBuff) {
        this.specialBuff = specialBuff;
    }
}
