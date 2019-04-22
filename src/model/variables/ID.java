package model.variables;

import java.util.ArrayList;
import java.util.Random;

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
        return generateRandomString(5);
    }

    private static String generateRandomString(int size) {
        int leftLimit = 33;
        int rightLimit = 126;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
