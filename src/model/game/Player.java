package model.game;

import control.BattleHandler;
import control.BattlesOrderType;
import model.cards.*;
import model.other.Account;
import model.variables.CardsArray;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.cards.AttackType.HYBRID;
import static model.cards.AttackType.RANGED;

public class Player {

    private Account account;
    private Deck deck;
    private Hand hand;
    private int mana;
    private CardsArray graveYard = new CardsArray();
    private CardsArray inGameCards = new CardsArray();
    private CardsArray movedCardsInThisTurn = new CardsArray();
    private CardsArray attackerCardsInThisTurn = new CardsArray();
    private int turnNumber = 0;
    private int numberOfFlags = 0;
    private boolean endTurn = false;
    private boolean heroKilled = false;
    private Hero hero;
    private Cell selectedCardPlace;
    private Item item;

    public Player(Account account) throws CloneNotSupportedException {
        this.account = account;
        this.deck = account.getMainDeck().copyAll();
        this.item = this.deck.getItem();
    }

    public Cell getSelectedCardPlace() {
        return selectedCardPlace;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public CardsArray getGraveYard() {
        return graveYard;
    }

    public CardsArray getMovedCardsInThisTurn() {
        return movedCardsInThisTurn;
    }

    public CardsArray getAttackerCardsInThisTurn() {
        return attackerCardsInThisTurn;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public Hero getHero() {
        return hero;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public boolean setSelectedCard(Cell selectedCardPlace) {
        if(selectedCardPlace == null) return false;
        this.selectedCardPlace = selectedCardPlace;
        return true;
    }

    public boolean isEndTurn() {
        return endTurn;
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
        this.hero = hero;
        deck.deleteCard(hero);
        cell.put(hero,turnNumber);
        this.inGameCards.add(hero);
    }
    public boolean attack(Cell attackersCell,Cell defenderCell) {
        if(attackersCell == null) return false;
        if(attackerCardsInThisTurn.find(attackersCell.getInsideArmy()) == null) return false;
            return false;//
    }
    public boolean attack(Cell defenderCell) {
        return attack(selectedCardPlace,defenderCell);
    }

    public boolean moveFromHandToCell(String name,Cell cell) {
        if(cell.isEmpty() && mana >= hand.getNeededManaToMove(name)) {
            Card card = hand.pick(name);
            if(!(card instanceof Army)) return false;
            if(cell.put((Army) card,turnNumber)) {
                mana -= cell.getInsideArmy().getNeededManaToPut();
                movedCardsInThisTurn.add(card);
                attackerCardsInThisTurn.add(card);
                this.inGameCards.add(card);
                return true;
            }
        }
        return false;
    }

    public void usableItemEffect(String itemName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        try {
            Item.class.getDeclaredMethod(itemName + "Usable", Player.class).invoke(null, this);
        } catch (NoSuchMethodException n){

        }
    }

    public void startMatchSetup() { deck.fillHand(hand);
    }
    public void nextTurnSetup() {
        movedCardsInThisTurn.clear();
        attackerCardsInThisTurn.clear();
    }
    public void play() {
        endTurn = false;
        increaseTurnNumber();
        setMana();
        deck.transferCardTo(hand);
        while(!endTurn) {
            BattlesOrderType orderType = BattleHandler.getPlayingOrder();
            //////////
        }
    }
    public boolean haveCard(Card card){
        for(Card cardTemp : this.deck.getCards().getAllCards()){
            if(cardTemp == card){
                return true;
            }
        }
        return false;
    }

    public Player getEnemyPlayer(){
        Game game = Game.getCurrentGame();
        if(game.getFirstPlayer().equals(this))
            return game.getSecondPlayer();
        else
            return game.getFirstPlayer();
    }

    public ArrayList<Army> getInGameCards() {
        ArrayList<Army> army = new ArrayList<>();
        for(Card card : this.inGameCards.getAllCards()){
            army.add( (Army)card );
        }
        return army;
    }

    public Army getOneEnemy(){
        //
        return null;
    }

    public Army getOneFriend(){
        //
        return null;
    }

    public ArrayList<Army> getEnemiesInHeroRow(){
        ArrayList<Army> array = new ArrayList<>();
        //
        return array;
    }

    public ArrayList<Cell> getSquare2(){
        ArrayList<Cell> array = new ArrayList<>();
        //
        return array;
    }

    public ArrayList<Cell> getSquare3(){
        ArrayList<Cell> array = new ArrayList<>();
        //
        return array;
    }

    public Army getOneEnemyOrFriend(){
        //
        return null;
    }

    public ArrayList<Army> getAllEnemiesInOneColumn(){
        ArrayList<Army> array = new ArrayList<>();
        //
        return array;
    }

    public Minion getOneFriendMinion(){
        //
        return null;
    }

    public Minion getOneEnemyMinion(){
        //
        return null;
    }

    public Minion getRandomMinionAroundFriendHero(){
        //
        return null;
    }

}
