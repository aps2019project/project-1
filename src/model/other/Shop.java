package model.other;

import model.cards.Card;
import model.variables.CardsArray;
import model.variables.ID;

public class Shop {
    private static final Shop shop = new Shop();
    private CardsArray cards = Card.getCards();

    public static Shop getInstance() {
        return shop;
    }

    private Shop() {
    }

    public ID search(String name) {
        Card card = this.cards.findBYName(name);
        if (card != null) {
            return card.getID();
        }
        return null;
    }
}
