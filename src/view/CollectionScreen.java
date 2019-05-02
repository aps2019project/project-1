package view;

import model.cards.*;
import model.game.Deck;

import java.util.ArrayList;

public class CollectionScreen extends Screen {

    public static void showCardNotFound() {
        System.out.println("There is no card with this name... Try again");
    }

    public static void showFoundCard(Card card) {
        System.out.println("Card: " +
                "{Name: " + card.getName() +
                "\t ID: " + card.getID() +
                '}');
    }

    public static void showDeckExists() {
        System.out.println("Can't create a deck with this name...");
        System.out.println("Deck already exists.");
    }

    public static void showDeckNotFound() {
        System.out.println("Can't Find deck with given name...");
    }

    public static void showSuccessfulDeckCreation() {
        System.out.println("Deck created successfully");
    }

    public static void showSuccessfulDeckRemoval() {
        System.out.println("Deck deleted successfully");
    }

    public static void showDeckIsFull() {
        System.out.println("This deck is already full");
    }

    public static void showCantAddHero() {
        System.out.println("This Deck already has a hero...");
    }

    public static void showCantAddItem() {
        System.out.println("This Deck already has an Item...");
    }

    public static void showCardAddedSuccessfully() {
        System.out.println("Card added successfully :)");
    }

    public static void showDeckIsEmpty() {
        System.out.println("This deck has no Cards...");
    }

    public static void showCardRemovedSuccessfully() {
        System.out.println("Card removed successfully :)");
    }

    public static void showDeckIsValid() {
        System.out.println("Deck is Valid.");
    }

    public static void showDeckIsInvalid() {
        System.out.println("Deck is not Complete...");
    }

    public static void showMainDeckChangedSuccessfully() {
        System.out.println("Main deck changed successfully");
    }

    public static void showDeckDetails(Deck deck) {
        ArrayList<Hero> heroes = deck.getCards().getAllHeros();
        ArrayList<Item> items = deck.getCards().getAllItems();
        ArrayList<Spell> spells = deck.getCards().getAllSpells();
        ArrayList<Minion> minions = deck.getCards().getAllMinions();

        System.out.println("Deck: \"" + deck.getName() + "\"");
        System.out.println("Hero:");
        for (int i = 1; i <= heroes.size(); ++i) {
            Hero hero = heroes.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"]  AP: %d - HP: %d Class: [%s] - Special power: %s\t Sell price: %dD\n",
                    i, hero.getName(), hero.getAp(), hero.getHp(), hero.getAttackType().toString(),
                    hero.getDescription(), hero.getPrice());
        }
        System.out.println("Item:");
        for (int i = 1; i <= items.size(); ++i) {
            Item item = items.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"] - Desc: %s\n", i, item.getName(),
                    item.getDescription());
        }
        System.out.println("Cards:");
        for (int i = 1; i <= spells.size(); ++i) {
            Spell spell = spells.get(i - 1);
            System.out.printf("\t%d: Type: SPELL  Name: [\"%s\"]  - MP: %d, Desc: %s",
                    i, spell.getName(), spell.getMana(), spell.getDescription());
        }
        for (int i = spells.size() + 1; i <= spells.size() + minions.size(); ++i) {
            Minion minion = minions.get(i - spells.size() - 1);
            System.out.printf("\t%d: Type: MINION  Name: [\"%s\"] - Class: %s, AP: %d, HP: %d, MP: %d, Special power: %s",
                    i, minion.getName(), minion.getAttackType().toString(), minion.getAp(),
                    minion.getHp(), minion.getMana(), minion.getDescription());
        }
    }

    public static void showMainDeck(Deck deck) {
        System.out.println("Main Deck:____________________________________________");
        showDeckDetails(deck);
        System.out.println("______________________________________________________");
    }

    public static void showOptions() {
        System.out.println("show");
        System.out.println("search [card name]");
        System.out.println("create deck [deck name]");
        System.out.println("delete deck [deck name]");
        System.out.println("add [card id] to deck [deck name]");
        System.out.println("remove [card id] to deck [deck name]");
        System.out.println("validate [deck name]");
        System.out.println("select deck [deck name]");
        System.out.println("show all decks");
        System.out.println("show deck [deck name]");
    }

}
