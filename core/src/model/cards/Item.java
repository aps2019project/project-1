package model.cards;

import model.Buff.Buff;
import model.Buff.BuffType;
import model.game.Player;
import model.variables.CardsArray;

import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.BuffType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.AttackType.*;
import static model.cards.CardType.ITEM;

public class Item extends Card {
    private static ArrayList<Item> items = new ArrayList<Item>();
    private static CardsArray collectableItems = new CardsArray();
    private static int lastNumebr = 0;
    private ItemType itemType;


    public Item(int number, int gifNumber,  String name, int price, ItemType itemType, String description) {
        super(number, name, price, description, ITEM, 0);
        this.itemType = itemType;
        lastNumebr = number;
        gifPath = "Card/Item/" +gifNumber+".atlas";
        items.add(this);
        cards.add(this);
        if(itemType == ItemType.COLLECTIBLE) collectableItems.add(this);
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
            createItem(line);
        }
    }

    public static void scanItemsArrayList(ArrayList<String> data){
        data.remove(0);
        for(String string : data) {
            String[] array = string.split(",");
            createItem(array);
        }
    }

    public static void createItem(String[] line) {
        new Item(Integer.parseInt(line[1])
                , Integer.parseInt(line[2])
                , line[3]
                , Integer.parseInt(line[4])
                , ItemType.valueOf(line[5].toUpperCase())
                , line[6]);
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
        player.getHero().addBuff(new Buff(HOLY, 12, PERMANENT));
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
        army.addBuff(new Buff(HOLY, 10, 2, NORMAL));
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