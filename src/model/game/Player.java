package model.game;

import model.cards.Card;
import model.cards.Hero;
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
    public void increaseTurnNumber() {
        turnNumber++;
    }
    public void setMana() {
        if(turnNumber <= 7) mana = turnNumber+1;
        else               mana = 9;
    }

    public void addToGraveYard(Card card) {
        this.graveYard.add(card);
    }
    public boolean move(Cell presentCell,Cell destinationCell) {
        if(destinationCell.isEmpty()) return false;
        Card card = presentCell.pick();
        return destinationCell.put(card);

    }
    public void putHeroIn(Cell cell) {
        Hero hero = deck.getHero();
        deck.deleteCard(hero);
        cell.put(hero);
    }
    public boolean moveFromHandToCell(int index,Cell cell) {
        if(cell.isEmpty()) {
            return cell.put(hand.pick(index));
        }
        return false;
    }
    public void useItem(){}
    public void startMatchSetup() {
        deck.setNextCard();
    }
    public void nextTurnSetup() {

    }
    public void play() {
        increaseTurnNumber();
        setMana();
        deck.transferCardTo(hand);
        deck.setNextCard();
    }
}
