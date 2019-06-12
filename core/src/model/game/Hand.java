package model.game;

import model.cards.Card;
import model.variables.CardsArray;

public class Hand extends CardsArray {
    public boolean add(Card card) {
        if(super.getAllCards().size() < 5) return super.add(card);
        return false;
    }
    public int getNeededManaToMove(String name) {
        if(findByName(name) != null) return findByName(name).getNeededManaToPut();
        return 0;
    }

}