package model.Buff;

public class Disarm extends Buff {
    public Disarm(int number, BuffTImeType buffTImeType){
        super(number, BuffType.NEGATIVE, buffTImeType);
    }
    public Disarm(int number, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.NEGATIVE, buffTImeType);
    }
}
