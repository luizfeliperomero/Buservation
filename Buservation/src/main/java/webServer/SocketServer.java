package webServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    int port = 8081;
    ServerSocket serverSocket;

    public void runServer() {
         try {
            serverSocket = new ServerSocket(port);
             System.out.println("Server Listening on Port 8081");
         }catch (IOException e) {
             e.printStackTrace();
         }

         while(true){
             try {
                 Socket clientSocket = serverSocket.accept();
                 ReserveRunnable rr = new ReserveRunnable(clientSocket);
                 new Thread(rr).start();
             } catch (IOException e) {
                    e.printStackTrace();
             }
         }
    }
}
