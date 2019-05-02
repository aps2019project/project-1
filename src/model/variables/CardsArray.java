package model.variables;

import model.cards.Card;

import java.util.ArrayList;

public class CardsArray {
    protected ArrayList<Card> allCards;

    public CardsArray(){
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public Card find(Card card) {
        return find(card.getID().getValue());
    }
    public Card find(String IDValue) {
        for (Card cardCounter : this.allCards) {
            if (cardCounter.isSameAs(IDValue)) return cardCounter;
        }
        return null;
    }
    public Card findBYName(String name) {
        for (Card cardCounter : this.allCards) {
            if (cardCounter.getName().equals(name)){
                return cardCounter;
            }
        }
        return null;
    }

    public boolean add(Card card) {
        if (this.find(card) == null) {
            this.allCards.add(card);
            return true;
        } else {
            return false;
        }
    }
    public void remove(Card card) {
        allCards.remove(card);
    }
    public void remove(int index) {
        allCards.remove(index);
    }
    public void clear() {allCards.clear();}
    public Card pick(String name) {
            Card card =  findBYName(name);
            this.remove(card);
            return card;
    }

    public CardsArray copyAll() throws CloneNotSupportedException {
        CardsArray copyFromCardList = new CardsArray();
        for(Card card : this.allCards) {
            copyFromCardList.add(card.clone());
        }
        return copyFromCardList;
    }
}
