package webServer;

import Log.Log;
import model.Response;
import model.Seat;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;

import static webServer.Server.seats;

public class ReserveRunnable implements Runnable {
    protected Socket clientSocket;

    private static String HEADER = "HTTP/1.1 200 OK\n" +
            "Content-Type: mime; charset=UTF-8\n\n";

    public ReserveRunnable (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            byte[] buffer = new byte[1024];
            int size = in.read(buffer);
            String req = new String(buffer, 0, size);
            String[] linhas = req.split("\n");
            System.out.println("\n" + linhas[0]);
            String[] linha0 = linhas[0].split(" ");
            String metodo = linha0[0];
            System.out.println("Método: " +metodo);
            String recurso = linha0[1];
            System.out.println("\nRecurso: " +recurso);
            if (metodo.equals("GET")) {
                recurso = recurso.substring(1);
                String[] recursoSplited = recurso.split("\\?");
                recurso = recursoSplited[0];
                if(recurso.isEmpty()){
                    recurso = "index.html";
                }
                else if(recurso.equals("reserve")) {
                    String[] rec1 = recursoSplited[1].split("&");

                    int recursoId = Integer.parseInt(rec1[0].substring(3));
                    String recursoName = rec1[1].substring(5);
                    System.out.println("Recurso name: " +recursoName);
                    System.out.println("Recurso Posicao 2: " + recursoId);
                    recurso = "index.html";
                    Log log = new Log(clientSocket);
                    for (Seat s: seats) {
                        if(s.getId() == recursoId){
                            Response response = log.bookTickets(s, recursoName);
                            if(response.equals(Response.EMPTY)){
                                recurso = "empty.html";
                            } else if (response.equals(Response.ERROR)) {
                                recurso = "error.html";
                            }
                            else {
                                recurso = "success.html";
                            }
                        }
                    }
                }
                System.out.println("Recurso GET: " +recurso);

                showPage(recurso, out);
            }
            in.close();
            out.close();
            clientSocket.close();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showPage(String recurso, OutputStream out) {

        File file = new File("C:\\Users\\luizf\\Desktop\\test\\oSystem\\Buservation\\Buservation\\src\\main\\resources\\" + recurso);

        if (file.exists()) {
            Path path = file.toPath();
            String mimeType = null;
            try {
                mimeType = Files.probeContentType(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("MimeType: " +mimeType);
            String head = HEADER;
            if (mimeType != null) {
                head = HEADER.replaceAll("mime", mimeType);
            }
            System.out.println(head);
            try {
                out.write(head.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] buf_arquivo;
            try {
                buf_arquivo = Files.readAllBytes(file.toPath());
                String html = new String(buf_arquivo);
                String elements = "";
                for (Seat seat: seats) {
                    String element = "<div";
                    if(seat.isEmpty()) {
                        element += " class=\"emptySeat\"";
                        element += ">" + "<p>" + seat.getId() + "</p>"+ "</div>";
                    }
                    else {
                        element += " class=\"notEmpty\"";
                        element += ">" + "<p>" + seat.getId() + "</p>"+ "<p class=\"name\">" + seat.getOwner() + "</p>" + "<p class=\"date\">"+ seat.getTimestamp()+ "</p>" + "</div>";
                    }
                    elements += element + "\n";
                }
                html = html.replace("<seats />", elements);
                buf_arquivo = html.getBytes(StandardCharsets.UTF_8);
                out.write(buf_arquivo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            /*int read;
            do {
                try {
                    read = fin.read(buf_arquivo);
                    System.out.println("Read Before: " +read);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (read > 0) {
                    try {
                        System.out.println("Read After: " +read);
                        out.write(buf_arquivo, 0, read);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } while (read > 0);
            try {
                fin.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
        } else {
            System.out.println("recurso " + recurso + " nao encontrado.");
            try {
                recurso = "404.html";
                showPage(recurso, out);
                out.write("HTTP/1.1 404 NOT FOUND\n\n".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
