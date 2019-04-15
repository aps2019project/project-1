package model.variables;

import model.cards.Card;

import java.util.ArrayList;

public class CardsArray {
    private ArrayList<Card> allCards;

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public Card find(Card card){
        return find(card.getID().getValue());
    }
    public Card find(String IDValue) {
        for(Card cardCounter : this.allCards) {
            if(cardCounter.isSameAs(IDValue)) return cardCounter;
        }
        return null;
    }

    public boolean add(Card card) {
        if(this.find(card) == null) {
            this.allCards.add(card);
            return true;
        }
        else {
            return false;
        }
    }
    public void remove(Card card) {
        allCards.remove(card);
    }
}
