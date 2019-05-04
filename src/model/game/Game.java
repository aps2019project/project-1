package model.game;

import model.cards.Card;
import model.other.Account;

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

    public Game(Account firstAccount, Account secondAccount, GameType type) throws CloneNotSupportedException {
        firstPlayer = new Player(firstAccount);
        secondPlayer = new Player(secondAccount);
        this.type = type;
        currentGame = this;
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
        if( cell.getInsideArmy().getHp() > 0) return;
        Card card = cell.pick();
        if(card.getFlag() != null) {
            card.getFlag().dropTo(cell);
            cell.add(card.getFlag());
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
}
