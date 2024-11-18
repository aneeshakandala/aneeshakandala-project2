import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.*; 
import java.net.*;
import java.util.*;

public class Server{
    public static class serverThread extends Thread {
        private ServerSocket serverSocket; 
        private ArrayList<LocalDateTime> times;

        public serverThread(int port){
            serverSocket = new ServerSocket(port);
            times = new ArrayList<>(); //connection times
        }

    //takes as argument the number of clients it is expected to serve per test case
    //serve methid will have a loop for as many clients as specified in its argument
    //after accepting a client via successful handshake, this method will process the client
    //request in a separate(new?) thread so that the server can continue to accpet connections
    //while these expensive factorization calculations are being performed on behalf of 
    //various clients 
    public void serve(int numClients) throws IOException{
        for(int i = 0; i < numClients; i++){
            Socket clientSocket = serverSocket.accept();
            synchronized(times){
                times.add(LocalDateTime.now());//adding to connected time, to track?
            }
                //make a new thread
            new ClientHandler(clientSocket).start();
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

    private class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override 
        public void run(){
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                out = new PrintWriter(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String request = in.readLine();

                if (request != null) {
                    String result = factorize(request);
                    out.println(result);
                }
                out.close();
                in.close();
                clientSocket.close();
            }
                catch(Exception e){//IOException?
                    e.printStackTrace();
                }
        }

        private String factorize(String request){
            try {
                int number = INteger
            }
        }
    }

}

    }