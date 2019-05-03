package model.game;

import model.cards.Army;

public class Cell {
    private Army insideArmy;
    private CellEffect effect;
    private Flag flag;
    private int x;
    private int y;
    private Cell EnemyAttackersCell;

    public Cell(int x,int y) {
        this.x = x;
        this.y = y;
    }
    public Army getInsideArmy() {
        return insideArmy;
    }

    public void setEnemyAttackersCell(Cell enemyAttackersCell) {
        EnemyAttackersCell = enemyAttackersCell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return insideArmy == null;
    }
    public boolean put(Army army,int turnNumber) {
        if(this.isEmpty() || army == null) return false;
        this.insideArmy = army;
        army.setWhereItIs(this);
        if(flag != null) {
            flag.takeBy(army,turnNumber);
        }
        return true;
    }
    public Army pick() {
        Army army = insideArmy;
        insideArmy = null;
        army.setWhereItIs(null);
        return army;
    }
    public void add(Flag flag) {
        this.flag = flag;
    }
    public void defend() {
        Attack(EnemyAttackersCell);
    }
    public void Attack(Cell defenderCell) {
        if(defenderCell == null) return;
        //////
        defenderCell.setEnemyAttackersCell(this);
    }
    public static int getDistance(Cell firstCell, Cell secondCell) {
        return  Math.abs(firstCell.getX() - secondCell.getX()) +
                Math.abs(firstCell.getY() - secondCell.getY()) ;
    }

    public void getEffect(){

    }
    public void ActionPoisonBuff() {
        insideArmy.decreaseHp(1);
    }
    public void ActionFire() {
        insideArmy.decreaseHp(2);
    }
}
