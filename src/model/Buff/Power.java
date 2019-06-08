package model.Buff;

public class Power extends Buff {
    private PowerBuffType type;
    public Power(int number, PowerBuffType type, BuffTImeType buffTImeType){
        super(BuffType.POWER, number, BuffEffectType.POSITIVE, buffTImeType);
        this.type = type;
    }
    public Power(int number, PowerBuffType type, int turns, BuffTImeType buffTImeType){
        super(BuffType.POWER, number, turns, BuffEffectType.POSITIVE, buffTImeType);
        this.type = type;
    }

    public Power(int value, PowerBuffType type, int delay, int last, TargetType targetType){
        super(BuffType.POWER, value, delay, last, targetType);
        this.type = type;
    }

    public PowerBuffType getType() {
        return type;
    }
}

