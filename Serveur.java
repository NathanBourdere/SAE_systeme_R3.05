import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Serveur {

    public static boolean verifierCaracs(String mes){
        if (mes.equals("")){
            return false;
        }
        System.out.println(mes);
        String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890&é{([-|è^çà@)]=}ù$³/:,?;.!§";
        List<String> caracs = new ArrayList<>();
        for (int i=0;i<caracteres.length();i++){
            caracs.add(String.valueOf(caracteres.charAt(i)));
        }
        for (char lettre:mes.toCharArray()){
            String let = String.valueOf(lettre);
            if (!(caracs.contains(let))){
                System.out.println(let);
                return false;
            }
        }
        return true;
    }
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
                }
                else if (!(verifierCaracs(message))){
                    sockets.add(clientSocket);
                    InetSocketAddress socketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                    String clientIpAddress = socketAddress.getAddress().getHostAddress();
                    usernames.add(clientIpAddress);
                    System.out.println(usernames);
                    Thread t = new Thread(new ClientHandler(clientSocket, sockets, message));
                    t.start();
                    writer.println("Votre IP est : "+usernames.get(usernames.size()-1));
                    writer.flush();
                }
                else {
                    sockets.add(clientSocket);
                    usernames.add(message);
                    Thread t = new Thread(new ClientHandler(clientSocket, sockets, message));
                    t.start();
                    System.out.println(usernames);
                    writer.println("Votre nom est donc : "+message);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}