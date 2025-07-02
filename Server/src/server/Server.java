package server;

import database.Queries;
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

    public static boolean endROOM(int ID, int victoriousPlayer){
        boolean success = false;
        for(Room room : roomLIST){
            if(room.ID==ID){
                Queries.playerWon(victoriousPlayer==1?room.p1.getName():room.p2.getName());
                Queries.playerLost(victoriousPlayer==1?room.p2.getName():room.p1.getName());
                roomLIST.remove(room);
                success=true;
                break;
            }
        }
        return success;
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
