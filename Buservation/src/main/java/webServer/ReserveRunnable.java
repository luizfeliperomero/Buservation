package webServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ReserveRunnable implements Runnable {
    protected Socket clientSocket;

    public ReserveRunnable (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
           BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
           String info = in.readLine();
           StringBuilder request = new StringBuilder();
           if(info.equals("reserve")) {
               System.out.println("200 OK ");
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
