package model.Buff;

public class Power extends Buff {
    private PowerBuffType type;
    public Power(int number, PowerBuffType type, BuffTImeType buffTImeType){
        super(number, BuffType.NEGATIVE, buffTImeType);
        this.type = type;
    }
    public Power(int number, PowerBuffType type, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.NEGATIVE, buffTImeType);
        this.type = type;
    }
}

