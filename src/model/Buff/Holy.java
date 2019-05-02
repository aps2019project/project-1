package model.Buff;

public class Holy extends Buff {
    public Holy(int number, BuffTImeType buffTImeType){
        super(number, BuffType.POSITIVE, buffTImeType);
    }
    public Holy(int number, int turns, BuffTImeType buffTImeType){
        super(number, turns, BuffType.POSITIVE, buffTImeType);
    }
}
