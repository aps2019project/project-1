package model.Buff;

public class Poison extends Buff {
    public Poison(BuffTImeType buffTImeType){
        super(1, BuffType.NEGATIVE, buffTImeType);
    }
    public Poison(int turns, BuffTImeType buffTImeType){
        super(1, turns, BuffType.NEGATIVE, buffTImeType);
    }
}
