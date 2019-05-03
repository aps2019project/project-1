package view;

import model.cards.Hero;
import model.cards.Item;
import model.cards.Minion;
import model.cards.Spell;
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

    public static void showCollection(CardsArray collection) {
        ArrayList<Hero> heroes = collection.getAllHeros();
        ArrayList<Item> items = collection.getAllItems();
        ArrayList<Spell> spells = collection.getAllSpells();
        ArrayList<Minion> minions = collection.getAllMinions();

        System.out.println("Heroes:");
        for (int i = 1; i <= heroes.size(); ++i) {
            Hero hero = heroes.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"]  AP: %d - HP: %d Class: [%s] - Special power: %s\t Sell cost: %dD\n",
                    i, hero.getName(), hero.getAp(), hero.getHp(), hero.getAttackType().toString(),
                    hero.getDescription(), hero.getPrice());
        }
        System.out.println("Items:");
        for (int i = 1; i <= items.size(); ++i) {
            Item item = items.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"] - Desc: %s  sell cost: %dD\n", i, item.getName(),
                    item.getDescription(), item.getPrice());
        }
        System.out.println("Cards:");
        for (int i = 1; i <= spells.size(); ++i) {
            Spell spell = spells.get(i - 1);
            System.out.printf("\t%d: Type: SPELL  Name: [\"%s\"]  - MP: %d, Desc: %s - sell cost: %dD\n",
                    i, spell.getName(), spell.getMana(), spell.getDescription(), spell.getPrice());
        }
        for (int i = spells.size() + 1; i <= spells.size() + minions.size(); ++i) {
            Minion minion = minions.get(i - spells.size() - 1);
            System.out.printf("\t%d: Type: MINION  Name: [\"%s\"] - Class: %s, AP: %d, HP: %d, MP: %d, sell cost: %dD, Special power: %s\n",
                    i, minion.getName(), minion.getAttackType().toString(), minion.getAp(),
                    minion.getHp(), minion.getMana(),minion.getPrice(), minion.getDescription());
        }
    }
}
