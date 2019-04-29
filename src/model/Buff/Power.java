package model.Buff;

public class Power extends Buff {
    private Type type;
    public Power(int number, Type type, int turns) {
        super(number, turns);
        this.type = type;
    }
}

