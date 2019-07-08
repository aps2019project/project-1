package control;

import java.io.*;
import java.util.ArrayList;

public class CsvReader {

    public static ArrayList<String[]> readCards(String cardType){
        ArrayList<String[]> data = new ArrayList<String[]>();
        String fileAddress = "Files/" + cardType +".csv";
        try {
            FileReader fileReader = new FileReader(fileAddress);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
            fileReader.close();
            reader.close();
        } catch (IOException io){
            io.printStackTrace();
        }
        return data;
    }

    public static String readFile(String cardType) {
        try {
            InputStream is = new FileInputStream("Files/" + cardType +".csv");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            return sb.toString();
        } catch (IOException i){
            i.printStackTrace();
        }
        return null;
    }
}

