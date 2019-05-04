package view;

import model.cards.*;
import model.other.Account;
import model.variables.CardsArray;

import java.util.ArrayList;

public class ShopScreen extends Screen {

    public static void showWelcomeLine() {
        System.out.println("\t\t\t_______________S H O P_______________");
    }

    public static void showOptions() {
        System.out.println(" Things you can do here ______________________________________________");
        System.out.println("|                                                                     |");
        System.out.println("| Show Collection:               *to see your collection              |");
        System.out.println("| Show:                          *to see cards in shop                |");
        System.out.println("| Search [card name]:            *find card's id in shop              |");
        System.out.println("| Search Collection [card name]  *find card's id in your collection   |");
        System.out.println("| Buy [card name]:               *buy card from shop                  |");
        System.out.println("| Sell [card name]:              *sell card from collection           |");
        System.out.println("| Help:                          *see this menu                       |");
        System.out.println("| Exit:                          *return to main menu                 |");
        System.out.println("|_____________________________________________________________________|");
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

    public static void showBuyWasSuccessful() {
        System.out.println("Card added to your collection successfully");
        System.out.println("Remaining Daric: " + Account.getCurrentAccount().getDaric() + "D");
    }

    public static void showSellWasSuccessful() {
        System.out.println("Card sold successfully");
        System.out.println("Remaining Daric: " + Account.getCurrentAccount().getDaric() + "D");
    }

}
