package server;
import database.Queries;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import queue.Node;
import queue.Player;
import sha256.Sha256;

public class ClientHandler implements Runnable{
    
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    private Player p1;
    private Player p2;

    public void dealer(ArrayList<String> data){ //recebe a mensagem e age de acordo
        if(data.get(0).equals("LOGIN")){
            
            if(Queries.loginValid(data.get(1), Sha256.hash(data.get(2)))){
                this.clientUsername=data.get(1);
                clientHandlers.add(this);
                System.out.println("Login");
                try {
                    this.bufferedWriter.write("LOGIN SUCCESSFUL-"+data.get(1)+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (Exception e) {}
                //sendMessage(this,"LOGIN SUCCESSFUL- - "); //pode usar esta função para enviar a mensagem porque o nome está definido e a mensagem será enviada para o clienthandler que atualmente está como this
            }
            else{
                try{
                    this.bufferedWriter.write("LOGIN FAILED- - ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }catch(Exception e){}
                System.out.println("Login failed");
            }

        }
        else if(data.get(0).equals("CREATE")){

            if(Queries.usernameExists(data.get(1))){ //confirmar se username já existe
                try{
                    this.bufferedWriter.write("CREATE FAILED- - ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }catch(Exception e){}
                System.out.println("Not Created");
            }
            else{
                this.clientUsername=data.get(1);
                clientHandlers.add(this);
                Queries.createUser(data.get(1),Sha256.hash(data.get(2)));
                System.out.println("Created");
                sendMessage("CREATE SUCCESSFUL- - ");
            }
            
        }
        else if(data.get(0).equals("LOGOUT")){
            clientHandlers.remove(this);
            System.out.println(this.clientUsername+" has left");
        }
        else if(data.get(0).equals("RANKING")){
            if(data.get(1).equals("WINS")){
                String response = Queries.getRankingWins();
                try {
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
            else if (data.get(1).equals("GAMES")) {
                String response = Queries.getRankingGames();
                try {
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
            else if (data.get(1).equals("WINRATE")) {
                String response = Queries.getRankingWinrate();
                try {
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
            else if (data.get(1).equals("JOINED")) {
                String response = Queries.getRankingDate();
                try {
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
        }
        else if(data.get(0).equals("DEQUEUE")){
            Server.q.dequeue();
            sendMessage("DEQUEUE SUCCESSFUL- - ");
        }
        else if(data.get(0).equals("QUEUE")){
            Server.q.enqueue(new Node(new Player(this.clientUsername,this)));
            Server.q.print_queue();
            sendMessage("QUEUE WAITING- - ");
        
            if(Server.q.QueueLength>=2){
                p1 = Server.q.dequeue();
                p2 = Server.q.dequeue();
                try{
                    p1.getClientHandler().getBufferedWriter().write("QUEUE SUCCESSFUL-1- ");
                    p1.getClientHandler().getBufferedWriter().newLine();
                    p1.getClientHandler().getBufferedWriter().flush();
                    p2.getClientHandler().getBufferedWriter().write("QUEUE SUCCESSFUL-2- ");
                    p2.getClientHandler().getBufferedWriter().newLine();
                    p2.getClientHandler().getBufferedWriter().flush();
                }catch(Exception e){}
                
                Server.roomLIST.add(new Room(p1,p2,Server.rooms));
                
                Server.rooms++;
                
                Server.printROOMS();
                
            }

        }
        else if(data.get(0).equals("MOVEMENT")){
            
            //pode substituir todo data[1] por this.clientUsername
            
            int v=0;
            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1)) || room.p2.getName().equals(data.get(1))){
                    p1=room.p1;
                    p2=room.p2;
                    v=room.vez;
                    
                    if( (data.get(1).equals(this.p1.getName()) && v==1)){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            p1.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            p2.getClientHandler().getBufferedWriter().write("MOVEMENT-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(Exception e){}

                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.vez=2;}
                        }
                        
                    }
                    else if(data.get(1).equals(this.p2.getName()) && v==2){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            p1.getClientHandler().getBufferedWriter().write("MOVEMENT-"+data.get(2)+"- ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            p2.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(Exception e){}
                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.vez=1;}
                        }

                    }

                }
            }
                        
        }
        else if(data.get(0).equals("PROMOTION")){
            
            //pode substituir todo data[1] por this.clientUsername
            
            int v=0;
            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1)) || room.p2.getName().equals(data.get(1))){
                    p1=room.p1;
                    p2=room.p2;
                    v=room.vez;
                    
                    if( (data.get(1).equals(this.p1.getName()) && v==1)){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            p1.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            p2.getClientHandler().getBufferedWriter().write("PROMOTED-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(Exception e){}

                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.vez=2;}
                        }
                        
                    }
                    else if(data.get(1).equals(this.p2.getName()) && v==2){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            p1.getClientHandler().getBufferedWriter().write("PROMOTED-"+data.get(2)+"- ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            p2.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(Exception e){}
                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.vez=1;}
                        }

                    }

                }
            }
                        
        }       
        else if(data.get(0).equals("END")){
            
            int ID=0;

            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1))){
                    ID=room.ID;
                    try{
                        room.p1.getClientHandler().getBufferedWriter().write("WON- - ");
                        room.p1.getClientHandler().getBufferedWriter().newLine();
                        room.p1.getClientHandler().getBufferedWriter().flush();
                        room.p2.getClientHandler().getBufferedWriter().write("LOST- - ");
                        room.p2.getClientHandler().getBufferedWriter().newLine();
                        room.p2.getClientHandler().getBufferedWriter().flush();
                    }catch(Exception e){
                        e.printStackTrace();
                    }                    
                }
                else if(room.p2.getName().equals(data.get(1))){
                    ID=room.ID;
                    try{
                        room.p1.getClientHandler().getBufferedWriter().write("LOST- - ");
                        room.p1.getClientHandler().getBufferedWriter().newLine();
                        room.p1.getClientHandler().getBufferedWriter().flush();
                        room.p2.getClientHandler().getBufferedWriter().write("WON- - ");
                        room.p2.getClientHandler().getBufferedWriter().newLine();
                        room.p2.getClientHandler().getBufferedWriter().flush();    
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }
            Server.endROOM(ID);
        }
    }

    public ClientHandler(Socket socket){ //conhecer o cliente
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
        }
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run(){
        while(socket.isConnected()){
            try{
                String message =  bufferedReader.readLine();
                System.out.println(message);
                ArrayList<String> data = line_break(message,'-');
                dealer(data);

            }
            catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void sendMessage(String messageToSend){
        for(ClientHandler clientHandler:clientHandlers){
            try{
                if(clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
            catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void sendMessage(ClientHandler ch, String message){
            
        try{
            ch.bufferedWriter.write(message);
            ch.bufferedWriter.newLine();
            ch.bufferedWriter.flush();
        }
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    // public void sendMessageToPlayer(String messageToSend){
    //     try{
    //         this.bufferedWriter.write(messageToSend);
    //         this.bufferedWriter.newLine();
    //         this.bufferedWriter.flush();
    //     }
    //     catch(IOException e){
    //         closeEverything(socket, bufferedReader, bufferedWriter);
    //     }
    // }
    
    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler:clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
            catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+clientUsername+" has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<String> line_break(String line, char delimiter){

        String delimiterRegex = Character.toString(delimiter);
        String[] items = line.split(Pattern.quote(delimiterRegex));
        return new ArrayList<>(Arrays.asList(items));
        
    }
}
