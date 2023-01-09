import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClientHandler implements Runnable{ //fait la lecture 

    private Socket client;
    private List<Socket> clientsConnectees;
    private String username_client;

    public ClientHandler(Socket socket,List<Socket> lesClients,String username){
        this.client = socket;
        this.clientsConnectees = lesClients;
        this.username_client = username;
    }

    public void addClient(Socket client){
        if (client != null){
            this.clientsConnectees.add(client);
        }
    }
    public void sendMessage(String username,String message){
        try{
            if(!(message.equals(""))){
                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter formatteddate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String dateFormatee = date.format(formatteddate);
                String leMessage = "[" + dateFormatee + "] " + username+" : "+message;
                for (Socket clients :clientsConnectees){
                    if (clients!=client){
                        PrintWriter writer = new PrintWriter(clients.getOutputStream());
                        writer.println(leMessage);
                        writer.flush();
                    }
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
            //System.out.println(this.client);
                String message = reader.readLine();
                OutputStream os = client.getOutputStream(); 
                this.sendMessage(this.username_client, message);
                
        }
    }
             catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
