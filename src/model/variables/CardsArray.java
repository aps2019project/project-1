package model.variables;

import model.cards.*;
import view.BattleScreen;

import java.awt.datatransfer.MimeTypeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntFunction;

import static model.cards.CardType.*;

public class CardsArray {

    protected ArrayList<Card> allCards = new ArrayList<>();

    public CardsArray(){
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

    public ArrayList<Item> getSellableItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType() == ITEM) {
                Item item = (Item) card;
                if (item.getItemType().equals(ItemType.USABLE))
                    items.add((Item) card);
            }
        }
        return items;
    }

    public ArrayList<Army> getArmy() {
        ArrayList<Army> armies = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType() == MINION || card.getType() == HERO)
                armies.add((Army) card);
        }
        return armies;
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
            if (cardCounter.getName().toLowerCase().equals(name.toLowerCase())){
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

    public void remove(String name) {
        allCards.remove(findByName(name));
    }


    public void clear() {allCards.clear();}

    public Card pick(String name) {
        Card card =  findByName(name);
        this.remove(card);
        return card;
    }

    public Card getRandomCard() {
        return allCards.get((int)Math.floor(Math.random()*allCards.size()));
    }

    public CardsArray copyAll() {
        CardsArray copyFromCardList = new CardsArray();
        for(Card card : this.allCards) {
            try {
                copyFromCardList.add(card.clone());
            }
            catch(Exception e) {
                BattleScreen.showInvalidClone();
            }
        }
        return copyFromCardList;
    }

    private void sortCards() {
        allCards.sort(Comparator.comparing(Card::getType));
    }

    @Override
    public String toString() {
        return "CardsArray{" +
                "allCards=" + allCards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardsArray)) return false;
        if (!super.equals(o)) return false;
        CardsArray armies = (CardsArray) o;
        return Objects.equals(getAllCards(), armies.getAllCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAllCards());
    }

}
