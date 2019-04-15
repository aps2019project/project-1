package model.variables;

import java.util.ArrayList;

public class ID {
    private String value;
    private static ArrayList<ID> allIDs = new ArrayList<>();
    private static int lastID = 1000;

    public ID() {
        this.value = getNewID();
        allIDs.add(this);
    }

    public String getValue() {
        return value;
    }

    public boolean isSameAs(String value) {
        return this.value.compareTo(value) == 0;
    }
    public boolean isSameAs(ID id) {
        return this.isSameAs(id.getValue());
    }

    public static String getNewID() {
        lastID++;
        return Integer.toString(lastID);
    }
}
