package connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import control.CsvReader;
import control.CvsWriter;
import graphic.Others.CardTexture;
import model.cards.CardType;
import model.other.Account;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private static Gson gson;

    static {
        gson = new GsonBuilder().create();
    }

    public static void connect() {
        try {
            Socket socket = new Socket("localhost", 8000);
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

    public static  <T> T getData(Type type) {
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

    public static void sendCardString(String data){
        sendCommand(data);
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

}
