package model.cards;

import model.Buff.*;
import model.game.Cell;
import model.game.CellEffect;
import model.game.Player;
import model.other.Account;

import static model.Buff.BuffEffectType.*;
import static model.Buff.BuffTImeType.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.CardType.SPELL;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<Spell>();
    private static int lastNumber = 0;
    private int mana;
    private String targetDescription;
    private SpellTargetType spellTargetType;
    private Buff specialBuff = null;

    Spell(int number, int gifNumber, String name, int price, int mana, String description, String target) {
        super(number, name, price, description, SPELL, mana);
        this.mana = mana;
        gifPath = "Card/Spell/" +gifNumber+".atlas";
        if(number > lastNumber)
            lastNumber = number;
        this.targetDescription = target;
        if(number <=20) {
            spells.add(this);
            cards.add(this);
        }
    }

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public static int getLastNumber() {
        return lastNumber;
    }

    public int getMana() {
        return mana;
    }

    public SpellTargetType getSpellTargetType() {
        return spellTargetType;
    }

    public static void scanSpells(ArrayList<String[]> data) {
        for (String[] line : data) {
            createSpell(line);
        }
    }

    public static void createSpell(String[] line) {
        Spell spell = new Spell(Integer.parseInt(line[1])
                , Integer.parseInt(line[2])
                , line[3]
                , Integer.parseInt(line[4])
                , Integer.parseInt(line[5])
                , line[7]
                , line[6]);
        if(spell.getNumber() > 20) {
            int col = 8;
            spell.spellTargetType = SpellTargetType.valueOf(line[6].toUpperCase().replace(" ", "_"));
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
            spell.setSpecialBuff(buff);
            spells.add(spell);
            cards.add(spell);
            if(Account.getCurrentAccount() != null) {
                spell.setUserName(Account.getCurrentAccount().getUsername());
                Account.getCurrentAccount().addCardToCollection(spell);
            }
        }
    }

    @Override
    public String toString() {
        return  "mana=" + mana +
                ", spellTarget=" + targetDescription +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type;
    }

    public static void useSpell(Player player, String spellname) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Spell spell = (Spell)Card.getCards().findByName(spellname);
        if(spell.getSpecialBuff() != null){
            spell.useCustomSpell(player);
        }
        Spell.class.getDeclaredMethod(spellname + "Effect", Player.class).invoke(null, player);
    }

    public void useCustomSpell(Player player) {
        ArrayList<Army> targets = new ArrayList<Army>();
        switch (spellTargetType){
            case ONE:
                Cell cell = player.getOneCell();
                if(cell.getInsideArmy() != null)
                    targets.add(cell.getInsideArmy());
                break;
            case SQUARE_2:
                targets = getArmiesFromCells(player.getSquare2());
                break;
            case SQUARE_3:
                targets = getArmiesFromCells(player.getSquare3());
                break;
            case ALL_ENEMIES:
                targets = player.getEnemyPlayer().getInGameCards();
                break;
            case ALL_FRIENDS:
                targets = player.getInGameCards();
                break;
        }
        for(Army army : targets){
            if(specialBuff.getTargetType() == TargetType.FRIEND && !player.isFriend(army)) continue;
            if(specialBuff.getTargetType() == TargetType.ENEMY && player.isFriend(army)) continue;
            army.addBuff(specialBuff);
        }
    }

    public ArrayList<Army> getArmiesFromCells(ArrayList<Cell> cells){
        ArrayList<Army> armies = new ArrayList<Army>();
        for(Cell cell : cells){
            if(cell.getInsideArmy() != null){
                armies.add(cell.getInsideArmy());
            }
        }
        return armies;
    }

    public static void TotalDisarmEffect(Player player) {
        Army army = player.getOneEnemy();
        army.addBuff(new Buff(DISARM, 1, PERMANENT));
    }

    public static void AreaDispelEffect(Player player) {
        ArrayList<Cell> array = player.getSquare2();
        for (Cell cell : array) {
            Army army = cell.getInsideArmy();
            if (army == null) continue;
            if (player.haveCard(army)) {
                army.deleteBuffs(NEGATIVE);
            } else {
                army.deleteBuffs(POSITIVE);
            }
        }
    }

    public static void EmpowerEffect(Player player) {
        Army army = player.getOneFriend();
        army.setAp(army.getAp() + 2);
    }

    public static void FireballEffect(Player player) {
        Army army = player.getOneEnemy();
        army.getDamaged(4, null);
    }

    public static void GodStrengthEffect(Player player) {
        Hero hero = player.getHero();
        hero.setAp(hero.getAp() + 4);
    }

    public static void HellFireEffect(Player player) {
        ArrayList<Cell> array = player.getSquare2();
        for(Cell cell : array) {
            cell.setEffect(CellEffect.FIERY);
        }
    }

    public static void LightingBoltEffect(Player player) {
        Hero hero = player.getEnemyPlayer().getHero();
        hero.getDamaged(8, null);
    }

    public static void PoisonLakeEffect(Player player) {
        ArrayList<Cell> array = player.getSquare3();
        for(Cell cell : array) {
            cell.setEffect(CellEffect.POISON);
        }
    }

    public static void MadnessEffect(Player player) {
        Army army = player.getOneFriend();
        army.addBuff(new Buff(HOLY, 4, 3, NORMAL));
        army.addBuff(new Buff(DISARM, 1, 3, NORMAL));
    }

    public static void AllDisarmEffect(Player player) {
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for (Army army : array) {
            army.addBuff(new Buff(DISARM, 1, 1, NORMAL));
        }
    }

    public static void AllPoisonEffect(Player player) {
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for (Army army : array) {
            army.addBuff(new Buff(POISON,4, NORMAL));
        }
    }

    public static void DispelEffect(Player player) {
        Army army = player.getOneEnemyOrFriend();
        if (player.haveCard(army)) {
            army.deleteBuffs(NEGATIVE);
        } else {
            army.deleteBuffs(POSITIVE);
        }
    }

    public static void HealthWithProfitEffect(Player player) {
        Army army = player.getOneFriend();
        Buff buff = new Buff(WEAKNESS, 6, PERMANENT);
        buff.setPowerBuffType(HP);
        army.addBuff(buff);
        army.addBuff(new Buff(HOLY, 2, 3, NORMAL));
    }

    public static void PowerUpEffect(Player player) {
        Army army = player.getOneFriend();
        Buff buff = new Buff(POWER, 6, PERMANENT);
        buff.setPowerBuffType(AP);
        army.addBuff(buff);
    }

    public static void AllPowerEffect(Player player) {
        ArrayList<Army> array = player.getInGameCards();
        for (Army army : array) {
            Buff buff = new Buff(POWER, 2, PERMANENT);
            buff.setPowerBuffType(AP);
            army.addBuff(buff);
        }
    }

    public static void AllAttackEffect(Player player) {
        ArrayList<Army> array = player.getAllEnemiesInOneColumn().getArmy();
        for (Army army : array) {
            army.getDamaged(6, null);
        }
    }

    public static void WeakeningEffect(Player player) {
        Army army = player.getOneEnemyMinion();
        Buff buff = new Buff(WEAKNESS, 4, PERMANENT);
        buff.setPowerBuffType(AP);
        army.addBuff(buff);
    }

    public static void SacrificeEffect(Player player){
        Army army = player.getOneFriendMinion();
        Buff buff = new Buff(WEAKNESS, 6, PERMANENT);
        buff.setPowerBuffType(HP);
        army.addBuff(buff);
        Buff buff1 = new Buff(POWER, 8, PERMANENT);
        buff1.setPowerBuffType(AP);
        army.addBuff(buff1);
    }

    public static void KingsGuardEffect(Player player){
        Army army = player.getRandomMinionAroundFriendHero();
        army.setHp(0);
    }

    public static void ShockEffect(Player player) {
        Army army = player.getOneEnemy();
        army.addBuff(new Buff(STUN, 1, 2, NORMAL));
    }

    public Buff getSpecialBuff() {
        return specialBuff;
    }

    public void setSpecialBuff(Buff specialBuff) {
        this.specialBuff = specialBuff;
    }
}