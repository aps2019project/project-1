package model.game;

import model.cards.Card;
import model.other.Account;

import java.util.ArrayList;

import static model.variables.GlobalVariables.*;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    ArrayList<ArrayList<Cell>> table = new ArrayList<>();
    private int time = 0;
    private Player whoIsHisTurn;
    private Account winner;
    private int reward;
    private int turnNumber = 1;
    private GameType type;

    public Game(Account firstAccount,Account secondAccount,GameType type) {//////////
        for(int counter = 0 ; counter < TABLE_HEIGHT ; counter++) {
            ArrayList<Cell> row = new ArrayList<>();
            for(int counter1 = 0 ; counter1 <TABLE_WIDTH ; counter1++) {
                row.add(new Cell());
            }
            table.add(row);
        }
        firstPlayer = new Player(firstAccount);
        secondPlayer = new Player(secondAccount);
        this.type = type;
     }
    public void startMatch() {
        firstPlayer.putHeroIn(table.get(TABLE_HEIGHT /2).get(0));
        secondPlayer.putHeroIn(table.get(TABLE_HEIGHT /2).get(TABLE_WIDTH -1));
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

        if(whoIsHisTurn == firstPlayer) whoIsHisTurn = secondPlayer;
        else                            whoIsHisTurn = firstPlayer;
    }
    public MatchResult getResults() {
        return new MatchResult(firstPlayer.getAccount(),secondPlayer.getAccount(),winner,reward);
    }
    public void setupCardDeaf(Cell cell) {
        Card card = cell.pick();
        if(card.getFlag() != null) {
            card.getFlag().dropTo(cell);
            cell.add(card.getFlag());
        }
        if(card.getAccount() == firstPlayer.getAccount()) {
            firstPlayer.addToGraveYard(card);
        }
        else {
            secondPlayer.addToGraveYard(card);
        }
    }
}
