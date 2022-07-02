import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientBuservation {
    public static void main(String[] args) {
        String serverIP = "192.168.1.108";
        int port = 8081;
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        InputStreamReader isr;

        try {

            socket = new Socket(serverIP, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

            out.println("reserve");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
