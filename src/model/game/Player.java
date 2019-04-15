package model.game;

import model.cards.Card;
import model.other.Account;

import java.util.ArrayList;

public class Player {
    private Account account;
    private Deck deck;
    private Hand hand;
    private int mana;
    private ArrayList<Card> graveYard = new ArrayList<>();
    private int turnNumber;
    private int numberOfFlags;

    public void addToGraveYard(Card card) {
        this.graveYard.add(card);
    }
}
