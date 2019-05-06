package model.game;

import model.cards.*;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Deck {
    private String name;
    private CardsArray cards;
    private Hero hero;
    private Item item;
    private Card nextCard;

    public Deck() {
    }

    public Deck(String name) {
        this.name = name;
        cards = new CardsArray();
    }
    private Deck(String name,CardsArray cards) {
        this.name = name;
        this.cards = cards;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
        if(card.isSameAs(hero)) hero = null;
        if(card.isSameAs(item)) item = null;
    }

    public boolean addCard(Card card) {
        if(card instanceof Hero) {
            if(this.hero != null) return false;
            this.hero = (Hero)card;
        }
        if(card instanceof Item) {
            if(this.item != null) return false;
            this.item = (Item)card;
        }
        return cards.add(card);
    }

    public CardsArray getCards() {
        return cards;
    }

    public Hero getHero() {
        if(cards.getAllHeros().size() == 0) return null;
        hero = cards.getAllHeros().get(0);
        return hero;
    }

    public Item getItem() {
        if(cards.getAllItems().size() == 0) return null;
        item = cards.getAllItems().get(0);
        return item;
    }

    public Deck copyAll() {
        Deck deck = new Deck(name,this.cards.copyAll());
        deck.addCard(this.hero);
        deck.addCard(this.item);
        return deck;
    }
    public boolean checkIfValid() {
        return cards.getAllCards().size() == 20 && hero != null;
    }
    public void fillHand(Hand hand) {
        setNextCard();
        while(transferCardTo(hand));
    }
    public boolean transferCardTo(Hand hand) {
        Card card = getNextCard();
        if(hand.add(card)) {
            this.deleteCard(card);
            setNextCard();
            return true;
        }
        return false;
    }
    public Card getNextCard() {
        if(nextCard == null) setNextCard();
        return nextCard;
    }
    public void setNextCard() {
        nextCard = cards.getAllCards().get((int)(Math.random()*cards.getAllCards().size()));
        if(nextCard == this.item || nextCard == this.hero) setNextCard();
    }
    public String getName() {
        return name;
    }

    public int size() {
        return cards.getAllCards().size();
    }

    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deck)) return false;
        Deck deck = (Deck) o;
        return Objects.equals(getName(), deck.getName()) &&
                Objects.equals(getCards(), deck.getCards()) &&
                Objects.equals(getHero(), deck.getHero()) &&
                Objects.equals(getItem(), deck.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCards(), getHero(), getItem());
    }
}
