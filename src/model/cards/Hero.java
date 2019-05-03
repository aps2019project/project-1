package model.cards;

import model.Buff.BuffType;
import model.Buff.Disarm;
import model.Buff.Power;
import model.Buff.Stun;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.HERO;

public class Hero extends Army {
    private static ArrayList<Hero> heroes = new ArrayList<>();

    private int mp, coolDown;

    Hero(String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType, String description) {
        super(name, price, description, hp, ap, ar, attackType, HERO);
        this.mp = mp;
        this.coolDown = coolDown;
        heroes.add(this);
        cards.add(this);
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
            new Hero(line[1]
                    , Integer.parseInt(line[2])
                    , Integer.parseInt(line[3])
                    , Integer.parseInt(line[4])
                    , Integer.parseInt(line[6])
                    , Integer.parseInt(line[8])
                    , Integer.parseInt(line[9])
                    , AttackType.valueOf(line[5].toUpperCase())
                    , line[7]);
        }
    }

    @Override
    public String toString() {
        return "Hero{" +
                "mp=" + mp +
                ", coolDown=" + coolDown +
                ", ap=" + ap +
                ", attackType=" + attackType +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }

    public void useSpell() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Hero.class.getDeclaredMethod(this.name).invoke(this);
    }

    public void WhiteDemonSpell(){
        this.addBuff(new Power(4, AP, PERMANENT));
    }

    public void SimorghSpell(){
        ArrayList<Army> array = new ArrayList<>();
        for(Army army : array){
            army.addBuff(new Stun(1, 1, NORMAL));
        }
    }

    public void DragonSpell(){
        Army army = null;
        army.addBuff(new Disarm(1, PERMANENT));
    }

    public void RakhshSpell(){
        Army army = null;
        army.addBuff(new Stun(1, 1, NORMAL));
    }

    public void KaveSpell(){

    }

    public void ArashSpell(){
        ArrayList<Army> array = new ArrayList<>();
        for(Army army : array){
            army.setAp(army.getAp() + 4);
        }
    }

    public void AfsaneSpell(){
        Army army = null;
        army.deleteBuffs(BuffType.POSITIVE);
    }

}
