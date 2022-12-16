import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
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
        String message = this.lecteur.readLine();
        System.out.println(message);
    }
    public Socket getSocket(){
        return this.socket;
    }
    public void ecriture(){
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
            // System.out.println("Donnez l'adresse IP du serveur : ");
            // Scanner sc = new Scanner(System.in);
            // String ipserveur = sc.nextLine();
            // System.out.println("Serveur choisi : "+ipserveur);
            Socket socket = new Socket("localhost", 4444);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            InputStreamReader stream = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            writer.println("Hellow world!");
            writer.flush();
            // while (true){
            // writer.println(sc.nextLine());
            // writer.flush();
            // }
            String message = reader.readLine();
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}