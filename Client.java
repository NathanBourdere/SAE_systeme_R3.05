import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Client{
    private Scanner scanner;
    private Socket socket;
    private String username;
    private BufferedReader lecteur;
    private PrintStream ecriture;
    private ClientLecture clientlirek;
    public Client(){
        this.scanner = new Scanner(System.in);
    }

    public void connectTo(String ip,Integer port,String username){
        try{
        this.socket = new Socket(ip,port);
        this.username = username;
        this.lecteur = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.ecriture = new PrintStream(this.socket.getOutputStream());
        this.ecriture.println(username);
        this.ecriture.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void lire() throws IOException{
        String message = lecteur.readLine();
        try{
            if(message.equals("null")){
                this.socket.close();
                System.out.println("Vous avez été deconnecté");
                System.exit(0);
            }
            System.out.println(message);
        }
        catch(Exception e){
            this.socket.close();
            System.out.println("Vous avez été deconnecté");
            System.exit(0);
        }
    }
    public Socket getSocket(){
        return this.socket;
    }
    public String getUsername(){
        return this.username;
    }
    public void startClient(){
        this.clientlirek = new ClientLecture(this);
        this.clientlirek.start();
        while(this.socket.isConnected()){
            String message = scanner.nextLine();
            ecriture.println(message);
            ecriture.flush();
        }
        scanner.close();
    }
    public static void main(String[] args) {
        try {
            System.out.println("Donnez votre nom : ");
            Scanner sc = new Scanner(System.in);
            String nom = sc.nextLine();
            // System.out.println("Serveur choisi : "+ipserveur);
            Client client = new Client();
            client.connectTo("localhost",4444,nom);
            PrintWriter writer = new PrintWriter(client.socket.getOutputStream());
            InputStreamReader stream = new InputStreamReader(client.socket.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            writer.flush();
            // while (true){
            // writer.println(sc.nextLine());
            // writer.flush();
            // }
            client.startClient();
            String message = reader.readLine();
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter datee = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String dateFormatee = date.format(datee);
            String leMessage = "[" + dateFormatee + "] " + nom+" : "+message;
            //System.out.println(leMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}