package model.Buff;

public class Weakness extends Buff {
    private PowerBuffType type;
    public Weakness(int number, PowerBuffType powerBuffType, BuffTImeType buffTImeType){
        super(number, BuffType.NEGATIVE, buffTImeType);
        this.type = powerBuffType;
    }
    public Weakness(int number, PowerBuffType powerBuffType, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.NEGATIVE, buffTImeType);
        this.type = powerBuffType;
    }
}
