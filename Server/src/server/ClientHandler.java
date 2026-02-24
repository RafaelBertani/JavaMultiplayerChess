package server;
import aes.CryptoAES;
import database.Queries;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import queue.Node;
import queue.Player;
import screen.Screen;

public class ClientHandler implements Runnable {
    
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

    public void dealer(ArrayList<String> data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, Exception{ //recebe a mensagem e age de acordo

        String requestType = data.get(0);

        if(requestType.equals("LOGIN")){
            
            if(Queries.loginValid(data.get(1), data.get(2)) ){
                this.clientUsername=data.get(1);
                clientHandlers.add(this);
                try {
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): LOGIN SUCCESSFUL-"+data.get(1)+"- ");
                    this.bufferedWriter.write("LOGIN SUCCESSFUL-"+data.get(1)+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
                //sendMessage(this,"LOGIN SUCCESSFUL- - "); //pode usar esta função para enviar a mensagem porque o nome está definido e a mensagem será enviada para o clienthandler que atualmente está como this
            }
            else{
                try{
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): LOGIN FAILED- - ");
                    this.bufferedWriter.write("LOGIN FAILED- - ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }catch(IOException e){}
            }

        }
        else if(requestType.equals("CREATE")){

            if(Queries.usernameExists(data.get(1))){ //confirmar se username já existe
                try{
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): CREATE FAILED- - ");
                    this.bufferedWriter.write("CREATE FAILED- - ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }catch(IOException e){}
            }
            else{
                Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): CREATE SUCCESSFUL- - ");
                this.clientUsername=data.get(1);
                clientHandlers.add(this);

                CryptoAES aes = new CryptoAES();
                aes.geraChave();
                Queries.createUser(data.get(1), aes.geraCifra(data.get(2),aes.getSecretKey()), Base64.getEncoder().encodeToString(aes.getSecretKey().getEncoded()) );
                sendMessage("CREATE SUCCESSFUL- - ");
            }
            
        }
        else if(requestType.equals("LOGOUT")){
            clientHandlers.remove(this);
        }
        else if(requestType.equals("RANKING")){
            if(data.get(1).equals("WINS")){
                String response = Queries.getRankingWins(data.get(2));
                try {
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): RANKING-"+response+"- ");
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
            else if (data.get(1).equals("GAMES")) {
                String response = Queries.getRankingGames(data.get(2));
                try {
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): RANKING-"+response+"- ");
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
            else if (data.get(1).equals("WINRATE")) {
                String response = Queries.getRankingWinrate(data.get(2));
                try {
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): RANKING-"+response+"- ");
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
            else if (data.get(1).equals("JOINED")) {
                String response = Queries.getRankingDate(data.get(2));
                try {
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): RANKING-"+response+"- ");
                    this.bufferedWriter.write("RANKING-"+response+"- ");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } catch (IOException e) {}
            }
        }
        else if(requestType.equals("DEQUEUE")){
            Server.q.dequeue();
            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): DEQUEUE SUCCESSFUL- - ");
            sendMessage("DEQUEUE SUCCESSFUL- - ");
        }
        else if(requestType.equals("QUEUE")){
            Server.q.enqueue(new Node(new Player(this.clientUsername,this)));
            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): QUEUE WAITING- - ");
            sendMessage("QUEUE WAITING- - ");
        
            if(Server.q.QueueLength>=2){
                p1 = Server.q.dequeue();
                p2 = Server.q.dequeue();
                try{
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): QUEUE SUCCESSFUL-1- ");
                    p1.getClientHandler().getBufferedWriter().write("QUEUE SUCCESSFUL-1- ");
                    p1.getClientHandler().getBufferedWriter().newLine();
                    p1.getClientHandler().getBufferedWriter().flush();
                    Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.clientUsername+"): QUEUE SUCCESSFUL-2- ");
                    p2.getClientHandler().getBufferedWriter().write("QUEUE SUCCESSFUL-2- ");
                    p2.getClientHandler().getBufferedWriter().newLine();
                    p2.getClientHandler().getBufferedWriter().flush();
                }catch(IOException e){}
                
                Server.roomLIST.add(new Room(p1,p2,Server.rooms));
                
                Server.rooms++;
                                
            }

        }
        else if(requestType.equals("MOVEMENT")){
            
            //pode substituir todo data[1] por this.clientUsername
            
            int v=0;
            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1)) || room.p2.getName().equals(data.get(1))){
                    p1=room.p1;
                    p2=room.p2;
                    v=room.turn;
                    
                    if( (data.get(1).equals(this.p1.getName()) && v==1)){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p1.getName()+"): FLIPTURN- - ");
                            p1.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p2.getName()+"): MOVEMENT-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().write("MOVEMENT-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(IOException e){}

                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.turn=2;}
                        }
                        
                    }
                    else if(data.get(1).equals(this.p2.getName()) && v==2){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p1.getName()+"): MOVEMENT-"+data.get(2)+"- ");
                            p1.getClientHandler().getBufferedWriter().write("MOVEMENT-"+data.get(2)+"- ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p2.getName()+"): MOVEMENT-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(IOException e){}
                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.turn=1;}
                        }

                    }

                }
            }
                        
        }
        else if(requestType.equals("PROMOTION")){
            
            //pode substituir todo data[1] por this.clientUsername
            
            int v=0;
            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1)) || room.p2.getName().equals(data.get(1))){
                    p1=room.p1;
                    p2=room.p2;
                    v=room.turn;
                    
                    if( (data.get(1).equals(this.p1.getName()) && v==1)){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p1.getName()+"): FLIPTURN- - ");
                            p1.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p2.getName()+"): PROMOTED-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().write("PROMOTED-"+data.get(2)+"- ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(Exception e){}

                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.turn=2;}
                        }
                        
                    }
                    else if(data.get(1).equals(this.p2.getName()) && v==2){

                        //MANDA RETORNO PARA O ADVERSÁRIO
                        try{
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p1.getName()+"): PROMOTED-"+data.get(2)+"- ");
                            p1.getClientHandler().getBufferedWriter().write("PROMOTED-"+data.get(2)+"- ");
                            p1.getClientHandler().getBufferedWriter().newLine();
                            p1.getClientHandler().getBufferedWriter().flush();
                            Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p2.getName()+"): FLIPTURN- - ");
                            p2.getClientHandler().getBufferedWriter().write("FLIPTURN- - ");
                            p2.getClientHandler().getBufferedWriter().newLine();
                            p2.getClientHandler().getBufferedWriter().flush();
                        }catch(Exception e){}
                        //flip turn
                        for(Room r : Server.roomLIST){
                            if(r.p1.getName().equals(data.get(1)) || r.p2.getName().equals(data.get(1))){room.turn=1;}
                        }

                    }

                }
            }
                        
        }       
        else if(requestType.equals("END")){
            
            int ID=0;
            int victoriousPlayer=0;

            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1))){
                    ID=room.ID;
                    try{
                        Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p1.getName()+"): WON- - ");
                        room.p1.getClientHandler().getBufferedWriter().write("WON- - ");
                        room.p1.getClientHandler().getBufferedWriter().newLine();
                        room.p1.getClientHandler().getBufferedWriter().flush();
                        Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p2.getName()+"): LOST- - ");
                        room.p2.getClientHandler().getBufferedWriter().write("LOST- - ");
                        room.p2.getClientHandler().getBufferedWriter().newLine();
                        room.p2.getClientHandler().getBufferedWriter().flush();
                    }catch(IOException e){}
                    victoriousPlayer=1;
                }
                else if(room.p2.getName().equals(data.get(1))){
                    ID=room.ID;
                    try{
                        Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p1.getName()+"): LOST- - ");
                        room.p1.getClientHandler().getBufferedWriter().write("LOST- - ");
                        room.p1.getClientHandler().getBufferedWriter().newLine();
                        room.p1.getClientHandler().getBufferedWriter().flush();
                        Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+this.p2.getName()+"): WON- - ");
                        room.p2.getClientHandler().getBufferedWriter().write("WON- - ");
                        room.p2.getClientHandler().getBufferedWriter().newLine();
                        room.p2.getClientHandler().getBufferedWriter().flush();    
                    }catch(IOException e){}
                    victoriousPlayer=2;
                }
            }
            Server.endROOM(ID,victoriousPlayer);
        }
        else if(requestType.equals("FORFEIT")){
            int ID=0;
            int victoriousPlayer=0;

            for(Room room : Server.roomLIST){
                if(room.p1.getName().equals(data.get(1))){
                    ID=room.ID;
                    try{
                        Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+room.p2.getName()+"): FORFEIT- - ");
                        room.p2.getClientHandler().getBufferedWriter().write("FORFEIT- - ");
                        room.p2.getClientHandler().getBufferedWriter().newLine();
                        room.p2.getClientHandler().getBufferedWriter().flush();
                    }catch(Exception e){e.printStackTrace();}
                    victoriousPlayer=2;
                }
                else if(room.p2.getName().equals(data.get(1))){
                    ID=room.ID;
                    try{
                        Screen.setAreaText(Screen.getAreaText()+"\nServer to client ("+room.p1.getName()+"): FORFEIT- - ");
                        room.p1.getClientHandler().getBufferedWriter().write("FORFEIT- - ");
                        room.p1.getClientHandler().getBufferedWriter().newLine();
                        room.p1.getClientHandler().getBufferedWriter().flush();
                    }catch(Exception e){e.printStackTrace();}
                    victoriousPlayer=1;
                }
            }
            Server.endROOM(ID,victoriousPlayer);
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
                Screen.setAreaText(Screen.getAreaText()+"\nClient("+this.clientUsername+"): "+message);
                ArrayList<String> data = lineBreak(message,'-'); //type-content1-content2
                try {
                    dealer(data);
                } catch (Exception e) {
                }
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

    public static ArrayList<String> lineBreak(String line, char delimiter){

        String delimiterRegex = Character.toString(delimiter);
        String[] items = line.split(Pattern.quote(delimiterRegex));
        return new ArrayList<>(Arrays.asList(items));
        
    }

}
