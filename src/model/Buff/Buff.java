package model.Buff;

public class Buff {
    protected int number;
    protected int turns;
    protected int delay = 0;
    protected BuffType buffType;
    protected TargetType targetType = null;
    protected BuffEffectType buffEffectType;
    protected BuffTImeType buffTImeType;

    public Buff(BuffType buffType, int number, BuffEffectType buffEffectType, BuffTImeType buffTImeType){
        this.buffType = buffType;
        this.number = number;
        this.buffEffectType = buffEffectType;
        this.buffTImeType = buffTImeType;
        if(buffTImeType == BuffTImeType.PERMANENT) this.turns = -1;
    }

    public Buff(BuffType buffType,int number, int turns, BuffEffectType buffEffectType, BuffTImeType buffTImeType){
        this(buffType, number, buffEffectType, buffTImeType);
        this.turns = turns;
    }

    public Buff(BuffType buffType,int value, int delay, int last, TargetType targetType){
        this.buffType = buffType;
        this.number = value;
        this.delay = delay;
        this.turns = last;
        this.targetType = targetType;
    }

    public BuffType getBuffType() {
        return buffType;
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

    public BuffEffectType getBuffEffectType() {
        return buffEffectType;
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
