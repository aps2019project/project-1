package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.CellEffect;
import model.game.Player;
import model.other.Account;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.HERO;

public class Hero extends Army {
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private static int lastNumber = 0;

    private int mp, coolDown;
    private Buff specialBuff = null;

    Hero(int number, String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType, String description) {
        super(number, name, price, description, hp, ap, ar, attackType, HERO, 0);
        this.mp = mp;
        this.coolDown = coolDown;
        if(number <=10) {
            heroes.add(this);
            cards.add(this);
        }
        lastNumber = number;
    }

    public static int getLastNumber() {
        return lastNumber;
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public int getMp() {
        return mp;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public static void scanHeroes(ArrayList<String[]> data){
        for(String[] line : data){
            createHero(line);
        }
    }

    public static void createHero(String[] line) {
        Hero hero = new Hero(Integer.parseInt(line[0])
                ,line[1]
                , Integer.parseInt(line[2])
                , Integer.parseInt(line[3])
                , Integer.parseInt(line[4])
                , Integer.parseInt(line[6])
                , Integer.parseInt(line[8])
                , Integer.parseInt(line[9])
                , AttackType.valueOf(line[5].toUpperCase())
                , line[7]);
        if(hero.getNumber() > 10) {
            String buffType = line[11];
            int value = Integer.parseInt(line[12]);
            int delay = Integer.parseInt(line[13]);
            int last = Integer.parseInt(line[14]);
            TargetType targetType = TargetType.valueOf(line[15].toUpperCase());
            Buff buff = new Buff(POWER, value, delay, last, targetType);
            if(buffType.equals("power") || buffType.equals("weakness"))
                buff.setPowerBuffType(AP);
            hero.setSpecialBuff(buff);

            heroes.add(hero);
            cards.add(hero);
            if(Account.getCurrentAccount() != null) {
                hero.setUserName(Account.getCurrentAccount().getUsername());
                Account.getCurrentAccount().addCardToCollection(hero);
            }
        }
    }

    public Buff getSpecialBuff() {
        return specialBuff;
    }

    public void setSpecialBuff(Buff specialBuff) {
        this.specialBuff = specialBuff;
    }

    @Override
    public String toString() {
        return "mp=" + mp +
                ", coolDown=" + coolDown +
                ", ap=" + ap +
                ", attackType=" + attackType +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type;
    }

    public void useSpell(Player player) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(this.specialBuff != null) {
            Army army = player.getSelectedCardPlace().getInsideArmy();
            if(army == null ) return;
            if(specialBuff.getTargetType() == TargetType.FRIEND && !player.isFriend(army)) return;
            if(specialBuff.getTargetType() == TargetType.ENEMY && player.isFriend(army)) return;
            army.addBuff(specialBuff);
        } else
            Hero.class.getDeclaredMethod(this.name + "Spell", Player.class).invoke(this, player);
    }

    public void WhiteDemonSpell(Player player){
        Buff buff = new Buff(POWER, 4, PERMANENT);
        buff.setPowerBuffType(AP);
        this.addBuff(buff);
    }

    public void SimorghSpell(Player player){
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for(Army army : array){
            army.addBuff(new Stun(1, 1, NORMAL));
        }
    }

    public void DragonSpell(Player player){
        Army army = player.getOneEnemy();
        army.addBuff(new Disarm(1, PERMANENT));
    }

    public void RakhshSpell(Player player){
        Army army = player.getOneEnemy();
        army.addBuff(new Stun(1, 1, NORMAL));
    }

    public void KaveSpell(Player player){
        Cell cell = player.getOneCell();
        cell.setEffect(CellEffect.HOLY);
    }

    public void ArashSpell(Player player){
        ArrayList<Army> array = player.getEnemiesInHeroRow().getArmy();
        for(Army army : array){
            army.setAp(army.getAp() + 4);
        }
    }

    public void AfsaneSpell(Player player){
        Army army = player.getOneEnemy();
        System.out.println(army.getName());
        army.deleteBuffs(BuffEffectType.POSITIVE);
    }

    public void EsfandiarSpell(Player player){
        this.addBuff(new Holy(3, CONTINUOUS));
    }

}
