package model.Buff;

public class Unholy extends Buff {
    public Unholy(int number, BuffTImeType buffTImeType){
        super(BuffType.UNHOLY, number, BuffEffectType.NEGATIVE, buffTImeType);
    }

    public Unholy(int number, int turns, BuffTImeType buffTImeType){
        super(BuffType.UNHOLY, number, turns, BuffEffectType.NEGATIVE, buffTImeType);
    }
}
