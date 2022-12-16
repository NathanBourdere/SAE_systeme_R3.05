import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable{ //fait la lecture 

    private Socket client;
    private List<Socket> clientsConnectees;

    public ClientHandler(Socket socket,List<Socket> lesClients){
        this.client = socket;
        this.clientsConnectees = lesClients;
    }

    public void addClient(Socket client){
        if (client != null){
            this.clientsConnectees.add(client);
        }
    }
    public void sendMessage(String username,String message){
        try{
            if(!(message.equals(""))){
                String leMessage = username+" : "+message;
                for (Socket client :clientsConnectees){
                    PrintWriter writer = new PrintWriter(client.getOutputStream());
                    writer.println(leMessage);
                    writer.flush();
                }

            }
        }
        catch(IOException err){
            err.printStackTrace();
        }
    }
    public void run(){
        try{
        InputStreamReader stream = new InputStreamReader(this.client.getInputStream());
        BufferedReader reader = new BufferedReader(stream);
        while(true){
            System.out.println(this.client);
                String message = reader.readLine();
                OutputStream os = client.getOutputStream(); 
                PrintWriter writer = new PrintWriter(os);
                writer.println("feur");
                writer.flush();
        }
    }
             catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
