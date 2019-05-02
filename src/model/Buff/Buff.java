package model.Buff;

public class Buff {
    protected int number;
    protected int turns;
    public Buff(int number, int turns){
        this.number = number;
        this.turns = turns;
    }

    public int getNumber() {
        return number;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void decreaseTurns (){
        this.turns --;
    }
}
