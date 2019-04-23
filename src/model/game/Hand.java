package model.game;

import model.cards.Card;
import model.variables.CardsArray;

public class Hand extends CardsArray {
    public boolean add(Card card) {
        if(super.allCards.size() < 4) return super.add(card);
        return false;
    }
}
