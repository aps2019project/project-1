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



}
