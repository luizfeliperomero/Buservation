package Log;

import model.Response;
import model.Seat;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Iterator;

import static webServer.Server.avaiableSeats;
import static webServer.Server.seats;

public class Log {
    Socket socket;


    public Log(Socket socket) {
        this.socket = socket;
    }


    public synchronized Response bookTickets(Seat seat, String name) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String msg = "";
        File file = new File(".." + File.separator + ".." + File.separator + ".." + File.separator + "logs" + File.separator + "log.txt");
        System.out.println("Log Path: " +file.getAbsolutePath());
        Response response = Response.OK;

        String socketIp = socket.getInetAddress().toString();
        if(avaiableSeats.size() == 0) {
            msg = "BOOKING ----- "+socketIp +" Tried to book a ticket but there was none left ----- " +timestamp;
            response = Response.EMPTY;
        }
        else {
            if(!avaiableSeats.contains(seat)){
                msg = "BOOKING ----- " + socketIp + " Tried to book seat " + seat.getId() + " but it wasn't available -----" +timestamp;
                response = Response.ERROR;
            }
            else {
                Iterator<Seat> itr = avaiableSeats.iterator();
                while (itr.hasNext()){
                    Seat s = itr.next();
                    if(s.getId() == seat.getId()){
                        s.setOwner(name);
                        s.setTimestamp(timestamp);
                        msg = "BOOKING ----- " + "Seat " + s.getId() + " Booked by " + socketIp + " ----- " +timestamp;
                        s.setEmpty(false);
                        itr.remove();
                    }
                }
            }
            System.out.println("Avaiable Seats After Book: " +avaiableSeats.size());
        }
        writeLog(msg, file);
        return response;
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
