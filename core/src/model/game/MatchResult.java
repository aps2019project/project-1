package model.game;

import model.other.Account;

import java.util.Date;

public class MatchResult {

    private Account firstPlayer;
    private Account secondPlayer;
    private Account winner;
    private Date date;
    private int reward;

    public MatchResult(Account firstPlayer, Account secondPlayer, Account winner, int reward) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
        this.date = new Date();
        this.reward = reward;
        //need to add functions
    }

    public Account getFirstPlayer() {
        return firstPlayer;
    }

    public Account getSecondPlayer() {
        return secondPlayer;
    }

    public Account getWinner() {
        return winner;
    }

    public Date getDate() {
        return date;
    }

    public int getReward() {
        return reward;
    }
}
