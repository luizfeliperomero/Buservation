package webServer;

import java.io.*;
import java.net.Socket;

public class ReserveRunnable implements Runnable {
    protected Socket clientSocket;

    public ReserveRunnable (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            String message = input.readUTF();

            System.out.println("Message: "+message);
            output.writeUTF("200 MESSAGE RECEIVED");
            output.flush();

            input.close();
            output.close();
            clientSocket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
