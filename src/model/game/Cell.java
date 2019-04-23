package model.game;

import model.cards.Card;

public class Cell {
    private Card insideCard;
    private CellEffect effect;
    private Flag flag;
    private int x;
    private int y;

    public boolean isEmpty() {
        return insideCard == null;
    }
    public boolean put(Card card) {
        if(this.isEmpty() || card == null) return false;
        this.insideCard = card;
        return true;
    }
    public Card pick() {
        Card card = insideCard;
        insideCard = null;
        return card;
    }
    public void add(Flag flag) {
        this.flag = flag;
    }
    public void defend() {

    }

    public void getEffect(){

    }
    public void ActionPositionBuff() {

    }
    public void ActionFire() {

    }
}
