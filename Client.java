import java.net.Socket;
import java.io.*; 
import java.net.*;

public class Client{
    protected Socket client; 
    protected PrintWriter out;
    protected BufferedReader in;


    public Client(String host, int port) throws IOException {
        client = new Socket(host, port);
        out = new PrintWriter(client.getOutputStream());//output stream
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));//input stream 

        // Socket client = new Socket("localhost", 2021);
        // PrintWriter out = new PrintWriter(client.getOutputStream());
        // BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void handshake() throws IOException{
        System.out.println("12345");//initial handshake

        String line = in.readLine();

        if("handshake sent".equals(line)){
            return;
        }
        else{
            throw new IOException("couldn't handshake" + line);
        }
    }

    public void disconnect() throws IOException{
        client.close();
        in.close();
        out.close();
    }

    public String request(String num) throws IOException{
        out.println(num);//sending the number to the server
        out.flush(); 

        String numFactors = in.readLine();//reads response from the server.
        //request is going to be requestiing the calculation that the server does (by itself) 
        return "The number " + num + " has " + numFactors + " factors"; 
        //ex: "The number 47483647 has 4 factors
    }

    public Socket getSocket(){
        return client; 
    }

}
