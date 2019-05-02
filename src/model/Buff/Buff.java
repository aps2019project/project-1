package model.Buff;

public class Buff {
    protected int number;
    protected int turns;
    protected BuffType buffType;
    public Buff(int number, int turns, BuffType buffType){
        this.number = number;
        this.turns = turns;
        this.buffType = buffType;
    }

    public int getNumber() {
        return number;
    }

    public int getTurns() {
        return turns;
    }

    public BuffType getBuffType() {
        return buffType;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void decreaseTurns (){
        this.turns --;
    }
}
