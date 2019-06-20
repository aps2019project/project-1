package view;

import model.cards.*;
import model.game.Deck;
import model.variables.CardsArray;

import java.util.ArrayList;

public class CollectionScreen extends Screen {

    public static void showWelcomeLine() {
        System.out.println("\t\t\t_______________COLLECTION_______________\n");
    }

    public static void shoeInvalidCommand() {
        System.out.println("Invalid command. Try again.");
    }

    public static void showOptions() {
        System.out.println(" Things you can do here..._________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|  Show:                                   *to see your collection |");
        System.out.println("|  Search [card name]                      *find card in collection|");
        System.out.println("|  Create deck [deck name]                 *create new deck        |");
        System.out.println("|  Delete deck [deck name]                 *delete a deck          |");
        System.out.println("|  Add [card id] to deck [deck name]       *add card to collection |");
        System.out.println("|  Remove [card id] to deck [deck name]    *remove card collection |");
        System.out.println("|  Validate [deck name]                    *check if deck is valid |");
        System.out.println("|  Select deck [deck name]                 *select main deck       |");
        System.out.println("|  Show all decks                          *see all decks          |");
        System.out.println("|  Show deck [deck name]                   *see decks cards        |");
        System.out.println("|  Help:                                   *see this menu          |");
        System.out.println("|  Exit:                                   *return to main menu    |");
        System.out.println("|__________________________________________________________________|");
    }

    public static void showCardNotFound() {
        System.out.println("There is no card with this name... Try again");
    }

    public static void showFoundCard(Card card) {
        System.out.println("Card: " +
                "{ Name: " + card.getName() +
                "\t ID: [" + card.getID().getValue() +
                "]  }");
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
        if (deck == null) {
            System.out.println("Error - Deck is NULL");
            return;
        }
        System.out.println("Deck: \"" + deck.getName() + "\"");
        showCardArray(deck.getCards());
    }

    public static void showCardArray(CardsArray cards) {

        if (cards == null) {
            System.out.println("Error - Cards are null");
            return;
        }

        ArrayList<Hero> heroes = cards.getAllHeroes();
        ArrayList<Item> items = cards.getAllItems();
        ArrayList<Spell> spells = cards.getAllSpells();
        ArrayList<Minion> minions = cards.getAllMinions();

        System.out.println("Hero:");
        for (int i = 1; i <= heroes.size(); ++i) {
            Hero hero = heroes.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"] ID: %s AP: %d - HP: %d Class: [%s] - Special power: %s\t Sell price: %dD\n",
                    i, hero.getName(), hero.getID().getValue(), hero.getAp(), hero.getHp(), hero.getAttackType().toString(),
                    hero.getDescription(), hero.getPrice());
        }
        System.out.println("Item:");
        for (int i = 1; i <= items.size(); ++i) {
            Item item = items.get(i - 1);
            System.out.printf("\t%d: Name: [\"%s\"] - ID: %s - Desc: %s\n", i, item.getName(),
                    item.getID().getValue(),
                    item.getDescription());
        }
        System.out.println("Cards:");
        for (int i = 1; i <= spells.size(); ++i) {
            Spell spell = spells.get(i - 1);
            System.out.printf("\t%d: Type: SPELL  Name: [\"%s\"]  - ID: %s - MP: %d, Desc: %s\n",
                    i, spell.getName(), spell.getID().getValue(), spell.getMana(), spell.getDescription());
        }
        for (int i = spells.size() + 1; i <= spells.size() + minions.size(); ++i) {
            Minion minion = minions.get(i - spells.size() - 1);
            System.out.printf("\t%d: Type: MINION  Name: [\"%s\"] - ID: %s - Class: %s, AP: %d, HP: %d, MP: %d, Special power: %s\n",
                    i, minion.getName(), minion.getID().getValue(), minion.getAttackType().toString(), minion.getAp(),
                    minion.getHp(), minion.getMana(), minion.getDescription());
        }
    }

    public static void showMainDeck(Deck deck) {
        System.out.println("Main Deck:____________________________________________");
        showDeckDetails(deck);
        System.out.println("______________________________________________________");
    }

    public static void showCloneError() {
        System.out.println("Something went wrong: Card Cloning");
    }

}
