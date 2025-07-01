package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import queue.Queue;

public class Server{
    
    private ServerSocket serverSocket;

    public static Queue q = new Queue();

    public static List<Room> roomLIST = new ArrayList<>();

    //counter for id
    public static int rooms=0;

    public static boolean endROOM(int ID){
        boolean q=false;
        for(Room room : roomLIST){
            if(room.ID==ID){roomLIST.remove(room);q=true;break;}
        }
        return q;
    }

    public static void printROOMS(){
        System.out.println("Rooms: ");
        for(Room room : roomLIST){
            room.printROOM();
        }
    }
    
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){

        try{

            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                //System.out.println("A new client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        
        }
        catch(IOException e){

        }

    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
