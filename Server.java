import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.*; 
import java.net.*;
import java.util.*;

public class Server{
        private ServerSocket serverSocket; 
        private ArrayList<LocalDateTime> times;

        public Server(int port) throws IOException{
            // serverSocket = new ServerSocket(port);
            // times = new ArrayList<>(); //connection times
            try{
                serverSocket = new ServerSocket(port);
            }
            catch (IOException e){
                System.out.print("Could not open server");
            }
            times = new ArrayList<>(); //connection times
             }

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
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.println("handshake sent");
                String request = in.readLine();

                if (request != null) {
                    String result = numFactors(request);
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

        private String numFactors(String request){
            long n = Long.parseLong(request);
            int count = 0;

             for (int i=1; i * i <= n; i++){
                if (n % i == 0){
                    count++;
                    if(i != n/i){
                        count ++; 
                    }
                }
            }
            
        return String.valueOf(count); 
        }   

    }

}


