package model.Buff;

public class Poison extends Buff {
    public Poison(BuffTImeType buffTImeType){
        super(BuffType.POISON, 1, buffTImeType);
    }

    public Poison(int turns, BuffTImeType buffTImeType){
        super(BuffType.POISON, 1, turns, buffTImeType);
    }

    public Poison(int value, int delay, int last, TargetType targetType){
        super(BuffType.POISON, value, delay, last, targetType);
    }
}
