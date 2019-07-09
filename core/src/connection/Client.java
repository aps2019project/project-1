package connection;

import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import control.CsvReader;
import control.CvsWriter;
import model.other.Account;
import model.other.SavingObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static int PORT;
    private static String HOST;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private static Gson gson;
    private static Mousepos mousePos = new Mousepos(0,0);

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
    public static void setMousePos() {
        sendCommand("play game orders");
        sendCommand("get mousePos");
        mousePos = getData(Mousepos.class);
    }

    public static MouseState getMouseState() {
        if(mousePos == null) return null;
        return mousePos.getMouseState();
    }

    public static Vector2 getMousePos() {
        if(mousePos == null) return null;
        return new Vector2(mousePos.x, mousePos.y);
    }
    public static void sendMousePos(Vector2 mousePos, MouseState mouseState) {
        if(mouseState == MouseState.NOTHING) return;
        sendCommand("play game orders");
        sendCommand("send mousePos");
        Mousepos mouse = new Mousepos(mousePos.x, mousePos.y);
        mouse.setMouseState(mouseState);
        sendData(mouse);
    }
    public static int getMyNumberInGame() {
        sendCommand("get my number in game");
        return getData(Integer.class);
    }

    public void closeClient() {
        try {
            inputStream.close();
            outputStream.close();
        }
        catch(IOException i) {
            System.out.println(i);
        }
    }

    public static Account getEnemyAccount() {
        sendCommand("get enemy account");
        return getData(SavingObject.class).getAccount();
    }
    public static void applyPlayMultiPlayerGame(String gameType, int numberOfFlags) {
        sendCommand("apply play multiplayer game");
        sendCommand(gameType);
        sendCommand(Integer.toString(numberOfFlags));
       }
    public static String getApplyCondition(){
        sendCommand("get applying condition");
        try {
            String line = inputStream.readUTF();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";
    }
    public static void cancelApplying() {
        sendCommand("cancel applying");
    }
}
