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

    Spell(int number, String name, int price, int mana, String description, String target) {
        super(number, name, price, description, SPELL, mana);
        this.mana = mana;
        spells.add(this);
        cards.add(this);
        this.targetDescription = target;
        lastNumber = number;
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
        Spell spell = new Spell(Integer.parseInt(line[0])
                ,line[1]
                , Integer.parseInt(line[2])
                , Integer.parseInt(line[3])
                , line[5]
                , line[4]);
        if(spell.getNumber() > 20) {
            int col = 6;
            spell.spellTargetType = SpellTargetType.valueOf(line[4].toUpperCase().replace(" ", "_"));
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
        Spell.class.getDeclaredMethod(spellname + "Effect", Player.class).invoke(null, player);
    }

    public static void TotalDisarmEffect(Player player) {
        Army army = player.getOneEnemy();
        army.addBuff(new Disarm(1, PERMANENT));
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
        army.addBuff(new Holy(4, 3, NORMAL));
        army.addBuff(new Disarm(1, 3, NORMAL));
    }

    public static void AllDisarmEffect(Player player) {
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for (Army army : array) {
            army.addBuff(new Disarm(1, 1, NORMAL));
        }
    }

    public static void AllPoisonEffect(Player player) {
        ArrayList<Army> array = player.getEnemyPlayer().getInGameCards();
        for (Army army : array) {
            army.addBuff(new Poison(4, NORMAL));
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
        army.addBuff(new Weakness(6, HP, PERMANENT));
        army.addBuff(new Holy(2, 3, NORMAL));
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
        army.addBuff(new Weakness(4, AP, PERMANENT));
    }

    public static void SacrificeEffect(Player player){
        Army army = player.getOneFriendMinion();
        army.addBuff(new Weakness(6, HP, PERMANENT));
        Buff buff = new Buff(POWER, 8, PERMANENT);
        buff.setPowerBuffType(AP);
        army.addBuff(buff);
    }

    public static void KingsGuardEffect(Player player){
        Army army = player.getRandomMinionAroundFriendHero();
        army.setHp(0);
    }

    public static void ShockEffect(Player player) {
        Army army = player.getOneEnemy();
        army.addBuff(new Stun(1, 2, NORMAL));
    }

    public Buff getSpecialBuff() {
        return specialBuff;
    }

    public void setSpecialBuff(Buff specialBuff) {
        this.specialBuff = specialBuff;
    }
}