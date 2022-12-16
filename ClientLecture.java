import java.io.IOException;
import java.net.Socket;

public class ClientLecture extends Thread{
    private Client client;
    private Socket socket;
    
public ClientLecture(Client client){
    this.client = client;
    this.socket = client.getSocket();
}

public void run(){
    try{
        while(socket.isConnected()){
            this.client.lire();
        }
    }
    catch(Exception e){

    }
}
}
