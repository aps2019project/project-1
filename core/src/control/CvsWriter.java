package control;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CvsWriter {

    public static void write(String cardType, ArrayList<String> data) {
        String fileAddress = "Files/" + cardType +".csv";
        try {
            FileWriter fileWriter = new FileWriter(fileAddress, true);
            fileWriter.append(join(',', data));
            fileWriter.append("\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public static String join(char c, ArrayList<String> data) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String string : data){
            stringBuilder.append(string);
            stringBuilder.append(c);
        }
        stringBuilder.deleteCharAt(stringBuilder.length());
        return stringBuilder.toString();
    }
}
