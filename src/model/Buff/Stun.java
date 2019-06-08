package model.Buff;

public class Stun extends Buff {

    public Stun(int number, BuffTImeType buffTImeType){
        super(BuffType.STUN, number, BuffEffectType.NEGATIVE, buffTImeType);
    }

    public Stun(int number, int turns, BuffTImeType buffTImeType){
        super(BuffType.STUN, number, turns, BuffEffectType.NEGATIVE, buffTImeType);
    }

    public Stun(int value, int delay, int last, TargetType targetType){
        super(BuffType.STUN, value, delay, last, targetType);
    }

}
