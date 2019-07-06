package network;

import com.badlogic.gdx.math.Vector2;
import model.other.Account;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client
{
    // initialize socket and input output streams
    private static Socket socket		 = null;
    private static DataInputStream input = null;
    private static DataOutputStream out	 = null;

    private static Vector2 mousePos = new Vector2();
    private static MouseState mouseState = MouseState.NOTHING;
    private static Account account = null;
    private static ArrayList<String> onlineAccounts = new ArrayList<>();
    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(socket.getInputStream());
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // close the connection

    }
    public static void setMousePos() {

    }

    public static MouseState getMouseState() {
        return mouseState;
    }

    public static Vector2 getMousePos() {
        return mousePos;
    }

    public void closeClient() {
        try {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
    public static String logIn(String account, String password) {
        try {
            out.writeUTF("login");
            out.writeUTF(account);
            out.writeUTF(password);
            String line = input.readUTF();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return "cant connect";
        }
    }
    public static void setOnlineAccounts() {
        try {
            out.writeUTF("get online accounts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int numberOfOnlineAccounts = 0;
        try {
            numberOfOnlineAccounts = Integer.parseInt(input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < numberOfOnlineAccounts ; i++) {
            try {
                onlineAccounts.add(input.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Account getAccount(String userName) {
        try {
            out.writeUTF("get account");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ////////
        return Account.findAccount(userName);
    }
    public static void applyPlayMultiPlayerGame(String username, String gameType, int numberOfFlags) {
        try {
            out.writeUTF("apply play multiplayer game");
            out.writeUTF(username);
            out.writeUTF(gameType);
            out.writeUTF(Integer.toString(numberOfFlags));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getApplyCondition(String username){
        try {
            out.writeUTF("get applying condition");
            out.writeUTF(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";
    }
    public static void cancelApplying(String username) {
        try {
            out.writeUTF("cancel applying");
            out.writeUTF(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void acceptApplying(String username) {
        try {
            out.writeUTF("accept applying");
            out.writeUTF(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getOnlineAccounts() {
        return onlineAccounts;
    }
}
