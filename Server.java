import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.*; 
import java.net.*;
import java.util.*;

public class Server{
    protected ServerSocket serverSocket; 
    protected ArrayList<LocalDateTime> times;

    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        times = new ArrayList<>(getConnectedTimes());
    }

    //takes as argument the number of clients it is expected to serve per test case
    //serve methid will have a loop for as many clients as specified in its argument
    //after accepting a client via successful handshake, this method will process the client
    //request in a separate(new?) thread so that the server can continue to accpet connections
    //while these expensive factorization calculations are being performed on behalf of 
    //various clients 
    public void serve(int numClients) throws IOException{
        for(int i = 0; i < numClients; i++){
            Socket remote = serverSocket.accept();
            times.add(LocalDateTime.now());//adding to connected time, to track?
            //new Thread

        }

    }

    //returning a sorted ArrayList of LocalDateTime
    //representing the connection time of every client 
    public ArrayList<LocalDateTime> getConnectedTimes(){
        return new ArrayList<>(times);
    }

    public void disconnect(){
        try{
            serverSocket.close();
        } catch (IOException e){
            System.out.println("An exception happened.");
        }
    }

}
