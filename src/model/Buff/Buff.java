package model.Buff;

public class Buff {
    protected int number;
    protected int turns;
    protected int delay = 0;
    protected TargetType targetType = null;
    protected BuffType buffType;
    protected BuffTImeType buffTImeType;

    public Buff(int number, BuffType buffType, BuffTImeType buffTImeType){
        this.number = number;
        this.buffType = buffType;
        this.buffTImeType = buffTImeType;
        if(buffTImeType == BuffTImeType.PERMANENT) this.turns = -1;
    }

    public Buff(int number, int turns, BuffType buffType, BuffTImeType buffTImeType){
        this(number, buffType, buffTImeType);
        this.turns = turns;
    }

    public Buff(int value, int delay, int last, TargetType targetType){
        this.number = value;
        this.delay = delay;
        this.turns = last;
        this.targetType = targetType;
    }

    public int getNumber() {
        return number;
    }

    public int getTurns() {
        return turns;
    }

    public int getDelay() {
        return delay;
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

    public void decreaseDelay() {
        this.delay --;
    }

    public TargetType getTargetType() {
        return targetType;
    }
}
