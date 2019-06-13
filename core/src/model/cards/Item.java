package model.cards;

import model.Buff.Buff;
import model.Buff.BuffType;
import model.Buff.Holy;
import model.game.Cell;
import model.game.Player;
import model.variables.CardsArray;

import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.AttackType.*;
import static model.cards.CardType.ITEM;

public class Item extends Card {
    private static ArrayList<Item> items = new ArrayList<Item>();
    private static CardsArray collectableItems = new CardsArray();
    private static int lastNumebr = 0;
    private ItemType itemType;


    public Item(int number, String name, int price, ItemType itemType, String description) {
        super(number, name, price, description, ITEM, 0);
        this.itemType = itemType;
        items.add(this);
        cards.add(this);
        if(itemType == ItemType.COLLECTIBLE) collectableItems.add(this);
        lastNumebr = number;
    }

    public static int getLastNumebr() {
        return lastNumebr;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static CardsArray getCollectableItems() {
        return collectableItems;
    }

    public ItemType getItemType() {
        return itemType;
    }


    public static void scanItems(ArrayList<String[]> data) {
        for (String[] line : data) {
            new Item(Integer.parseInt(line[0])
                    ,line[1]
                    , Integer.parseInt(line[2])
                    , ItemType.valueOf(line[3].toUpperCase())
                    , line[4]);
        }
    }

    @Override
    public String toString() {
        return "itemType=" + itemType +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type;
    }

    public static void HonorShieldUsbale(Player player) {
        player.getHero().addBuff(new Holy(12, PERMANENT));
    }

    public static void SimurghFeatherUsable(Player player) {
        Hero hero = player.getEnemyPlayer().getHero();
        if (hero.getAttackType() == RANGED || hero.getAttackType() == HYBRID) {
            hero.setAp(hero.getAp() - 2);
        }
    }

    public static void NooshdaruCollectible(Player player, Army army) {
        army.setHp(army.getHp() + 6);
    }

    public static void TwoHeadArrowCollectible(Player player, Army army) {
        if(army.getAttackType() != MELEE) {
            army.setAp(army.getAp() + 2);
        }
    }

    public static void ElixirCollectible(Player player, Army army) {
        if(army instanceof Minion) {
            army.setHp(army.getHp() + 3);
            Buff buff = new Buff(BuffType.POWER, 3, NORMAL);
            buff.setPowerBuffType(AP);
            army.addBuff(buff);
        }

    }

    public static void ManaPotionCollectible(Player player, Army army) {
        player.useManaPotion();
    }

    public static void RevengeousPotionCollectible(Player player, Army army) {
        army.addBuff(new Holy(10, 2, NORMAL));
    }

    public static void DeathCurseCollectible(Player player, Army army) {
        if(!(army instanceof Minion)) return;
        ((Minion) army).setHaveDeathCurse(true);
    }

    public static void RandomDamageCollectible(Player player, Army army) {
        army.setAp(army.getAp() + 2);
    }

    public static void BladesOfAgilityCollectible(Player player ,Army army) {
        army.setAp(army.getAp() + 6);
    }

    public static void ChiniSwordCollectible(Player player, Army army) {
        if (army.getAttackType() == MELEE) {
            army.setAp(army.getAp() + 5);
        }
    }



}