package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {

    public static ArrayList<String[]> readCards(String cardType){
        ArrayList<String[]> data = new ArrayList<>();
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
}

