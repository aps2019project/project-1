package model.variables;

import java.util.ArrayList;
import java.util.Random;

public class ID {

    private static ArrayList<ID> allIDs = new ArrayList<ID>();
    private String value;

    public ID() {
        this.value = getNewID();
        allIDs.add(this);
    }

    public String getValue() {
        return value;
    }

    public boolean isSameAs(String value) {
        return this.value.equals(value);
    }

    public boolean isSameAs(ID id) {
        return this.isSameAs(id.getValue());
    }

    public static String getNewID() {
        String randomString = generateRandomString(5);
        for (int i = 0; i < allIDs.size(); ++i) {
            if (allIDs.get(i).value.equals(randomString)) {
                i = 0;
                randomString = generateRandomString(5);
            }
        }
        return randomString;
    }

    private static String generateRandomString(int size) {
        int leftLimit = 97;
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

    @Override
    public String toString() {
        return "ID{" +
                "value='" + value + '\'' +
                '}';
    }
}
