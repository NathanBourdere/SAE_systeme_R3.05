import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Serveur {
    public static void main(String[] args) {
        try {
            List<String> usernames = new ArrayList<>();
            List<Socket> sockets = new ArrayList<>();
            ServerSocket serverSock = new ServerSocket(4444);
            while (true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                InputStreamReader stream = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(stream);
                String message = reader.readLine();
                if (usernames.contains(message)) {
                    writer.println("Nom déjà existant");
                    writer.flush();
                    clientSocket.close();
                } else {
                    sockets.add(clientSocket);
                    usernames.add(message);
                    Thread t = new Thread(new ClientHandler(clientSocket, sockets, message));
                    t.start();
                    writer.println(message);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}