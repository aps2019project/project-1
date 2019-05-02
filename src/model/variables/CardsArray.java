package model.variables;

import model.cards.*;

import java.awt.datatransfer.MimeTypeParseException;
import java.util.ArrayList;
import java.util.Comparator;

import static model.cards.CardType.*;

public class CardsArray {
    protected ArrayList<Card> allCards;

    public CardsArray(){
        allCards = new ArrayList<>();
    }

    public CardsArray(ArrayList<Card> array){
        allCards = new ArrayList<>(array);
    }

    public ArrayList<Card> getAllCards() {
        sortCards();
        return allCards;
    }

    public ArrayList<Hero> getAllHeros() {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType() == HERO)
                heroes.add((Hero) card);
        }
        return heroes;
    }

    public ArrayList<Minion> getAllMinions() {
        ArrayList<Minion> minions = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType() == MINION)
                minions.add((Minion) card);
        }
        return minions;
    }

    public ArrayList<Spell> getAllSpells() {
        ArrayList<Spell> spells = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType() == SPELL)
                spells.add((Spell) card);
        }
        return spells;
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType() == ITEM)
                items.add((Item) card);
        }
        return items;
    }

    public Card find(Card card) {
        return find(card.getID().getValue());
    }

    public Card find(String IDValue) {
        for (Card card : allCards) {
            if (card.isSameAs(IDValue))
                return card;
        }
        return null;
    }

    public Card findByName(String name) {
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
    public Card pick(int index) {
            Card card =  allCards.get(index);
            this.remove(card);
            return card;
    }

    public void showCards() {
        int counter = 1;
        for(Card card : allCards) {
            System.out.println(counter+". ");
//            card.showCard();
            counter++;
        }
    }

    private void sortCards() {
        allCards.sort(Comparator.comparing(Card::getType));
    }
}
