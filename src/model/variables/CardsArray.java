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

    private ArrayList<Hero> allHeroes = new ArrayList<>();
    private ArrayList<Minion> allMinions = new ArrayList<>();
    private ArrayList<Spell> allSpells = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(heroCards());
        allCards.addAll(spellCards());
        allCards.addAll(itemCards());
        allCards.addAll(minionCards());
        sortCards(allCards);
        return allCards;
    }

    public ArrayList<Hero> getAllHeros() {
        return this.allHeroes;
    }

    public ArrayList<Minion> getAllMinions() {
        return this.allMinions;
    }

    public ArrayList<Spell> getAllSpells() {
        return this.allSpells;
    }

    public ArrayList<Item> getAllItems() {
        return this.allItems;
    }

    public ArrayList<Item> getSellableItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getItemType().equals(ItemType.USABLE))
                items.add(item);
        }
        return items;
    }

    public ArrayList<Army> getArmy() {
        ArrayList<Army> armies = new ArrayList<>();
        armies.addAll(getAllHeros());
        armies.addAll(getAllMinions());
        return armies;
    }

    public Card find(Card card) {
        if(card == null) return null;
        return find(card.getID().getValue());
    }

    public Card find(String IDValue) {
        for (Card card : getAllCards()) {
            if (card.isSameAs(IDValue))
                return card;
        }
        return null;
    }

    public Card findByName(String name) {
        for (Card cardCounter : getAllCards()) {
            if (cardCounter.getName().toLowerCase().equals(name.toLowerCase())){
                return cardCounter;
            }
        }
        return null;
    }

    public boolean add(Card card) {
        if (this.find(card) != null) {
            return false;
        }
        try {
            switch (card.getType()) {
                case ITEM:
                    allItems.add((Item) card);
                    break;
                case HERO:
                    allHeroes.add((Hero) card);
                    break;
                case MINION:
                    allMinions.add((Minion) card);
                    break;
                case SPELL:
                    allSpells.add((Spell) card);
                    break;
            }
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void remove(Card card) {
        if (this.find(card) == null) {
            return;
        }
        try {
            switch (card.getType()) {
                case ITEM:
                    System.out.println(card);
                    allItems.remove(card);
                    break;
                case HERO:
                    allHeroes.remove(card);
                    break;
                case MINION:
                    allMinions.remove(card);
                    break;
                case SPELL:
                    allSpells.remove(card);
                    break;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clear() {
        allSpells.clear();
        allHeroes.clear();
        allMinions.clear();
        allItems.clear();
    }

    public Card pick(String name) {
        Card card =  findByName(name);
        this.remove(card);
        return card;
    }

    public Card getRandomCard() {
        return getAllCards().get((int)Math.floor(Math.random()*getAllCards().size()));
    }

    public CardsArray copyAll() {
        CardsArray copyFromCardList = new CardsArray();
        for(Card card : getAllCards()) {
            try {
                copyFromCardList.add(card.clone());
            }
            catch(Exception e) {
                BattleScreen.showInvalidClone();
            }
        }
        return copyFromCardList;
    }

    private void sortCards(ArrayList<Card> allCards) {
        allCards.sort(Comparator.comparing(Card::getType));
    }

    private ArrayList<Card> heroCards() {
        ArrayList<Card> cards = new ArrayList<>(allHeroes);
        return cards;
    }

    private ArrayList<Card> spellCards() {
        ArrayList<Card> cards = new ArrayList<>(allSpells);
        return cards;
    }

    private ArrayList<Card> itemCards() {
        ArrayList<Card> cards = new ArrayList<>(allItems);
        return cards;
    }

    private ArrayList<Card> minionCards() {
        ArrayList<Card> cards = new ArrayList<>(allMinions);
        return cards;
    }

    @Override
    public String toString() {
        return "CardsArray{" +
                "allHeroes=" + allHeroes +
                ", allMinions=" + allMinions +
                ", allSpells=" + allSpells +
                ", allItems=" + allItems +
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
