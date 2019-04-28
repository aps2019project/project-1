package model.Buff;

public class Weakness extends Buff {
    private Type type;
    public Weakness(int number, Type type){
        super(number);
        this.type = type;
    }
}
