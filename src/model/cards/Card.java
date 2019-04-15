package model.cards;

import model.variables.ID;

public class Card {
    private model.variables.ID ID = new ID();

    public model.variables.ID getID() {
        return ID;
    }
    public boolean isSameAs(Card card) {
        return this.ID.isSameAs(card.ID);
    }
    public boolean isSameAs(String ID) {
        return this.ID.isSameAs(ID);
    }


}
