package ap.spring2019.project.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class SocketAdder implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = Server.getServer().accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }


        }
    }
}
