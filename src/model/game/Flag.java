package model.game;

import model.cards.Army;

public class Flag {
    private Army army;
    private Cell cell;
    private boolean taken = false;
    private int numberOfTurnItTaken = 0;

    public int getNumberOfTurnItTaken() {
        return numberOfTurnItTaken;
    }

    public Cell getCell() {
        return cell;
    }
    public Army getArmy() {
        return army;
    }
    public boolean isTaken() {
        return taken;
    }

    public void dropTo(Cell cell) {
        this.cell = cell;
        this.army = null;
        this.taken = false;
    }
    public void takeBy(Army army,int turnNumber) {
        this.army = army;
        this.cell = null;
        this.taken = true;
        this.numberOfTurnItTaken = turnNumber;
    }
}
