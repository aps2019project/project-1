package model.Buff;

import static model.Buff.BuffEffectType.*;
import static model.Buff.BuffType.*;

public class Buff {
    protected int number;
    protected int turns;
    protected int delay = 0;
    protected BuffType buffType;
    protected TargetType targetType;
    protected BuffEffectType buffEffectType;
    protected BuffTImeType buffTImeType;
    protected PowerBuffType powerBuffType;

    public Buff(BuffType buffType, int number, BuffTImeType buffTImeType){
        this.buffType = buffType;
        this.setBuffEffectType(buffType);
        this.number = number;
        this.buffTImeType = buffTImeType;
        if(buffTImeType == BuffTImeType.PERMANENT) this.turns = -1;
    }

    public Buff(BuffType buffType, int number, int turns, BuffTImeType buffTImeType){
        this(buffType, number, buffTImeType);
        this.turns = turns;
    }

    public Buff(BuffType buffType, int value, int delay, int last, TargetType targetType){
        this.buffType = buffType;
        this.setBuffEffectType(buffType);
        this.number = value;
        this.delay = delay;
        this.turns = last;
        this.targetType = targetType;
    }

    public void setBuffEffectType(BuffType buffType) {
        if(buffType == POWER || buffType == HOLY)
            this.buffEffectType = POSITIVE;
        else
            this.buffEffectType = NEGATIVE;
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
        if(this.delay > 0) return;
        this.turns --;
    }

    public void decreaseDelay() {
        this.delay --;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public PowerBuffType getPowerBuffType() {
        return powerBuffType;
    }

    public void setPowerBuffType(PowerBuffType powerBuffType) {
        this.powerBuffType = powerBuffType;
    }
}
