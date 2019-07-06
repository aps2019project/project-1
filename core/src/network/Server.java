package network;

import model.game.Game;
import model.other.Account;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class Server {
    static final int PORT = 5000;
    static HashMap<Account, EchoThread> clients = new HashMap<>();

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client

            new EchoThread(socket).start();
        }
    }
}

class EchoThread extends Thread {
    protected Socket socket;
    private Game game = null;
    private Account account = null;
    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    private DataInputStream input = null;
    private DataOutputStream out = null;

    public void run() {
        try {
            input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";


        while (true) {
            try {
                line = input.readUTF();
            } catch (SocketException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            switch (line) {
                case "play game orders":
                    handlePlayGame();
                    break;
                case  "login":
                    handleLogin();
                    break;
                case "get online accounts":
                    handleGetOnlineAccounts();
                    break;
                case "get account" :
                    handleGetAccount();
                    break;
                case "another order":
                    break;
            }
        }
    }
    public void handlePlayGame() {
        String line = null;
        try {
            line = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (line) {
            case "get mousePos":
                game.getAnotherAccount(account);
                break;
            case "set mousePos":
                break;
        }
    }
    public void handleGetOnlineAccounts() {
        try {
            out.writeUTF(Integer.toString(Server.clients.keySet().size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Account account : Server.clients.keySet()) {
            try {
                out.writeUTF(account.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void handleLogin(){
        String password = "";
        String outCommand = "";
        try {
            String AccountName = input.readUTF();
            account = Account.findAccount(AccountName);
            password = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(account == null) {
            outCommand = "account is null";
        }
        else if(!account.getPassword().equals(password)){
            outCommand = "wrong password";
        }
        else {
            outCommand = "correct login";
            Server.clients.put(account, this);
        }
        try {
            out.writeUTF(outCommand);
        } catch (IOException e) {
            System.out.println("cant send login answer");
        }
    }
    public void handleGetAccount(){
        try {
            String userName = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ////
    }
}