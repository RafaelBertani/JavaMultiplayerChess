import client.Client;
import java.net.Socket;
import screen.Screen;

public class Main{

    public static void main(String[] args) throws Exception{
    
        Socket socket = new Socket("localhost",1234);
        Client c = new Client(socket);
        Screen screen = new Screen(c);
        c.listenForMessage(screen);
    }
    
}
