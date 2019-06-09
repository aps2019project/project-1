package model.Buff;

import java.util.ArrayList;

public class Bleeding extends Buff {
    private ArrayList<Integer> bleed = new ArrayList<>();
    public Bleeding(ArrayList<Integer> array){
        super(BuffType.BLEEDING, 1, array.size(),  BuffTImeType.NORMAL);
        bleed.addAll(array);
    }

    public int getFirst() {
        int n = bleed.get(0);
        bleed.remove(0);
        return n;
    }
}
