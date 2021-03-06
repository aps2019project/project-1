package model.cards;

import com.badlogic.gdx.graphics.Texture;
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
    private static ArrayList<Hero> heroes = new ArrayList<Hero>();
    private static int lastNumber = 0;

    private int mp, coolDown;
    private Buff specialBuff = null;
    private String iconId;
    private String spellPath;

    Hero(int number,int gifNumber, String name, int price, int hp, int ap, int ar, int mp, int coolDown, AttackType attackType, String description) {
        super(number, name, price, description, hp, ap, ar, attackType, HERO, 0);
        this.mp = mp;
        this.coolDown = coolDown;
        if(number > lastNumber)
            lastNumber = number;
        iconId = "Card/Hero/Icon/" + gifNumber +".png";
        gifPath = "Card/Hero/" +gifNumber+".atlas";
        spellPath = "Card/Hero/general spell/" +gifNumber+".atlas";
        if(name.equals("Rostam")) spellPath = null;
        if(number <=10) {
            heroes.add(this);
            cards.add(this);
        }
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

    public String getIconId() {
        return iconId;
    }

    public static void scanHeroes(ArrayList<String[]> data){
        for(String[] line : data){
            createHero(line);
        }
    }

    public static void scanHeroesArrayList(ArrayList<String> data){
        data.remove(0);
        for(String string : data) {
            String[] array = string.split(",");
            createHero(array);
        }
    }

    public static Hero createHero(String[] line) {
        Hero hero = new Hero(Integer.parseInt(line[1])
                , Integer.parseInt(line[2])
                , line[3]
                , Integer.parseInt(line[4])
                , Integer.parseInt(line[5])
                , Integer.parseInt(line[6])
                , Integer.parseInt(line[8])
                , Integer.parseInt(line[10])
                , Integer.parseInt(line[11])
                , AttackType.valueOf(line[7].toUpperCase())
                , line[9]);
        if(hero.getNumber() > 10) {
            int col = 12;
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
            hero.setSpecialBuff(buff);
            heroes.add(hero);
            cards.add(hero);
            if(Account.getCurrentAccount() != null) {
                hero.setUserName(Account.getCurrentAccount().getUsername());
//                Account.getCurrentAccount().addCardToCollection(hero);
            }
        }
        return hero;
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
            army.addBuff(new Buff(STUN, 1, 1, NORMAL));
        }
    }

    public void DragonSpell(Player player){
        Army army = player.getOneEnemy();
        Buff disarm = new Buff(DISARM, 1, PERMANENT);
        army.addBuff(disarm);
    }

    public void RakhshSpell(Player player){
        Army army = player.getOneEnemy();
        army.addBuff(new Buff(STUN, 1, 1, NORMAL));
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
        this.addBuff(new Buff(HOLY, 3, CONTINUOUS));
    }

    public String getSpellPath() {
        return spellPath;
    }
}
