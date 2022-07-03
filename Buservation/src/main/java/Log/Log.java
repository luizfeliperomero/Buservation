package Log;

import model.Seat;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import static webServer.Server.avaiableSeats;
import static webServer.Server.seats;

public class Log {
    Socket socket;


    public Log(Socket socket) {
        this.socket = socket;
    }


    public synchronized void bookTickets(Seat seat) {
        String msg = "";
        File file = new File("log.txt");
        String socketIp = socket.getInetAddress().toString();
        if(avaiableSeats.size() == 0) {
            msg = socketIp +" Tried to book a ticket but there was none left";
        }
        else {
            if(!avaiableSeats.contains(seat)){
                msg = "BOOKING ----- " + socketIp + " Tried to book seat " + seat.getId() + " but it wasn't available";
            }
            else {
                Iterator<Seat> itr = avaiableSeats.iterator();
                while (itr.hasNext()){
                    Seat s = itr.next();
                    if(s.getId() == seat.getId()){
                        msg = "BOOKING ----- " + "Seat " + s.getId() + " Booked by " + socketIp;
                        s.setEmpty(false);
                        itr.remove();
                    }
                }
            }
            System.out.println("Avaiable Seats After Book: " +avaiableSeats.size());
        }
        writeLog(msg, file);
    }

    public void writeLog(String msg, File file) {
        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(msg);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
