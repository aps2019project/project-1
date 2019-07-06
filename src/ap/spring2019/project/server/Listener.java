package ap.spring2019.project.server;

import ap.spring2019.project.logic.model.other.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Listener implements Runnable {

    private Gson gson;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    Listener(Socket socket) {
        try {
            this.socket = socket;
            this.gson = new GsonBuilder().create();
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            String command = getCommand();
            if (command.matches("login \\w+ \\w+")) {
                loginUser(command.split(" ")[1], command.split(" ")[2]);
            } else if (command.matches("create account \\w+ \\w+")) {
                createAccount(command.split(" ")[2], command.split(" ")[3]);
            }
        }
    }

    private String getCommand() {
        try {
            return inputStream.readUTF();
        } catch (IOException ignored) {
            return "";
        }
    }

    private <T> void sendData(T data) {
        try {
            outputStream.writeUTF(gson.toJson(data));
        } catch (IOException ignored) {
        }
    }

    private void createAccount(String userName, String password) {
        if (!Account.isUserNameAvailable(userName)) {
            sendData("Error: userName is invalid");
        }
        synchronized (Account.getAccounts()) {
            Account account = new Account(userName, password);
            Server.addUser(account.getUsername(), socket);
        }
    }

    private void loginUser(String userName, String password) {
        if (!Account.checkIfPasswordIsCorrect(userName, password)) {
            sendData("Error: Account doesn't exists");
            return;
        }
        if (Server.isAccountOnline(userName)) {
            sendData("Error: Account is online");
            return;
        }
        Server.addUser(userName, socket);
    }
}