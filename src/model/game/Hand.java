package model.game;

import model.cards.Card;
import model.variables.CardsArray;

public class Hand extends CardsArray {
    public boolean add(Card card) {
        if(super.allCards.size() < 5) return super.add(card);
        return false;
    }
    public int getNeededManaToMove(int index) {
        if(super.allCards.size() >index) return super.allCards.get(index).getNeededManaToMove();
        return 0;
    }
}
