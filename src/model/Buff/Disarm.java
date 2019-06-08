package model.Buff;

public class Disarm extends Buff {
    public Disarm(int number, BuffTImeType buffTImeType){
        super(BuffType.DISARM, number, BuffEffectType.NEGATIVE, buffTImeType);
    }
    public Disarm(int number, int turns, BuffTImeType buffTImeType){
        super(BuffType.DISARM, number, turns, BuffEffectType.NEGATIVE, buffTImeType);
    }
    public Disarm(int value, int delay, int last, TargetType targetType){
        super(BuffType.DISARM, value, delay, last, targetType);
    }
}
