package model.game;

import model.cards.Army;
import model.cards.Item;

import java.util.ArrayList;

public class Cell {
    private Army insideArmy;
    private Item insideItem;
    private CellEffect effect;
    private Flag flag;
    private int x;
    private int y;
    private float screenX;
    private float screenY;
    private Cell EnemyAttackersCell;

    public Cell(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void removeItem() {
        this.insideItem = null;
    }

    public void setEffect(CellEffect effect) {
        this.effect = effect;
    }

    public void setInsideItem(Item insideItem) {
        this.insideItem = insideItem;
    }

    public Item getInsideItem() {
        return insideItem;
    }

    public Army getInsideArmy() {
        return insideArmy;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
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
        if(!this.isEmpty() || army == null) return false;
        this.insideArmy = army;
        army.setWhereItIs(this);
        if(flag != null) {
            flag.takeBy(army,turnNumber);
        }
        if(this.insideItem != null){
            army.getPlayer().addItem(this.insideItem);
            this.removeItem();
        }
        getEffect();
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
    public static boolean isNear(Cell firstCell ,Cell secondCell) {
        return Math.abs(firstCell.getX() - secondCell.getX()) < 2 && Math.abs(firstCell.getY() - secondCell.getY()) < 2;
    }
    public static Cell getRandomCell(ArrayList<Cell> cells) {
        int index = (int)Math.floor(Math.random()*cells.size());
        return cells.get(index);
    }
    public void getEffect(){
        if(effect == CellEffect.FIERY) ActionFire();
        else if(effect == CellEffect.POISON) ActionPoisonBuff();
    }
    public void ActionPoisonBuff() {
        insideArmy.decreaseHp(1);
    }
    public void ActionFire() {
        insideArmy.decreaseHp(2);
    }

    public void setInsideArmy(Army insideArmy) {
        this.insideArmy = insideArmy;
    }

    public float getScreenX() {
        return screenX;
    }

    public void setScreenX(float screenX) {
        this.screenX = screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public void setScreenY(float screenY) {
        this.screenY = screenY;
    }
}
