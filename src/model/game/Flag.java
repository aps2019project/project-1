package model.game;

import model.cards.Card;

public class Flag {
    private Card card;
    private Cell cell;
    private boolean taken = false;

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
    public void takeBy(Card card) {
        this.card = card;
        this.cell = null;
        this.taken = true;
    }
}
