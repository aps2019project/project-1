package model.cards;

import model.Buff.Holy;
import model.Buff.Power;
import model.game.Game;
import model.game.Player;

import javax.swing.plaf.metal.MetalBorders;
import java.util.ArrayList;

import static model.Buff.BuffTImeType.*;
import static model.Buff.PowerBuffType.*;
import static model.cards.AttackType.*;
import static model.cards.CardType.ITEM;

public class Item extends Card {
    private static ArrayList<Item> items = new ArrayList<>();
    private ItemType itemType;
    private SPTime itemEffectTime;

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

    public SPTime getItemEffectTime() {
        return itemEffectTime;
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

    public static void WisdomCrownUsable(Player player) {

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

    public static void KingWisdomUsable(Player player) {

    }

    public static void NooshdaruCollectible(Player player, Army army) {
        ArrayList<Army> array = Game.getCurrentGame().getAllInGameCards();
        Army armyTemp = Army.getRandomArmy(array);
        armyTemp.setHp(armyTemp.getHp() + 6);
    }

    public static void TwoHeadArrowCollectible(Player player, Army army) {
        ArrayList<Army> array = Game.getCurrentGame().getAllInGameCards();
        boolean thereIsRangedOrHybrid = false;
        for(Army armyTemp : array) {
            if(armyTemp.getAttackType()!= MELEE) {
                thereIsRangedOrHybrid = true;
                break;
            }
        }
        if(!thereIsRangedOrHybrid) return;
        while(true){
            Army armyTemp = Army.getRandomArmy(array);
            if(army.getAttackType() != MELEE) {
                armyTemp.setAp(armyTemp.getAp() + 2);
                return;
            }
        }
    }

    public static void ElixirCollectible(Player player, Army army) {
        ArrayList<Army> array = Game.getCurrentGame().getAllInGameCards();
        army.setHp(army.getHp() + 3);
        boolean thereIsMinion = false;
        for(Army armyTemp : array) {
            if(armyTemp instanceof Minion) {
                thereIsMinion = true;
                break;
            }
        }
        if(!thereIsMinion) return;
        while(true){
            Army armyTemp = Army.getRandomArmy(array);
            if(armyTemp instanceof Minion) {
                armyTemp.addBuff(new Power(3, AP, NORMAL));
                return;
            }
        }
    }

    public static void ManaPotionCollectible(Player player, Army army) {

    }

    public static void RevengeousPotionCollectible(Player player, Army army) {
        Army armyTemp = Army.getRandomArmy(player.getInGameCards());
        armyTemp.addBuff(new Holy(10, 2, NORMAL));
    }

    public static void DeathCurseCollectible(Player player, Army army) {

    }

    public static void RandomDamageCollectible(Player player, Army army) {
        Army armyTemp = Army.getRandomArmy(Game.getCurrentGame().getAllInGameCards());
        armyTemp.setAp(armyTemp.getAp() + 2);
    }

    public static void BladesOfAgilityCollectible(Player player ,Army army) {
        Army armyTemp = Army.getRandomArmy(Game.getCurrentGame().getAllInGameCards());
        armyTemp.setAp(armyTemp.getAp() + 6);
    }

    public static void ChiniSwordCollectible(Player player, Army army) {
        if (army.getAttackType() == MELEE) {
            army.setAp(army.getAp() + 5);
        }

    }



}