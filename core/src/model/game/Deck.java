package model.game;

import model.cards.*;
import model.variables.CardsArray;

public class Deck {
    private String name;
    private CardsArray cards;
    private Hero hero;
    private Item item;
    private Card nextCard;
    private float[] array = null;
    private int cardNum = 0;
    public Deck() {
    }

    public Deck(String name) {
        this.name = name;
        cards = new CardsArray();
    }
    public Deck(String name,CardsArray cards) {
        this.name = name;
        this.cards = cards;
        if(cards.getAllHeroes().size() > 0)
            this.hero = cards.getAllHeroes().get(0);
        if(cards.getAllItems().size() > 0)
            this.item = cards.getAllItems().get(0);

    }

    public void setArray(float[] array) {
        this.array = array;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
        if (card == null)
            return;
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
        if(cards.getAllHeroes().size() == 0) return null;
        hero = cards.getAllHeroes().get(0);
        return hero;
    }

    public Item getItem() {
        if(cards.getAllItems().size() == 0) return null;
        item = cards.getAllItems().get(0);
        return item;
    }

    public Deck copyAll() {
        Deck deck = new Deck(name,this.cards.copyAll());
        return deck;
    }
    public boolean checkIfValid() {
        return cards.getAllCards().size() == 20 && hero != null;
    }
    public void checkValidation() throws Exception {
        if (hero == null)
            throw new Exception("Main deck doesn't have HERO");
        if (item == null)
            throw new Exception("Main deck doesn't have ITEM");
        if (cards.getAllCards().size() < 20)
            throw new Exception("Main deck is not COMPLETE");
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
        int number = (int)(Math.random()*cards.getAllCards().size());
        if(array != null) {
            number = (int)(array[cardNum]*cards.getAllCards().size());
            cardNum++;
        }
        if(cards.getAllCards().size() == 0) return;
        nextCard = cards.getAllCards().get(number);
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
        return getName().equals(deck.name) &&
                getCards().equals(deck.cards)&&
                getHero().equals(deck.hero) &&
                getItem().equals(deck.item);
    }
}
