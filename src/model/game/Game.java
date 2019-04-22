package model.game;

import model.other.Account;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    ArrayList<ArrayList<Cell>> table = new ArrayList<>();
    private int time = 0;
    private Player turn;
    private Account winner;
    private int reward;
    private int TurnNumber;
    private GameType type;

    public void startMatch() {

    }
    public void nextTurn() {

    }
    public MatchResult getResults() {
        return new MatchResult(firstPlayer.getAccount(),secondPlayer.getAccount(),winner,reward);
    }
}
