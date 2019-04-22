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

    public Account getAccount() {
        return account;
    }

    public void addToGraveYard(Card card) {
        this.graveYard.add(card);
    }
    public boolean move(Cell presentCell,Cell destinationCell) {
        if(destinationCell.isEmpty()) return false;
        Card card = presentCell.pick();
        return destinationCell.put(card);

    }
    public void useItem(){}
    public void nextTurn() {

    }
}
