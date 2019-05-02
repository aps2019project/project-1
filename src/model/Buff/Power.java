package model.Buff;

public class Power extends Buff {
    private PowerBuffType type;
    public Power(int number, PowerBuffType type, int turns) {
        super(number, turns, BuffType.POSITIVE);
        this.type = type;
    }
}

