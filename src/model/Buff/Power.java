package model.Buff;

public class Power extends Buff {
    private PowerBuffType type;
    public Power(int number, PowerBuffType type, BuffTImeType buffTImeType){
        super(number, BuffType.POSITIVE, buffTImeType);
        this.type = type;
    }
    public Power(int number, PowerBuffType type, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.POSITIVE, buffTImeType);
        this.type = type;
    }

    public PowerBuffType getType() {
        return type;
    }
}

