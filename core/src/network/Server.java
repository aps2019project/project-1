package network;

import model.game.Game;
import model.game.GameType;
import model.other.Account;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    static final int PORT = 5000;
    static HashMap<String, EchoThread> clients = new HashMap<>();
    static ArrayList<ContentsBetweenTwoPlayers> contentsBetweenClients = new ArrayList<>();
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
    public static ContentsBetweenTwoPlayers findOrCreateContent(String firstUsername, String secondUsername){
        if(clients.get(firstUsername) == null || clients.get(secondUsername) == null) return null;
        for(ContentsBetweenTwoPlayers content : contentsBetweenClients) {
            if(content.check(firstUsername, secondUsername)){
                return content;
            }
        }
        ContentsBetweenTwoPlayers content = new ContentsBetweenTwoPlayers(firstUsername, secondUsername);
        contentsBetweenClients.add(content);
        return content;
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
                Server.clients.remove(account);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Server.clients.remove(account);
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
                case "apply play multiplayer game":
                    handleApplyPlayMultiplayerGame();
                    break;
                case "accept applying":
                    handleAcceptApplying();
                    break;
                case "cancel applying" :
                    handleCancelApply();
                    break;
                case  "get applying condition" :
                    handleGetApplyingCondition();
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
        for(String username : Server.clients.keySet()) {
            try {
                out.writeUTF(username);
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
            Server.clients.put(account.getUsername(), this);
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
    public void handleApplyPlayMultiplayerGame() {
        String username = "";
        GameType type = GameType.KILL_HERO;
        int numberOfFlags = 0;
        try {
            username = input.readUTF();
            type = Game.getGameTypeByString(input.readUTF());
            numberOfFlags = Integer.parseInt(input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentsBetweenTwoPlayers content = Server.findOrCreateContent(account.getUsername(), username);
        if(content != null)
            content.applyCondition(account.getUsername(), type, numberOfFlags);
    }


    public void handleAcceptApplying() {
        String username = "";
        try {
            username = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentsBetweenTwoPlayers content = Server.findOrCreateContent(account.getUsername(), username);
        content.acceptCondition();;
    }
    public void handleCancelApply() {
        String username = "";
        try {
            username = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentsBetweenTwoPlayers content = Server.findOrCreateContent(account.getUsername(), username);
        content.cancelApplying();;
    }
    public void handleGetApplyingCondition() {
        String username = "";
        try {
            username = input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentsBetweenTwoPlayers content = Server.findOrCreateContent(account.getUsername(), username);
        try {
            out.writeUTF(content.getApplyingCondition(account.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}