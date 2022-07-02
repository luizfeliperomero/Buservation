import java.io.*;
import java.net.Socket;

public class ClientBuservation {
    public static void main(String[] args) {
        String serverIP = "localhost";
        int port = 8081;
        Socket socket;

        try {

            socket = new Socket(serverIP, port);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            String message = "RESERVE SEAT";
            output.writeUTF(message);
            output.flush();

            message = input.readUTF();
            System.out.println("RESPONSE: "+message);

            input.close();
            output.close();
            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
