package ap.spring2019.project.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Listener implements Runnable{

    private Gson gson;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    Listener(Socket socket) {
        try {
            gson = new GsonBuilder().create();
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream =new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String command = getCommand();
            switch (command) {

            }
        }
    }

    private String getCommand() {
        try {
            return inputStream.readUTF();
        }
        catch (IOException ignored) {
            return "";
        }
    }

    private <T> void sendData(T data) {
        try {
            outputStream.writeUTF(gson.toJson(data));
        }
        catch (IOException ignored) {}
    }

}
