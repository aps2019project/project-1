package model.game;

import model.other.Account;

import java.util.Date;

public class MatchResult {

    private String firstPlayer;
    private String secondPlayer;
    private String winner;
    private Date date;
    private int reward;

    public MatchResult(Account firstPlayer, Account secondPlayer, Account winner, int reward) {
        this.firstPlayer = firstPlayer.getUsername();
        this.secondPlayer = secondPlayer.getUsername();
        this.winner = winner.getUsername();
        this.date = new Date();
        this.reward = reward;
        //need to add functions
    }

    public Account getFirstPlayer() {
        return Account.findAccount(firstPlayer);
    }

    public Account getSecondPlayer() {
        return Account.findAccount(secondPlayer);
    }

    public Account getWinner() {
        return Account.findAccount(winner);
    }

    public Date getDate() {
        return date;
    }

    public int getReward() {
        return reward;
    }
}
