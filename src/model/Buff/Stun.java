package model.Buff;

public class Stun extends Buff {

    public Stun(int number, BuffTImeType buffTImeType){
        super(number, BuffType.NEGATIVE, buffTImeType);
    }
    public Stun(int number, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.NEGATIVE, buffTImeType);
    }

}
