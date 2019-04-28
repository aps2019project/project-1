package model.Buff;

public class Weakness extends Buff {
    Type type;
    public Weakness(int number, Type type){
        super(number);
        this.type = type;
    }
}
