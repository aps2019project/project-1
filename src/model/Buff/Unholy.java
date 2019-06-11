package model.Buff;

public class Unholy extends Buff {
    public Unholy(int number, BuffTImeType buffTImeType){
        super(BuffType.UNHOLY, number, buffTImeType);
    }

    public Unholy(int number, int turns, BuffTImeType buffTImeType){
        super(BuffType.UNHOLY, number, turns, buffTImeType);
    }
}
