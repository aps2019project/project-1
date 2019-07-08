package connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import control.CsvReader;
import control.CvsWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static int PORT;
    private static String HOST;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private static Gson gson;

    static {
        gson = new GsonBuilder().create();
    }

    public static void connect() {
        getConfigData();
        try {
            Socket socket = new Socket(HOST, PORT);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  <T> void sendData(T data) {
        try {
            outputStream.writeUTF(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  <T> T getData(Type type, Class<T> tClass) {
        try {
            return gson.fromJson(inputStream.readUTF(), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  <T> T getData(Class<T> tClass) {
        try {
            String data = inputStream.readUTF();
            return gson.fromJson(data, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendCommand(String str) {
        try {
            outputStream.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCommand() {
        try {
            return gson.fromJson(inputStream.readUTF(), String.class);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void sendCardFile(String cardType){
        sendCommand(CsvReader.readFile(cardType));
    }

    public static void getCardFiles() {
        sendCommand("Send Card File Heroes");
        CvsWriter.writeCardFiles("Heroes", getCommand());

        sendCommand("Send Card File Minions");
        CvsWriter.writeCardFiles("Minions", getCommand());

        sendCommand("Send Card File Spells");
        CvsWriter.writeCardFiles("Spells", getCommand());

        sendCommand("Send Card File Items");
        CvsWriter.writeCardFiles("Items", getCommand());
    }

    private static void getConfigData() {
        try {
            Scanner scanner = new Scanner(new File("Files/Data/.config"));
            HOST = scanner.nextLine();
            PORT = scanner.nextInt();
        } catch (FileNotFoundException e) {
            HOST = "localhost";
            PORT = 8765;
            e.printStackTrace();
        }
    }

}
