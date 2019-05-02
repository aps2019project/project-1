package model.game;

import model.cards.Card;

public class Flag {
    private Card card;
    private Cell cell;
    private boolean taken = false;
    private int numberOfTurnItTaken = 0;

    public int getNumberOfTurnItTaken() {
        return numberOfTurnItTaken;
    }

    public Cell getCell() {
        return cell;
    }
    public Card getCard() {
        return card;
    }
    public boolean isTaken() {
        return taken;
    }

    public void dropTo(Cell cell) {
        this.cell = cell;
        this.card = null;
        this.taken = false;
    }
    public void takeBy(Card card,int turnNumber) {
        this.card = card;
        this.cell = null;
        this.taken = true;
        this.numberOfTurnItTaken = turnNumber;
    }
}
