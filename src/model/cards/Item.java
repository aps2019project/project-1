package model.cards;

import model.Buff.Holy;
import model.game.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.cards.AttackType.*;
import static model.cards.CardType.ITEM;

public class Item extends Card {
    private static ArrayList<Item> items = new ArrayList<>();
    private ItemType itemType;

    Item(String name, int price, ItemType itemType, String description) {
        super(name, price, description, ITEM);
        this.itemType = itemType;
        items.add(this);
        cards.add(this);
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public static void scanItems(ArrayList<String[]> data) {
        for (String[] line : data) {
            new Item(line[1]
                    , Integer.parseInt(line[2])
                    , ItemType.valueOf(line[3].toUpperCase())
                    , line[4]);
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemType=" + itemType +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }

    public static void itemEffect(Player player, String itemName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Spell.class.getDeclaredMethod(itemName + "Effect", Player.class).invoke(null, player);
    }

    public static void WisdomCrownEffect(Player player) {

    }

    public static void HonorShieldEffect(Player player) {
        player.getHero().addBuff(new Holy(12, PERMANENT));
    }

    public static void SimurghFeatherEffect(Player player) {
        Hero hero = player.getEnemyPlayer().getHero();
        if (hero.getAttackType() == RANGED || hero.getAttackType() == HYBRID){
            hero.setAp(hero.getAp() - 2);
        }
    }

}
