package model.game;

import model.cards.Card;
import model.cards.Hero;
import model.other.Account;
import model.variables.CardsArray;

public class Player {

    private Account account;
    private Deck deck;
    private Hand hand;
    private int mana;
    private CardsArray graveYard = new CardsArray();
    private CardsArray movedCardInThisTurn = new CardsArray();
    private CardsArray attackerCardsInThisTurn = new CardsArray();
    private int turnNumber;
    private int numberOfFlags;

    public Player(Account account) {
        this.account = account;
        /////////////
    }
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
        if(presentCell == null || destinationCell == null) return false;
        if(destinationCell.isEmpty() || movedCardInThisTurn.find(presentCell.getInsideCard()) == null) return false;
        Card card = presentCell.pick();
        movedCardInThisTurn.add(card);
        return destinationCell.put(card);

    }
    public void putHeroIn(Cell cell) {
        Hero hero = deck.getHero();
        deck.deleteCard(hero);
        cell.put(hero);
    }
    public boolean attack(Cell attackersCell,Cell defendersCell) {
        if(attackersCell == null || defendersCell == null) return false;
        if(attackerCardsInThisTurn.find(attackersCell.getInsideCard()) == null) return false;
        return true;//////////
    }
    public boolean moveFromHandToCell(int index,Cell cell) {
        if(cell.isEmpty() && mana >= hand.getNeededManaToMove(index)) {
            if(cell.put(hand.pick(index))) {
                mana -= cell.getInsideCard().getNeededManaToMove();
                return true;
            }
        }
        return false;
    }
    public void useItem(){}
    public void startMatchSetup() { deck.fillHand(hand);
    }
    public void nextTurnSetup() {
        movedCardInThisTurn.clear();
        attackerCardsInThisTurn.clear();
    }
    public void play() {
        increaseTurnNumber();
        setMana();
        deck.transferCardTo(hand);

    }

    public void showHand() {
        hand.showCards();
        System.out.println("next card is:");
        deck.getNextCard().showCard();
    }
    public void showGraveYard() {
        graveYard.showCards();
    }
}
