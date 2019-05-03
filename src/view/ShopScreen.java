package view;

import model.cards.*;
import model.variables.CardsArray;

import java.util.ArrayList;

public class ShopScreen extends Screen {

    public static void showOptions() {

    }

    public static void showNoCardWithThisName(){
        System.out.println("There isn't any cards with this name in Shop");
    }

    public static void showID(String id){
        System.out.println("Card ID : " + id);
    }

    public static void showCardArray(CardsArray collection, String sell) {
        ArrayList<Hero> heroes = collection.getAllHeros();
        ArrayList<Item> items = collection.getSellableItems();
        ArrayList<Spell> spells = collection.getAllSpells();
        ArrayList<Minion> minions = collection.getAllMinions();

        System.out.println("Heroes:");
        for (int i = 1; i <= heroes.size(); ++i) {
            Hero hero = heroes.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"]  AP: %d - HP: %d Class: [%s] - Special power: %s\t %s cost: %dD\n",
                    i, hero.getName(), hero.getAp(), hero.getHp(), hero.getAttackType().toString(),
                    hero.getDescription(), sell, hero.getPrice());
        }
        System.out.println("Items:");
        for (int i = 1; i <= items.size(); ++i) {
            Item item = items.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"] - Desc: %s  %s cost: %dD\n", i, item.getName(),
                    item.getDescription(), sell, item.getPrice());
        }
        System.out.println("Cards:");
        for (int i = 1; i <= spells.size(); ++i) {
            Spell spell = spells.get(i - 1);
            System.out.printf("\t%d: Type: SPELL  Name: [\"%s\"]  - MP: %d, Desc: %s - %s cost: %dD\n",
                    i, spell.getName(), spell.getMana(), spell.getDescription(), sell, spell.getPrice());
        }
        for (int i = spells.size() + 1; i <= spells.size() + minions.size(); ++i) {
            Minion minion = minions.get(i - spells.size() - 1);
            System.out.printf("\t%d: Type: MINION  Name: [\"%s\"] - Class: %s, AP: %d, HP: %d, MP: %d, %s cost: %dD, Special power: %s\n",
                    i, minion.getName(), minion.getAttackType().toString(), minion.getAp(),
                    minion.getHp(), minion.getMana(), sell, minion.getPrice(), minion.getDescription());
        }
    }

    public static void showCardNotFound() {
        System.out.println("Can't find card with given name.");
    }

    public static void showCardInfo(Card card) {
        System.out.println("Card [\"" + card.getName() + "\"] ->\tID: " +card.getID().getValue());
    }

    public static void showOutOfDaric() {
        System.out.println("You Dont have enough Daric");
    }

    public static void showItemIsFull() {
        System.out.println("You already have 3 items...");
    }

    public static void showSomethingIsWrong() {
        System.out.println("something went wrong... try again");
    }

}
