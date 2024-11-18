import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.*; 
import java.net.*;
import java.util.*;

public class Server{
        private ServerSocket serverSocket; 
        private ArrayList<LocalDateTime> times =new ArrayList<>();

        public Server(int port) throws IOException{
            serverSocket = new ServerSocket(port);
           // ArrayList<LocalDateTime> times = new ArrayList<>(); //connection times
            // try{
            //     serverSocket = new ServerSocket(port);
            // }
            // catch (IOException e){
            //     System.out.print("Could not open server");
            // }
            // times = new ArrayList<>(); //connection times
             }

    public void serve(int numClients) throws IOException{
        for(int i = 0; i < numClients; i++){
            Socket clientSocket = serverSocket.accept();
         
                times.add(LocalDateTime.now());//adding to connected time, to track?
            
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

                //out.println("handshake sent");
                String request = in.readLine();
                if(!"12345".equals(request)) {
                    out.println("couldn't handshake");
                    out.flush();
                    clientSocket.close();
                    return; 
                }
               
                String req = in.readLine();
                try {
                    int parsing = Integer.parseInt(req);
                    int result = numFactors(parsing);
                    out.println("The number " + parsing + " has " + result + " factors");
                    out.flush();
                }
                catch (NumberFormatException e) {
                    out.println("There was an exception on the server");
                    out.flush();
                }

                clientSocket.close();

                }
                catch(Exception e){//IOException?
                    e.printStackTrace();
                }
        }
    }

        private int numFactors(int n){
           
            int count = 0;

             for (int i=1; i <= n; i++){
                if (n % i == 0){
                    count++;
                    // if(i != n/i){
                    //     count ++; 
                    // }
                }
            } 
            return count;
        }
    }



