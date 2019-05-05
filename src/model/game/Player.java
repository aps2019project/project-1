package model.game;

import control.BattleHandler;
import control.BattlesOrderType;
import model.cards.*;
import model.other.Account;
import model.variables.CardsArray;
import model.variables.GlobalVariables;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static model.cards.AttackType.HYBRID;
import static model.cards.AttackType.RANGED;

import java.util.ArrayList;

import static model.variables.GlobalVariables.TABLE_HEIGHT;

public class Player {
    protected Account account;
    protected Deck deck;
    protected Hand hand;
    protected int mana;
    protected CardsArray graveYard = new CardsArray();
    protected CardsArray inGameCards = new CardsArray();
    protected CardsArray movedCardsInThisTurn = new CardsArray();
    protected CardsArray attackerCardsInThisTurn = new CardsArray();
    protected int turnNumber = 0;
    protected int numberOfFlags = 0;
    protected boolean endTurn = false;
    protected boolean heroKilled = false;
    protected Hero hero;
    protected Cell selectedCardPlace;
    protected boolean InGraveYard = false;
    private Item usableItem;
    protected Cell selectedCellToPutFromHand;
    public Player(Account account){
        this(account,account.getMainDeck());
    }
    public Player(Account account,Deck deck) {
        this.account = account;
        this.deck = deck.copyAll();
        this.usableItem = this.deck.getItem();
    }


    public Cell getSelectedCardPlace() {
        return selectedCardPlace;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
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

    public Item getUsableItem() {
        return usableItem;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public boolean setSelectedCard(Cell selectedCardPlace) {
        if (selectedCardPlace == null) return false;
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
        if (turnNumber <= 7) mana = turnNumber + 1;
        else mana = 9;
        if (this.usableItem.getName().equals("WisdomCrown") && turnNumber<4) mana++;
        if (this.usableItem.getName().equals("KingWisdom")) mana++;
    }

    public void addToGraveYard(Card card) {
        if (card instanceof Hero) heroKilled = true;
        this.graveYard.add(card);
    }

    public boolean moveArmy(Cell presentCell, Cell destinationCell) {
        if (presentCell == null || destinationCell == null) return false;
        if (destinationCell.isEmpty() || movedCardsInThisTurn.find(presentCell.getInsideArmy()) == null
                || attackerCardsInThisTurn.find(presentCell.getInsideArmy()) == null) return false;
        if (Cell.getDistance(presentCell, destinationCell) > 2) return false;
        Army army = presentCell.pick();
        movedCardsInThisTurn.add(army);
        return destinationCell.put(army, turnNumber);
    }

    public void putHeroIn(Cell cell) {
        Hero hero = deck.getHero();
        this.hero = hero;
        deck.deleteCard(hero);
        cell.put(hero, turnNumber);
        this.inGameCards.add(hero);
    }
    public boolean isInRange(Cell attackersCell,Cell defenderCell) {
        if(attackersCell.isEmpty()) return false;
        return     (attackersCell.getInsideArmy().getAttackType() == AttackType.MELEE && Cell.isNear(attackersCell,defenderCell))
                || (attackersCell.getInsideArmy().getAttackType() == AttackType.RANGED && !Cell.isNear(attackersCell,defenderCell))
                || (attackersCell.getInsideArmy().getAttackType() == AttackType.HYBRID);
    }
    public boolean attack(Cell attackersCell,Cell defenderCell) {
        if(attackersCell == null) return false;
        if(attackersCell.getInsideArmy().getNeededManaToAttack() > mana || attackerCardsInThisTurn.find(attackersCell.getInsideArmy()) == null) return false;
        if(!isInRange(attackersCell,defenderCell)) return false;
        mana -= attackersCell.getInsideArmy().getNeededManaToAttack();
        attackersCell.getInsideArmy().attack(defenderCell.getInsideArmy());
        return true;

    }

    public boolean attack(Cell defenderCell) {
        return attack(selectedCardPlace, defenderCell);
    }

    public boolean attackCombo() {
        //
        return false;
    }
    public boolean moveFromHandToCell(String name,Cell cell) {
        if( cell.isEmpty() &&
            Game.getCurrentGame().getAllCellsNearAccountArmies(account).indexOf(cell) != -1 &&
            mana >= hand.getNeededManaToMove(name)) {
            Card card = hand.pick(name);
            if(card instanceof Spell) {
                selectedCellToPutFromHand = cell;
                //useSpell
                return true;
            }
            else if(card instanceof Army && cell.put((Army) card,turnNumber)) {
                mana -= cell.getInsideArmy().getNeededManaToPut();
                movedCardsInThisTurn.add(card);
                attackerCardsInThisTurn.add(card);
                this.inGameCards.add(card);
                ((Army)card).setPlayer(this);
                if(card instanceof Minion){
                    Minion minion = (Minion)card;
                    minion.checkOnSpawn(this, cell);
                }
                return true;
            }
        }
        return false;
    }

    public void usableItemEffect(String itemName) throws IllegalAccessException, InvocationTargetException {
        try {
            Item.class.getDeclaredMethod(itemName + "Usable", Player.class).invoke(null, this);
        } catch (NoSuchMethodException n) {

        }
    }

    public void collectibleItemEffect(String itemName, Army army) throws IllegalAccessException, InvocationTargetException {
        try {
            Item.class.getDeclaredMethod(itemName + "Collectible", Player.class, Army.class).invoke(null, this, army);
        } catch (NoSuchMethodException n) {

        }
    }

    public void useCollectibleItem() {
        //
    }


    public void startMatchSetup() {
        deck.fillHand(hand);
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
        while (!endTurn) {
            BattlesOrderType orderType = BattleHandler.getPlayingOrder();
            //////////
        }
    }

    public boolean haveCard(Card card) {
        for (Card cardTemp : this.deck.getCards().getAllCards()) {
            if (cardTemp == card) {
                return true;
            }
        }
        return false;
    }

    public Player getEnemyPlayer() {
        Game game = Game.getCurrentGame();
        if (game.getFirstPlayer().equals(this))
            return game.getSecondPlayer();
        else
            return game.getFirstPlayer();
    }

    public ArrayList<Army> getInGameCards() {
        ArrayList<Army> army = new ArrayList<>();
        for (Card card : this.inGameCards.getAllCards()) {
            army.add((Army) card);
        }
        return army;
    }


    public Army getOneEnemy(){
        if(!selectedCellToPutFromHand.isEmpty() && !selectedCellToPutFromHand.getInsideArmy().getAccount().equals(account)) {
            return selectedCardPlace.getInsideArmy();
        }
        return null;
    }

    public Army getOneFriend(){
        if(!selectedCellToPutFromHand.isEmpty() && selectedCellToPutFromHand.getInsideArmy().getAccount().equals(account)) {
            return selectedCardPlace.getInsideArmy();
        }
        return null;
    }

    public CardsArray getEnemiesInHeroRow(){
        ArrayList<Cell> cells = new ArrayList<>();
        for(Cell cell : Game.getCurrentGame().getTable()[hero.getWhereItIs().getX()]) {
            cells.add(cell);
        }
        return Game.getCurrentGame().getAllAccountArmiesInCellArray(cells,account);
    }

    public ArrayList<Cell> getSquare(int size) {
        ArrayList<Cell> cells = new ArrayList<>();
        for(int xIncrease = 0 ; xIncrease < size ; xIncrease++) {
            for(int yIncrease = 0 ; yIncrease < size ; yIncrease++) {
                if(Game.getCurrentGame().isTrueCoordinate(  selectedCellToPutFromHand.getX()+xIncrease,
                                                            selectedCellToPutFromHand.getY()+yIncrease)) {
                    cells.add(Game.getCurrentGame().getTable()[selectedCellToPutFromHand.getX()+xIncrease]
                                                              [selectedCellToPutFromHand.getY()+yIncrease]);
                }
            }
        }
        return cells;
    }
    public ArrayList<Cell> getSquare2(){
        return getSquare(2);
    }

    public ArrayList<Cell> getSquare3(){
        return getSquare(3);
    }

    public Army getOneEnemyOrFriend(){
        if(getOneEnemy() != null) return getOneEnemy();
        else if(getOneFriend() != null) return getOneFriend();
        else return null;
    }

    public CardsArray getAllEnemiesInOneColumn(){
        ArrayList<Cell> cells = new ArrayList<>();
        for(int counter = 0 ; counter < TABLE_HEIGHT ; counter++) {
            cells.add(Game.getCurrentGame().getTable()[counter][selectedCellToPutFromHand.getY()]);
        }
        return Game.getCurrentGame().getAllAccountArmiesInCellArray(cells,Game.getCurrentGame().getAnotherAccount(account));
    }

    public Minion getOneFriendMinion(){
        Card card = getOneFriend();
        if(card instanceof Minion) return (Minion) card;
        return null;
    }

    public Minion getOneEnemyMinion(){
        Card card = getOneEnemy();
        if(card instanceof Minion) return (Minion) card;
        return null;
    }

    public Minion getRandomMinionAroundFriendHero(){
        ArrayList<Cell> cells = Game.getCurrentGame().getAllNearCells(hero.getWhereItIs());
        CardsArray cards = Game.getCurrentGame().getAllAccountArmiesInCellArray(cells,account);
        int counter = 0;
        while(counter < cards.getAllCards().size()) {
            if(!(cards.getAllCards().get(counter) instanceof Minion)) cards.remove(cards.getAllCards().get(counter));
        }
        return (Minion)cards.getRandomCard();
    }
    public void goToGraveYard() {
        InGraveYard = true;
    }
    public void ExitFromGraveYard() {
        InGraveYard = false;
      
    public CardsArray getEnemiesAround(Cell cell) {
        return  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllNearCells(cell),
                Game.getCurrentGame().getAnotherAccount(account));
        
    }

    public CardsArray getFriendsAround(Cell cell) {
        return  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllNearCells(cell),account);
    }

    public CardsArray getEnemiesInDistance2(Cell cell) {
        return  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllCellsWithUniqueDistance(cell,2),
                Game.getCurrentGame().getAnotherAccount(account));

    }

    public Army getNearestEnemy(Cell cell) {
        CardsArray cards =  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getTable(),
                            Game.getCurrentGame().getAnotherAccount(account));
        Card nearestEnemy =  cards.getRandomCard();
        if(nearestEnemy == null) return null;
        for(Card card : cards.getAllCards()) {
            if(Cell.getDistance(cell,card.getWhereItIs()) < Cell.getDistance(cell,nearestEnemy.getWhereItIs())) {
                nearestEnemy = card;
            }
        }
        return (Army)nearestEnemy;
    }

    public boolean isInGraveYard() {
        return InGraveYard;
    }
}
