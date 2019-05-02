package model.game;

import control.BattleHandler;
import control.BattlesOrderType;
import model.cards.Army;
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
    private CardsArray movedCardsInThisTurn = new CardsArray();
    private CardsArray attackerCardsInThisTurn = new CardsArray();
    private int turnNumber = 0;
    private int numberOfFlags = 0;
    private boolean endTurn = false;
    private boolean heroKilled = false;

    public Player(Account account) throws CloneNotSupportedException {
        this.account = account;
        this.deck = account.getMainDeck().copyAll();
    }

    public boolean isHeroKilled() {
        return heroKilled;
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
        if(card instanceof Hero) heroKilled = true;
        this.graveYard.add(card);
    }
    public boolean moveArmy(Cell presentCell,Cell destinationCell) {
        if(presentCell == null || destinationCell == null) return false;
        if(destinationCell.isEmpty() || movedCardsInThisTurn.find(presentCell.getInsideArmy()) == null
        || attackerCardsInThisTurn.find(presentCell.getInsideArmy()) == null) return false;
        if(Cell.getDistance(presentCell,destinationCell) > 2) return false;
        Army army = presentCell.pick();
        movedCardsInThisTurn.add(army);
        return destinationCell.put(army,turnNumber);

    }
    public void putHeroIn(Cell cell) {
        Hero hero = deck.getHero();
        deck.deleteCard(hero);
        cell.put(hero,turnNumber);
    }
    public boolean attack(Cell attackersCell) {
        if(attackersCell == null) return false;
        if(attackerCardsInThisTurn.find(attackersCell.getInsideArmy()) == null) return false;
        return false;
    }
    public boolean moveFromHandToCell(int index,Cell cell) {
        if(cell.isEmpty() && mana >= hand.getNeededManaToMove(index)) {
            Card card = hand.pick(index);
            if(!(card instanceof Army)) return false;
            if(cell.put((Army) card,turnNumber)) {
                mana -= cell.getInsideArmy().getNeededManaToMove();
                movedCardsInThisTurn.add(card);
                attackerCardsInThisTurn.add(card);
                return true;
            }
        }
        return false;
    }
    public void useItem(){}
    public void startMatchSetup() { deck.fillHand(hand);
    }
    public void nextTurnSetup() {
        movedCardsInThisTurn.clear();
        attackerCardsInThisTurn.clear();
    }
    public void play() {
        increaseTurnNumber();
        setMana();
        deck.transferCardTo(hand);
        while(!endTurn) {
            BattlesOrderType orderType = BattleHandler.getPlayingOrder();
            //////////
        }
    }

    public void showHand() {
        hand.showCards();
        System.out.println("next card is:");
        deck.getNextCard().showCard();
    }

    public void showGraveYard() {
        graveYard.showCards();
    }

    public boolean haveCard(Card card){
        for(Card cardTemp : this.deck.getCards().getAllCards()){
            if(cardTemp == card){
                return true;
            }
        }
        return false;
    }
}
