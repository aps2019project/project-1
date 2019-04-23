package model.game;

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
    private int turnNumber;
    private GameType type;

    public void startMatch() {
        firstPlayer.putHeroIn(table.get(HEIGHT/2).get(0));
        secondPlayer.putHeroIn(table.get(HEIGHT/2).get(WIDTH-1));
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
}
