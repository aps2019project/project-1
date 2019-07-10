package connection;

import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import control.CsvReader;
import control.CvsWriter;
import graphic.Others.PopUp;
import graphic.screen.GetIpScreen;
import graphic.screen.LoadingScreen;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;
import model.other.Account;
import model.other.SavingObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Client {

    private static int PORT;
    private static String HOST;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private static Gson gson;
    private static Mousepos mousePos = new Mousepos(0,0);
    private static float[] myArray;
    private static float[] enemyArray;
    static {
        gson = new GsonBuilder().create();
    }

    public static void connect(String host) {
        try {
            Socket socket = new Socket(host, 8765);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            ScreenManager.setScreen(new LoadingScreen());
        } catch (UnknownHostException e) {
            PopUp.getInstance().setText("Can't Connect to Host IP...");
            ScreenManager.setScreen(new GetIpScreen());
            e.printStackTrace();
        } catch (IOException e) {
            PopUp.getInstance().setText("Cant get socket I/O streams...");
            ScreenManager.setScreen(new GetIpScreen());
            e.printStackTrace();
        }
    }

    public static <T> void sendData(T data) {
        try {
            outputStream.writeUTF(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getData(Type type) {
        try {
            return gson.fromJson(inputStream.readUTF(), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getData(Class<T> tClass) {
        try {
            String data = inputStream.readUTF();
            return gson.fromJson(data, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> ArrayList<T> getArrayList(Class<T> c) {
        ArrayList<T> result = new ArrayList<T>();
        String data;
        try {
            data = inputStream.readUTF();
            while (!data.matches("\"end\"")) {
                result.add(gson.fromJson(data, c));
                data = inputStream.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> HashSet<T> getHashSet(Class<T> c) {
        return new HashSet<T>(getArrayList(c));
    }

    public static <K, V> HashMap<K, V> getHashMap(Class<K> k, Class<V> v) {
        HashMap<K, V> result = new HashMap<K, V>();
        try {
            String data = inputStream.readUTF();
            while (!data.matches("\"end\"")) {
                K key = gson.fromJson(data, k);
                V value = getData(v);
                result.put(key, value);
                data = inputStream.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
            String data = inputStream.readUTF();
            return gson.fromJson(data, String.class);
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
        } catch (Exception e) {
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
    public static void setArrays() {
        sendCommand("get arrays");
        myArray = getData(float[].class);
        enemyArray = getData(float[].class);
    }

    public static float[] getMyArray() {
        return myArray;
    }

    public static float[] getEnemyArray() {
        return enemyArray;
    }
}
