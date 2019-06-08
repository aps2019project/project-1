package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.CellEffect;
import model.game.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.HERO;

public class Hero extends Army {
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private static int lastNumebr = 0;

    private int mp, coolDown;
    private Buff specialBuff = null;

    Hero(int number, String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType, String description) {
        super(number, name, price, description, hp, ap, ar, attackType, HERO, 0);
        this.mp = mp;
        this.coolDown = coolDown;
        heroes.add(this);
        cards.add(this);
        lastNumebr = number;
    }

    public static int getLastNumebr() {
        return lastNumebr;
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
                switch (buffType) {
                    case "power":
                        Power power = new Power(value, AP, delay, last, targetType);
                        hero.setSpecialBuff(power);
                        break;
                    case "weakening":
                        break;
                    case "holy":
                        break;
                    case "poison":
                        break;
                    case "stun":
                        break;
                    case "disarm":
                        break;
                }
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
        }
        Hero.class.getDeclaredMethod(this.name + "Spell", Player.class).invoke(this, player);
    }

    public void WhiteDemonSpell(Player player){
        this.addBuff(new Power(4, AP, PERMANENT));
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
        army.deleteBuffs(BuffType.POSITIVE);
    }

    public void EsfandiarSpell(Player player){
        this.addBuff(new Holy(3, CONTINUOUS));
    }

}
