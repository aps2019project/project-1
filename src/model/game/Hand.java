package model.game;

import model.cards.Card;
import model.variables.CardsArray;

public class Hand {
    private CardsArray cards = new CardsArray();
    public boolean add(Card card) {
        if(cards.getAllCards().size() > 4) return false;
        return cards.add(card);
    }
    public void remove(Card card) {
        cards.remove(card);
    }
}
