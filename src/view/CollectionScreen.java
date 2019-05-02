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
}
