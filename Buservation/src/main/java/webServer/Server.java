package webServer;

import model.Seat;

import java.util.ArrayList;

public class Server {
    public static ArrayList<Seat> seats = new ArrayList<>();
    public static ArrayList<Seat> avaiableSeats = new ArrayList<>();
    public static void main(String[] args) {
        for(int i = 1; i < 17; i++){
            seats.add(new Seat(i, true));
        }
        for (Seat s: seats) {
            if(s.isEmpty() == true){
                avaiableSeats.add(s);
            }
        }
        System.out.println("Avaiable Seats: " +avaiableSeats.size());
        SocketServer server = new SocketServer();
        server.runServer();
    }
}
