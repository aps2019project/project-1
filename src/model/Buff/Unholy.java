package model.Buff;

public class Unholy extends Buff {
    public Unholy(int number, BuffTImeType buffTImeType){
        super(number, BuffType.NEGATIVE, buffTImeType);
    }
    public Unholy(int number, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.NEGATIVE, buffTImeType);
    }
}
