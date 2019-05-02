package view;

import model.cards.Card;

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

}
