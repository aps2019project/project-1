package model.Buff;

public class Holy extends Buff {
    public Holy(int number, BuffTImeType buffTImeType){
        super(BuffType.HOLY, number, buffTImeType);
    }

    public Holy(int number, int turns, BuffTImeType buffTImeType){
        super(BuffType.HOLY, number, turns, buffTImeType);
    }

    public Holy(int value, int delay, int last, TargetType targetType){
        super(BuffType.HOLY, value, delay, last, targetType);
    }
}
