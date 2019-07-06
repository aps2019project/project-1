package model.game;

import graphic.Others.ArmyAnimation;
import graphic.screen.BattleScreen;
import model.cards.*;
import model.other.Account;
import model.variables.CardsArray;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static model.variables.GlobalVariables.TABLE_HEIGHT;

public class Player {
    protected Account account;
    protected Deck deck;
    protected Hand hand = new Hand();
    protected int mana;
    protected CardsArray graveYard = new CardsArray();
//    protected CardsArray inGameCards = new CardsArray();
    protected CardsArray movedCardsInThisTurn = new CardsArray();
    protected CardsArray attackerCardsInThisTurn = new CardsArray();
    protected int turnNumber = 0;
    protected int numberOfFlags = 0;
    protected boolean endTurn = false;
    protected boolean heroKilled = false;
    protected Hero hero;
    protected Cell selectedCardPlace;
    protected boolean inGraveYard = false;
    private CardsArray collectibleItem = new CardsArray();
    private Item usableItem;
    protected Cell selectedCellToPutFromHand;
    private boolean usedManaPotion;
    private int usedSpecialPowerTurn = 0;
    protected Item selectedItem;
    private String command;
    private String opponentCommand;

    public Player(Account account){
        this(account,account.getMainDeck());
    }

    public Player(Account account,Deck deck) {
        this.account = account;
        this.deck = deck.copyAll();
        for(Card card : deck.getCards().getAllCards()) {
            card.setUserName(account.getUsername());
        }
        this.usableItem = this.deck.getItem();
        //this.collectibleItem.add(this.usableItem);
    }

    public void useManaPotion() {
        usedManaPotion = true;
    }

    public Cell getSelectedCardPlace() {
        return selectedCardPlace;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Item getSelectedItem() {
        return selectedItem;
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

    public void exitFromGraveYard() {
        inGraveYard = false;
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

    public CardsArray getCollectibleItem() {
        return collectibleItem;
    }

    public void addItem(Item item) {
        this.collectibleItem.add(item);
    }

    public Hero getHero() {
        if (hero == null)
            hero = deck.getHero();
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
        if (this.usableItem != null && this.usableItem.getName().equals("WisdomCrown") && turnNumber<4) mana++;
        if (this.usableItem != null && this.usableItem.getName().equals("KingWisdom")) mana++;
        if (this.usedManaPotion) mana += 3;
    }

    public void addToGraveYard(Card card) {
        if (card instanceof Hero) heroKilled = true;
        this.graveYard.add(card);
    }

    public boolean moveArmy(Cell presentCell, Cell destinationCell) {
        if(!this.canMove(presentCell, destinationCell)) return false;
        Army army = presentCell.pick();
        movedCardsInThisTurn.add(army);
        HashMap<Army, ArmyAnimation> animations = BattleScreen.getAnimations();
        if(animations.get(army) != null) {
            animations.get(army).run(destinationCell.getScreenX(), destinationCell.getScreenY());
        }
        else {
//            System.out.println("animation army is null");
        }
        return destinationCell.put(army, turnNumber);
    }

    public boolean canMove(Cell presentCell, Cell destinationCell) {
        if (presentCell == null || destinationCell == null) return false;
        if (!destinationCell.isEmpty() || movedCardsInThisTurn.find(presentCell.getInsideArmy()) != null
                || attackerCardsInThisTurn.find(presentCell.getInsideArmy()) != null) return false;
        if (Cell.getDistance(presentCell, destinationCell) > 2) return false;
        return true;
    }

    public void putHeroIn(Cell cell) {
        Hero hero = deck.getHero();
        this.hero = hero;
        deck.deleteCard(hero);
        cell.put(hero, turnNumber);
//        this.inGameCards.add(hero);
    }
    public boolean isInRange(Cell attackersCell,Cell defenderCell) {
        if(attackersCell.isEmpty() || defenderCell.isEmpty()) return false;
        return     (attackersCell.getInsideArmy().getAttackType() == AttackType.MELEE && Cell.isNear(attackersCell,defenderCell))
                || (attackersCell.getInsideArmy().getAttackType() == AttackType.RANGED && !Cell.isNear(attackersCell,defenderCell))
                || (attackersCell.getInsideArmy().getAttackType() == AttackType.HYBRID);
    }
    public boolean attackCombo(Cell opponentCardCell,Cell myCardCell,ArrayList<Cell> cells) {
        boolean trueAttack = true;
        CardsArray cardsArray = new CardsArray();
        for(Cell cell : cells) {
            if(cell == null || !isInRange(myCardCell,cell)) trueAttack = false;
            cardsArray.add(cell.getInsideArmy());
        }
        if(!trueAttack) return false;
        myCardCell.getInsideArmy().attackCombo(opponentCardCell.getInsideArmy(),cardsArray);
        return true;
    }
    public boolean attack(Cell attackersCell,Cell defenderCell) {
        if(attackersCell == null){
            BattleScreen.setPopUp("Target Cell Is Empty");
            return false;
        }
        if(!isInRange(attackersCell,defenderCell)){
            BattleScreen.setPopUp("Target Not in range");
            return false;
        }
        attackersCell.getInsideArmy().attack(defenderCell.getInsideArmy());
        this.counterAttack(defenderCell, attackersCell);
        return true;
    }

    public void counterAttack(Cell attackersCell,Cell defenderCell) {
        if(attackersCell == null) return;
        if(!isInRange(attackersCell,defenderCell)) return;
        if(defenderCell.getInsideArmy().isDisarmed()) return;
        Army attacker = attackersCell.getInsideArmy();
        if(attacker instanceof  Minion){
            ((Minion)attacker).checkOnDefend(attackersCell.getInsideArmy());
        }
        attackersCell.getInsideArmy().attack(defenderCell.getInsideArmy());
    }

    public boolean attack(Cell defenderCell) {
        return attack(selectedCardPlace, defenderCell);
    }

    public boolean attackCombo() {
        //
        return false;
    }

    public boolean heroHaveSpecialPower() {
        if(this.getHero().getName().equals("Rostam")) return false;
        return true;
    }

    public boolean useSpecialPower(Cell cell) {
        selectedCardPlace = cell;
        if(this.getHero().getMp() > this.mana){
            BattleScreen.setPopUp("Not Enough Mana");
            return false;
        }
        if(!canUseHeroSp()){
            return false;
        }
        try {
            this.getHero().useSpell(this);
        } catch (Exception e) { e.printStackTrace();}
        this.mana -= this.getHero().getMp();
        this.usedSpecialPowerTurn = this.turnNumber;
        return true;
    }

    public boolean canUseHeroSp() {
        return this.usedSpecialPowerTurn == 0 || (this.turnNumber - this.usedSpecialPowerTurn >= this.getHero().getCoolDown());
    }

    public boolean moveFromHandToCell(Card card,Cell cell) {
        if( cell != null && cell.isEmpty() &&
            Game.getCurrentGame().getAllCellsNearAccountArmies(account).indexOf(cell) != -1) {
            if(mana < card.getNeededManaToPut()){
                BattleScreen.setPopUp("Not Enough Mana");
                return false;
            }
            this.hand.remove(card);
            if(card instanceof Spell) {
                selectedCellToPutFromHand = cell;
                try {
                    Spell.useSpell(this, card.getName());
                } catch (Exception e) {}
                return true;
            }
            else if(card instanceof Army && cell.put((Army) card,turnNumber)) {
                mana -= cell.getInsideArmy().getNeededManaToPut();
                movedCardsInThisTurn.add(card);
                attackerCardsInThisTurn.add(card);
//                this.inGameCards.add(card);
                if(card instanceof Minion){
                    Minion minion = (Minion)card;
                    minion.checkOnSpawn(this, cell);
                    minion.checkPassive(this, cell);
                }
                return true;
            }
        }
        BattleScreen.setPopUp("Invalid Cell");
        return false;
    }

    public void usableItemEffect(String itemName) throws IllegalAccessException, InvocationTargetException {
        try {
            Item.class.getDeclaredMethod(itemName + "Usable", Player.class).invoke(null, this);
        } catch (NoSuchMethodException n) { }
    }

    public void useCollectibleItem(String itemName, Army army) throws IllegalAccessException, InvocationTargetException {
        for(Item item : collectibleItem.getAllItems()) {
            if(item.getName().equals(itemName)) {
                collectibleItem.getAllItems().remove(item);
                break;
            }
        }
        try {
            Item.class.getDeclaredMethod(itemName + "Collectible", Player.class, Army.class).invoke(null, this, army);
        } catch (NoSuchMethodException n) { }
    }

    public void startMatchSetup() {
        deck.fillHand(hand);
        if(this.usableItem == null) return;
        try {
            this.usableItemEffect(this.getUsableItem().getName());

        }catch (Exception e){ }
    }

    public void nextTurnSetup() {
        movedCardsInThisTurn.clear();
        attackerCardsInThisTurn.clear();
    }

    public void setUpBuffs() {
        Army.decreaseBuffTurns(this.getInGameCards());
        Army.ActivateContinuousBuffs(this.getInGameCards());
        Army.checkPoisonAndBleeding(this.getInGameCards());
    }

    public void play() {
        endTurn = false;
        this.setUpBuffs();
        this.checkPassive();
        increaseTurnNumber();
        setMana();
        deck.transferCardTo(hand);
        while(!endTurn && !Game.getCurrentGame().isExitFromGame()) {
            synchronized (Game.getCurrentGame()){
                try{
                    Game.getCurrentGame().wait();
                } catch (InterruptedException i){
                    return;
                }
            }
            handleCommands();
        }
    }

    public void handleCommands() {
        command = null;
        command = BattleScreen.getCommand();
        BattleScreen.setCommand(null);
        if(command.matches("end turn"))
            this.endTurn = true;
//        } else if(command.contains("select")){
//            this.select();
//        }
    }

    public void select() {
        Game game = Game.getCurrentGame();
        if(!this.setSelectedCard(game.findInTable(command.split(" ")[1]))) {
            if (game.getWhoIsHisTurn().getCollectibleItem().find(command.split(" ")[1]) != null) {
                try {
                    Item item = (Item) game.getWhoIsHisTurn().getCollectibleItem().find(command.split(" ")[1]);
                    game.getWhoIsHisTurn().setSelectedItem(item);
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    public void checkPassive() {
        for(Cell cell : Game.getCurrentGame().getAllCellsInTable()){
            Army army = cell.getInsideArmy();
            if(army == null || army.getType() == CardType.HERO || !this.isFriend(army)) continue;
            Minion minion = (Minion)army;
            minion.checkPassive(this, cell);
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
        ArrayList<Army> army = new ArrayList<Army>();
        for(Cell cell : Game.getCurrentGame().getAllCellsInTable()){
            if(cell.getInsideArmy() == null) continue;
            if(this.isFriend(cell.getInsideArmy()))
                army.add(cell.getInsideArmy());
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
        ArrayList<Cell> cells = new ArrayList<Cell>();
        Collections.addAll(cells, Game.getCurrentGame().getTable()[hero.getWhereItIs().getX()]);
        return Game.getCurrentGame().getAllAccountArmiesInCellArray(cells,account);
    }

    public ArrayList<Cell> getSquare(int size) {
        ArrayList<Cell> cells = new ArrayList<Cell>();
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
        ArrayList<Cell> cells = new ArrayList<Cell>();
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

    public CardsArray getEnemiesAround(Cell cell) {
        return  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllNearCells(cell),
                Game.getCurrentGame().getAnotherAccount(account));

    }


    public Army getNearestEnemy(Cell cell) {
        CardsArray cards =  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllCellsInTable(),
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

    public void goToGraveYard() {
        inGraveYard = true;
    }

    public void ExitFromGraveYard() {
        inGraveYard = false;
    }

    public CardsArray getFriendsAround(Cell cell) {
        return  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllNearCells(cell),account);
    }

    public CardsArray getEnemiesInDistance2(Cell cell) {
        return  Game.getCurrentGame().getAllAccountArmiesInCellArray(Game.getCurrentGame().getAllCellsWithUniqueDistance(cell,2),
                Game.getCurrentGame().getAnotherAccount(account));

    }

    public boolean isInGraveYard() {
        return inGraveYard;
    }

    public Cell getOneCell() {
        return selectedCardPlace;
    }

    public boolean isFriend(Army army) {
        return army.getAccount().getUsername().equals(this.getAccount().getUsername());
//        return inGameCards.find(army) != null;
    }

    public void setHandAnimations(HashMap<Army, ArmyAnimation> animations) {
        for(Card card : hand.getAllCards()){
            if(card.getType() == CardType.ITEM || card.getType() == CardType.SPELL) continue;
            if(animations.containsKey((Army)card)) continue;
            ArmyAnimation animation = new ArmyAnimation(card.getGifPath());
            animations.put((Army) card, animation);
        }
    }

    public boolean isAroundArmies(Cell cell) {
        ArrayList<Cell> cells = Game.getCurrentGame().getAllCellsNearAccountArmies(account);
        for(Cell cell1 : cells){
            if(cell == cell1) return true;
        }
        return false;
    }

    public void getOpponentCommands(){
        while(!endTurn && !Game.getCurrentGame().isExitFromGame()) {
            synchronized (Game.getCurrentGame()){
                try{
                    Game.getCurrentGame().wait();
                } catch (InterruptedException i){
                    return;
                }
            }
            handleCommands();
        }
    }

}
