package model.Buff;

public class Weakness extends Buff {
    private PowerBuffType type;
    public Weakness(int number, PowerBuffType type, int turns){
        super(number, turns, BuffType.NEGATIVE);
        this.type = type;
    }
}
