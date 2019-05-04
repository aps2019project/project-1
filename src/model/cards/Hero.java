package model.cards;

import model.Buff.*;
import model.game.Player;

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

    public void useSpell(Player player) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Hero.class.getDeclaredMethod(this.name + "Spell", Player.class).invoke(this, player);
    }

    public void WhiteDemonSpell(Player player){
        this.addBuff(new Power(4, AP, PERMANENT));
    }

    public void SimorghSpell(Player player){
        ArrayList<Army> array = player.getEnemyCardsInGame();
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

    }

    public void ArashSpell(Player player){
        ArrayList<Army> array = player.getEnemiesInHeroRow();
        for(Army army : array){
            army.setAp(army.getAp() + 4);
        }
    }

    public void AfsaneSpell(Player player){
        Army army = player.getOneEnemy();
        army.deleteBuffs(BuffType.POSITIVE);
    }

    public void EsfandiarSpell(Player player){
        this.addBuff(new Holy(3, CONTINUOUS));
    }

}
