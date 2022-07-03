package Log;

import model.Seat;


import java.net.Socket;

import static webServer.Server.avaiableSeats;
import static webServer.Server.seats;

public class Log {
    Socket socket;


    public Log(Socket socket) {
        this.socket = socket;
    }


    public synchronized void bookTickets(int seatId) {
        String socketIp = socket.getInetAddress().toString();
        if(avaiableSeats.size() == 0) {
            System.out.println(socketIp +"Tried to book a ticket but there was none left");
        }
        else {
            for (Seat s : avaiableSeats) {
                if (s.getId() == seatId) {
                    s.setEmpty(false);
                    System.out.println("Seat " + s.getId() + " Booked by " + socketIp);
                }
                System.out.println("Avaiable Seats After Book: " +avaiableSeats.size());
            }
        }
    }

}
