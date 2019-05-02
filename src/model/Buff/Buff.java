package model.Buff;

public class Buff {
    protected int number;
    protected int turns;
    protected BuffType buffType;
    protected BuffTImeType buffTImeType;

    public Buff(int number, BuffType buffType, BuffTImeType buffTImeType){
        this.number = number;
        this.buffType = buffType;
        this.buffTImeType = buffTImeType;
    }

    public Buff(int number, int turns, BuffType buffType, BuffTImeType buffTImeType){
        this(number, buffType, buffTImeType);
        this.turns = turns;
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

    public BuffTImeType getBuffTImeType() {
        return buffTImeType;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void decreaseTurns (){
        this.turns --;
    }
}
