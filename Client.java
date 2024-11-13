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

    public void request(String num){
        //what does request do?
        //return String? void?
        System.out.println(num);
        return;
    }

    public Socket getSocket(){
        return client; 
    }


}
