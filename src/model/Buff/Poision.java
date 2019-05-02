package model.Buff;

public class Poision extends Buff {
    public Poision(int number, BuffTImeType buffTImeType){
        super(number, BuffType.NEGATIVE, buffTImeType);
    }
    public Poision(int number, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.NEGATIVE, buffTImeType);
    }
}
