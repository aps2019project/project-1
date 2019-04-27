package model.other;

import model.cards.Card;
import model.variables.CardsArray;

public class Shop {
    private static final Shop shop = new Shop();
    private CardsArray cards = new CardsArray(Card.getCards());
    public static Shop getInstance(){
        return shop;
    }
    private Shop(){
    }
}
