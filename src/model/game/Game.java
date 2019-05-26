package model.game;

import model.cards.Army;
import model.cards.Card;
import model.cards.Item;
import model.cards.Minion;
import model.other.Account;
import model.variables.CardsArray;

import java.util.ArrayList;

import static model.game.GameType.*;
import static model.variables.GlobalVariables.*;

public class Game {
    private static Game currentGame;
    private Player firstPlayer;
    private Player secondPlayer;
    private Cell[][] table = new Cell[TABLE_HEIGHT][TABLE_WIDTH];
    private ArrayList<Flag> flags = new ArrayList<>();
    private Player whoIsHisTurn;
    private Account winner;
    private int reward;
    private int turnNumber = 1;
    private GameType type;
    private ArrayList<Cell> allCellsInTable = new ArrayList<>();
    public Game(Account firstAccount, Account secondAccount, GameType type) {
        firstPlayer = new Player(firstAccount);
        secondPlayer = new Player(secondAccount);
        for(int counter1 = 0 ; counter1 < TABLE_HEIGHT ; counter1++) {
            for(int counter2 = 0 ; counter2 < TABLE_WIDTH ; counter2++) {
                table[counter1][counter2] = new Cell(counter1,counter2);
                allCellsInTable.add(table[counter1][counter2]);
            }
        }
        this.type = type;
        currentGame = this;
        table[2][2].setInsideItem(Item.getCollectableItems().getAllItems().get(0));
        table[2][6].setInsideItem(Item.getCollectableItems().getAllItems().get(3));
    }
    public Game(Account firstAccount, Account secondAccount, GameType type, int numberOfFlags) {
        this(firstAccount,secondAccount,type);
        if(numberOfFlags%2 == 1) {
            putFlagIn(table[TABLE_HEIGHT/2][TABLE_WIDTH/2]);
        }
        numberOfFlags/=2;
        if(numberOfFlags%2 == 1) {
            putFlagIn(table[TABLE_HEIGHT/2][TABLE_WIDTH/2-3]);
            putFlagIn(table[TABLE_HEIGHT/2][TABLE_WIDTH/2+3]);
        }
        numberOfFlags/=2;
        if(numberOfFlags%2 == 1) {
            putFlagIn(table[TABLE_HEIGHT/2+2][TABLE_WIDTH/2-2]);
            putFlagIn(table[TABLE_HEIGHT/2+2][TABLE_WIDTH/2+2]);
            putFlagIn(table[TABLE_HEIGHT/2-2][TABLE_WIDTH/2-2]);
            putFlagIn(table[TABLE_HEIGHT/2-2][TABLE_WIDTH/2+2]);
        }
    }
    public Game(Account firstAccount, IntelligentPlayer intelligentPlayer, GameType type, int numberOfFlags) {
        this(firstAccount,intelligentPlayer.getAccount(),type,numberOfFlags);
        intelligentPlayer.setGame(this);
        secondPlayer = intelligentPlayer;
    }
    public void putFlagIn(Cell cell) {
        Flag flag = new Flag();
        flags.add(flag);
        flag.dropTo(cell);
        cell.setFlag(flag);
    }
    public static Game getCurrentGame() {
        return currentGame;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Cell[][] getTable() {
        return table;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public Player getWhoIsHisTurn() {
        return whoIsHisTurn;
    }

    public Account getWinner() {
        return winner;
    }

    public int getReward() {
        return reward;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Player getWhoIsNotHisTurn() {
        if(whoIsHisTurn == firstPlayer) return secondPlayer;
        else                            return firstPlayer;
    }

    public GameType getType() {
        return type;
    }

    public void startMatch() {
        firstPlayer.putHeroIn(table[TABLE_HEIGHT /2][0]);
        secondPlayer.putHeroIn(table[TABLE_HEIGHT /2][TABLE_WIDTH -1]);
        firstPlayer.startMatchSetup();
        secondPlayer.startMatchSetup();
        whoIsHisTurn = firstPlayer;
        nextTurn();
    }
    public void nextTurn() {
        turnNumber++;
        firstPlayer.nextTurnSetup();
        secondPlayer.nextTurnSetup();
        whoIsHisTurn.play();
        firstPlayer.setUpBuffs();
        secondPlayer.setUpBuffs();
        setupDefends();
        setupCardsDeaf();
        if(whoIsHisTurn == firstPlayer) whoIsHisTurn = secondPlayer;
        else                            whoIsHisTurn = firstPlayer;
        if(isGameEnded())  return;
        nextTurn();
    }
    public MatchResult getResults() {
        return new MatchResult(firstPlayer.getAccount(),secondPlayer.getAccount(),winner,reward);
    }
    public void setupDefends(){
        for(Cell[] rows : table) {
            for(Cell cell : rows) {
                cell.defend();
                cell.setEnemyAttackersCell(null);
            }
        }

    }
    public void setupCardsDeaf() {
        for(Cell[] rows : table) {
            for(Cell cell : rows) {
                setupCardDeaf(cell);
            }
        }
    }
    public void setupCardDeaf(Cell cell) {
        if(cell.isEmpty() || cell.getInsideArmy().getHp() > 0) return;
        Card card = cell.pick();
        if(card.getFlag() != null) {
            card.getFlag().dropTo(cell);
            cell.add(card.getFlag());
        }
        if(card instanceof Minion) {
            ((Minion)card).chekcOnDeath(((Minion) card).getPlayer(), cell);
        }
        this.getPlayer(card.getAccount()).addToGraveYard(card);
    }
    public boolean isGameEnded() {
        if(this.type == KILL_HERO) {
            if(firstPlayer.isHeroKilled()) {
                winner = secondPlayer.getAccount();
                return true;
            }
            else if(secondPlayer.isHeroKilled()) {
                winner = firstPlayer.getAccount();
                return true;
            }
        }
        else if(this.type == CAPTURE_THE_FLAG) {
            Flag flag = flags.get(0);
            if(flag.isTaken() && turnNumber - flag.getNumberOfTurnItTaken()*2 > 7) {
                winner = flag.getArmy().getAccount();
                return true;
            }
        }
        else if(this.type == ROLLUP_FLAGS) {
            int firstPlayerFlags = 0;
            int secondPlayerFlags = 0;
            for(Flag flag : flags) {
                if(flag.isTaken() && flag.getArmy().getAccount() == firstPlayer.getAccount()) firstPlayerFlags++;
                else if(flag.isTaken() && flag.getArmy().getAccount() == secondPlayer.getAccount()) secondPlayerFlags++;
            }
            if(firstPlayerFlags > flags.size()/2) {
                winner = firstPlayer.getAccount();
                return true;
            }
            else if(secondPlayerFlags > flags.size()/2) {
                winner = secondPlayer.getAccount();
                return true;
            }
        }
        return false;
    }
    public Player getPlayer(Account account) {
        if(firstPlayer.getAccount() == account) return firstPlayer;
        else if(secondPlayer.getAccount() == account) return secondPlayer;
        else   return null;
    }
    public Cell findInTable(String ID){
        for(Cell[] row : table) {
            for(Cell cell : row) {
                if(!cell.isEmpty() && cell.getInsideArmy().getID().isSameAs(ID)) {
                    return cell;
                }
            }
        }
        return null;
    }
    public CardsArray getAllAccountArmiesInCellArray(ArrayList<Cell> cells , Account account) {
        CardsArray allArmiesInTable = new CardsArray();
        for(Cell cell : cells) {
            if(!cell.isEmpty() && cell.getInsideArmy().getAccount().equals(account)) allArmiesInTable.add(cell.getInsideArmy());
        }
        return allArmiesInTable;
    }

    public ArrayList<Cell> getAllCellsNearAccountArmies(Account account) {
        CardsArray cards = getAllAccountArmiesInCellArray(getAllCellsInTable(),account);
        ArrayList<Cell> cells = new ArrayList<>();
        for(Card card : cards.getAllCards()){
            cells.addAll(getAllNearCells(card.getWhereItIs()));
        }
        return cells;
    }
    public ArrayList<Cell> getAllCellsWithUniqueDistance(Cell cell,int distance) {
        ArrayList<Cell> allCellsWithUniqueDistance = new ArrayList<>();
        for(int xIncrease = -distance ; xIncrease <= distance ; xIncrease++) {
            for(int yIncrease = -distance ; yIncrease <= distance ; yIncrease++) {
                if(Math.abs(xIncrease)+Math.abs(yIncrease) <= distance && this.isTrueCoordinate(cell.getX()+xIncrease,cell.getY()+yIncrease)) {
                    allCellsWithUniqueDistance.add(table[cell.getX()+xIncrease][cell.getY()+yIncrease]);
                }
            }
        }
        return allCellsWithUniqueDistance;
    }
    public ArrayList<Cell> getAllNearCells(Cell cell) {
        ArrayList<Cell> allNearCells = new ArrayList<>();
        for(int xIncrease = -1 ; xIncrease < 2 ; xIncrease++) {
            for(int yIncrease = -1 ; yIncrease < 2 ; yIncrease++) {
                if(this.isTrueCoordinate(cell.getX()+xIncrease,cell.getY()+yIncrease)) {
                    allNearCells.add(table[cell.getX()+xIncrease][cell.getY()+yIncrease]);
                }
            }
        }
        return allNearCells;
    }
    public ArrayList<Cell> getAllNonNearCells(Cell cell) {
        ArrayList<Cell> allNoneNearCells = getAllCellsInTable();
        allNoneNearCells.removeAll(getAllNearCells(cell));
        return allNoneNearCells;
    }
    public ArrayList<Cell> getAllCellsInTable() {
        ArrayList<Cell> allCellsInTable = new ArrayList<>();
        allCellsInTable.addAll(this.allCellsInTable);
        return allCellsInTable;
    }
    public ArrayList<Cell> getAllCellsNearArmies(CardsArray armies) {
        ArrayList<Cell> allCellsNearArmies = new ArrayList<>();
        for(Card card : armies.getAllCards()) {
            allCellsNearArmies.addAll(getAllNearCells(card.getWhereItIs()));
        }
        return allCellsNearArmies;
    }
    public boolean isTrueCoordinate(int x,int y) {
        return x >= 0 && y >= 0 && x < TABLE_HEIGHT && y < TABLE_WIDTH;
    }
    public Account getAnotherAccount(Account account) {
        if(firstPlayer.getAccount().equals(account)) return secondPlayer.getAccount();
        else if(secondPlayer.getAccount().equals(account)) return firstPlayer.getAccount();
        else    return null;
    }
    public ArrayList<Army> getAllInGameCards(){
        ArrayList<Army> array = new ArrayList<>();
        array.addAll(firstPlayer.getInGameCards());
        array.addAll(secondPlayer.getInGameCards());
        return array;
    }
}
