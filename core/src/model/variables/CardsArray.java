package model.variables;

import model.cards.*;
import view.BattleScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CardsArray {

    public CardsArray() {
    }

    public CardsArray(ArrayList<String> cardNames) {
        for (String cardName: cardNames) {
            add(Card.getCards().findByName(cardName));
        }
    }

    private ArrayList<Hero> allHeroes = new ArrayList<Hero>();
    private ArrayList<Minion> allMinions = new ArrayList<Minion>();
    private ArrayList<Spell> allSpells = new ArrayList<Spell>();
    private ArrayList<Item> allItems = new ArrayList<Item>();

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(heroCards());
        allCards.addAll(spellCards());
        allCards.addAll(itemCards());
        allCards.addAll(minionCards());
        sortCards(allCards);
        return allCards;
    }

    public ArrayList<Hero> getAllHeroes() {
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
        ArrayList<Item> items = new ArrayList<Item>();
        for (Item item : allItems) {
            if (item.getItemType().equals(ItemType.USABLE))
                items.add(item);
        }
        return items;
    }

    public ArrayList<Army> getArmy() {
        ArrayList<Army> armies = new ArrayList<Army>();
        armies.addAll(getAllHeroes());
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
        if (card == null)
            return false;
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
        if (card == null)
            return;
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
        Collections.sort(allCards, new Comparator<Card>() {
            @Override
            public int compare(Card c1, Card c2) {
                return c1.getType().compareTo(c2.getType());
            }
        });
    }

    private ArrayList<Card> heroCards() {
        return new ArrayList<Card>(allHeroes);
    }

    private ArrayList<Card> spellCards() {
        return new ArrayList<Card>(allSpells);
    }

    private ArrayList<Card> itemCards() {
        return new ArrayList<Card>(allItems);
    }

    private ArrayList<Card> minionCards() {
        return new ArrayList<Card>(allMinions);
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
        return getAllCards().equals(armies.getAllCards());
    }
}
