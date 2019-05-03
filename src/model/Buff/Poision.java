package model.Buff;

public class Poision extends Buff {
    public Poision(BuffTImeType buffTImeType){
        super(1, BuffType.NEGATIVE, buffTImeType);
    }
    public Poision(int turns, BuffTImeType buffTImeType){
        super(1, turns, BuffType.NEGATIVE, buffTImeType);
    }
}
