package webServer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
            /*BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String s;
            while((s = input.readLine()) != null){
                System.out.println(s);
                if(s.isEmpty()){
                    break;
                }
            }

            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write("HTTP/1.11 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
            outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
            outputStream.write("<b>Buservation</b>".getBytes(StandardCharsets.UTF_8));
            outputStream.write("\r\n\r\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();


            input.close();
            outputStream.close();
            clientSocket.close(); */
            //System.out.println("\n\nConexão recebida de " + socket.getInetAddress());


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
                System.out.println("Recurso GET: " +recurso);
                File file = new File("src\\main\\resources\\" + recurso);
                //HEADER = HEADER.replace("text/html", "text/css");
                if (file.exists()) {
                    Path path = file.toPath();
                    String mimeType = Files.probeContentType(path);
                    System.out.println("MimeType: " +mimeType);
                    String head = HEADER;
                    if (mimeType != null) {
                        head = HEADER.replaceAll("mime", mimeType);
                    }
                    System.out.println(head);
                    out.write(head.getBytes(StandardCharsets.UTF_8));
                    FileInputStream fin = new FileInputStream(file);
                    byte[] buf_arquivo = new byte[1024];
                    int read;
                    do {
                        read = fin.read(buf_arquivo);
                        if (read > 0) {
                            out.write(buf_arquivo, 0, read);
                        }
                    } while (read > 0);
                    fin.close();
                } else {
                    System.out.println("recurso " + recurso + " nao encontrado.");
                    out.write("HTTP/1.1 404 NOT FOUND\n\n".getBytes(StandardCharsets.UTF_8));
                }
            }
            in.close();
            out.close();
            clientSocket.close();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
