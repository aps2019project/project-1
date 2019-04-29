package model.Buff;

public class Weakness extends Buff {
    private Type type;
    public Weakness(int number, Type type, int turns){
        super(number, turns);
        this.type = type;
    }
}
