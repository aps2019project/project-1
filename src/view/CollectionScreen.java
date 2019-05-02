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

    public static void shwDeckExists() {
        System.out.println("Can't create a deck with this name...");
        System.out.println("Deck already exists.");
    }

    public static void showSuccessfullDeckCreation() {
        System.out.println("Deck created successfully");
    }

}
