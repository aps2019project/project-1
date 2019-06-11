package model.Buff;

public class Weakness extends Buff {
    private PowerBuffType type;
    public Weakness(int number, PowerBuffType powerBuffType, BuffTImeType buffTImeType){
        super(BuffType.WEAKNESS, number, buffTImeType);
        this.type = powerBuffType;
    }
    public Weakness(int number, PowerBuffType powerBuffType, int turns, BuffTImeType buffTImeType){
        super(BuffType.WEAKNESS, number, turns, buffTImeType);
        this.type = powerBuffType;
    }

    public Weakness(int value, PowerBuffType type, int delay, int last, TargetType targetType){
        super(BuffType.WEAKNESS, value, delay, last, targetType);
        this.type = type;
    }

    public PowerBuffType getType() {
        return type;
    }
}
