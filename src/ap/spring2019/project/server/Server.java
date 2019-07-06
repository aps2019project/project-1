package ap.spring2019.project.server;

import ap.spring2019.project.logic.model.game.GameType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 8000;
    private static ServerSocket server;
    private static HashMap<String, Socket> onlineUsers;
    private static HashMap<GameType, String> quene;
    private static ArrayList<ap.spring2019.project.logic.model.game.Game> games;

    static {
        try {
            server = new ServerSocket(PORT);
            onlineUsers = new HashMap<>();
            quene = new HashMap<>();
            games = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        ExecutorService serverSocketAdder = Executors.newSingleThreadExecutor();
        serverSocketAdder.submit(() -> {
            while (Thread.currentThread().isAlive()) {
                try {
                    Socket socket = server.accept();
                    new Thread(new Listener(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static synchronized HashMap<String, Socket> getOnlineUsers() {
        return onlineUsers;
    }

    static synchronized ArrayList<ap.spring2019.project.logic.model.game.Game> getGames() {
        return games;
    }

    static synchronized void addUser(String userName, Socket socket) {
        onlineUsers.put(userName, socket);
    }

    static synchronized Socket deleteUser(String userName) {
        try {
            return onlineUsers.remove(userName);
        } catch (Exception e) {
            return null;
        }
    }

    static synchronized void addGame(ap.spring2019.project.logic.model.game.Game game) {
        games.add(game);
    }

    static synchronized void deleteGame(ap.spring2019.project.logic.model.game.Game game) {
        try {
            games.remove(game);
        }
        catch (Exception ignored){}
    }

    static synchronized boolean isAccountOnline(String userName) {
        return onlineUsers.containsKey(userName);
    }

}
