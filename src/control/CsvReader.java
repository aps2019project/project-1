package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {

    public static ArrayList<String[]> ReadCards(String cardType){
        ArrayList<String[]> data = new ArrayList<>();
        String fileAddress = "Files/" + cardType +".csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileAddress))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

