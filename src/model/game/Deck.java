package model.game;

import model.cards.*;
import model.variables.CardsArray;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private String name;
    private CardsArray cards = new CardsArray();
    private Hero hero;
    private Item item;
    private Card nextCard;

    public Deck(String name) {
        this.name = name;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
        if(card.isSameAs(hero)) hero = null;
        if(card.isSameAs(item)) item = null;
    }

    public boolean addCard(Card card) {
        return cards.add(card);
    }
    public boolean addCard(Hero hero) {
        if(this.hero != null) return false;
        this.hero = hero;
        return this.addCard(hero);
    }
    public boolean addCard(Item item) {
        if(this.item != null) return false;
        this.item = item;
        return this.addCard(item);
    }

    public CardsArray getCards() {
        return cards;
    }

    public Hero getHero() {
        return hero;
    }

    public Item getItem() {
        return item;
    }
    public Deck copyAll() {
        Deck deck = new Deck(name);
        deck.cards.getAllCards().addAll(this.cards.getAllCards());
        deck.addCard(this.hero);
        deck.addCard(this.item);
        return deck;
    }
    public boolean checkDeck() {
        return cards.getAllCards().size() == 20 && hero != null && item != null;
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
}
