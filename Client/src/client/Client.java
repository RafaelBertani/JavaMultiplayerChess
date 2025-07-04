package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import listeners.MyMouseListenerDragDrop;
import main_panels.CreatePanel;
import main_panels.LoginPanel;
import main_panels.MenuPanel;
import main_panels.PlayPanel;
import main_panels.RankingPanel;
import screen.Screen;
import screen.ScreenFunctions;

public class Client{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static String userName;
    public static Screen screen;

    public Client(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = "";
        }
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String msg){
        try{
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
        }
        catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // public void sendMessage(){
    //     try{
    //         bufferedWriter.write(userName);
    //         bufferedWriter.newLine();
    //         bufferedWriter.flush();

    //         Scanner scanner = new Scanner(System.in);
    //         while(socket.isConnected()){
    //             String messageToSend = scanner.nextLine();
    //             //bufferedWriter.write(userName+": "+messageToSend);
    //             bufferedWriter.write(messageToSend);
    //             bufferedWriter.newLine();
    //             bufferedWriter.flush();
    //         }
    //     }
    //     catch(IOException e){
    //         closeEverything(socket, bufferedReader, bufferedWriter);
    //     }
    // }
    
    public static int player_1_or_2 = 0;

    public void dealer(ArrayList<String> data){ //recebe a mensagem e age de acordo
        if(data.get(0).equals("LOGIN SUCCESSFUL")){
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        
            //set nome do usuário
            Client.userName=data.get(1);

            LoginPanel.getUsernametext().setText("");
            LoginPanel.getPasswordtext().setText("");
        }
        else if(data.get(0).equals("LOGIN FAILED")){
            ScreenFunctions.error_message(Screen.bn.getString("login.failed.content"),Screen.bn.getString("login.failed.title"));
        }
        else if(data.get(0).equals("CREATE SUCCESSFUL")){
            ScreenFunctions.information_message(Screen.bn.getString("create.success.content"), Screen.bn.getString("create.success.title"));
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(LoginPanel.getPanel());
            Screen.mainPanel.setVisible(true);

            CreatePanel.getUsernametext().setText("");
            CreatePanel.getPassword1text().setText("");
            CreatePanel.getPassword2text().setText("");
        }
        else if(data.get(0).equals("CREATE FAILED")){
            ScreenFunctions.error_message(Screen.bn.getString("create.failed.content"),Screen.bn.getString("create.failed.title"));
        }
        else if(data.get(0).equals("RANKING")){
            ArrayList<String[]> userList = new ArrayList<>();
            String[] item = new String[5];
            int counter = 0;
            for (String line : line_break(data.get(1), '*')) {
                for (String  conlumn: line_break(line, '|')) {
                    item[counter]=conlumn;
                    counter++;
                }
                userList.add(item);
                item = new String[5];
                counter = 0;
            }
            RankingPanel.setItemList(userList);

            RankingPanel.getPanel().setVisible(false);
            RankingPanel.getPanel().remove(RankingPanel.getBackgroundImage());
            RankingPanel.update_table();
            RankingPanel.getPanel().add(RankingPanel.getBackgroundImage());
            RankingPanel.getPanel().setVisible(true);
        }
        else if(data.get(0).equals("QUEUE WAITING")){
            MenuPanel.getLoadingLabel().setVisible(true);
            MenuPanel.getLoadingBar().setVisible(true);
            MenuPanel.getLoadingBar().setValue(0);
            MenuPanel.getStop().setVisible(true);
        }
        else if(data.get(0).equals("DEQUEUE SUCCESSFUL")){
            MenuPanel.getLoadingLabel().setVisible(false);
            MenuPanel.getLoadingBar().setVisible(false);
            MenuPanel.getStop().setVisible(false);
        }
        else if(data.get(0).equals("QUEUE SUCCESSFUL")){
            
            //limpa da partida anterior
            PlayPanel.promoted.clear();

            player_1_or_2=Integer.parseInt(data.get(1));
            if(player_1_or_2==1){PlayPanel.turn.setText(Screen.bn.getString("play.yourturn"));}
            else if(player_1_or_2==2){PlayPanel.turn.setText(Screen.bn.getString("play.otherturn"));}
            PlayPanel.position(player_1_or_2==1);
            
            MenuPanel.getLoadingLabel().setVisible(false);
            MenuPanel.getLoadingBar().setVisible(false);
            MenuPanel.getStop().setVisible(false);

            Screen.menuBar.setVisible(false);

            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(PlayPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        
        }
        else if(data.get(0).equals("FLIPTURN")){
            PlayPanel.turn.setText(Screen.bn.getString("play.otherturn"));
            // if(i.tampa.isVisible()){i.vez.setText("Sua vez");i.tampa.setVisible(false);}
            // else{i.vez.setText("Vez do oponente");i.tampa.setVisible(true);}
        }
        else if(data.get(0).equals("MOVEMENT")){
            ArrayList<String> move = line_break(data.get(1),'/');
            
            ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        
            int linhaINICIO = (Integer.parseInt(""+move.get(2).charAt(1))-1);
            int colunaINICIO = c.indexOf(""+move.get(2).charAt(0));
        
            int linhaFIM = (Integer.parseInt(""+move.get(3).charAt(1))-1);
            int colunaFIM = c.indexOf(""+move.get(3).charAt(0));            

            boolean isPlayer1 = Client.player_1_or_2==1;

            //System.out.println("Recebi "+linhaINICIO+" "+colunaINICIO+" "+linhaFIM+" "+colunaFIM+" que é o: "+PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]);

            PlayPanel.getPanel().setVisible(false);
            Screen.myMouseListenerDragDrop.troca_imagem(
                move.get(0),
                move.get(1),
                isPlayer1?linhaINICIO:7-linhaINICIO,
                isPlayer1?colunaINICIO:7-colunaINICIO,
                isPlayer1?linhaFIM:7-linhaFIM,
                isPlayer1?colunaFIM:7-colunaFIM
            );
            //7- porque visualmente, por coordenadas, os movimentos são espelhados
            PlayPanel.getPanel().setVisible(true);
            PlayPanel.coordinates_field[linhaFIM][colunaFIM]=move.get(0);
            PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]=" ";
            
            PlayPanel.addToHistory(""+move.get(2)+"->"+move.get(3), !isPlayer1);

            //flipturn
            PlayPanel.turn.setVisible(false);
            PlayPanel.turn.setText(Screen.bn.getString("play.yourturn"));
            PlayPanel.turn.setVisible(true);
        }
        else if(data.get(0).equals("PROMOTED")){
            ArrayList<String> move = line_break(data.get(1),'/');
            
            ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        
            int linhaINICIO = (Integer.parseInt(""+move.get(2).charAt(1))-1);
            int colunaINICIO = c.indexOf(""+move.get(2).charAt(0));
        
            int linhaFIM = (Integer.parseInt(""+move.get(3).charAt(1))-1);
            int colunaFIM = c.indexOf(""+move.get(3).charAt(0));            

            boolean isPlayer1 = Client.player_1_or_2==1;

            //System.out.println("Recebi "+linhaINICIO+" "+colunaINICIO+" "+linhaFIM+" "+colunaFIM+" que é o: "+PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]);

            PlayPanel.getPanel().setVisible(false);
            Screen.myMouseListenerDragDrop.troca_imagem(
                move.get(0),
                move.get(1),
                isPlayer1?linhaINICIO:7-linhaINICIO,
                isPlayer1?colunaINICIO:7-colunaINICIO,
                isPlayer1?linhaFIM:7-linhaFIM,
                isPlayer1?colunaFIM:7-colunaFIM
            );
            MyMouseListenerDragDrop.promotion(PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],move.get(4));
            //PlayPanel.promoted.add(new String[][]{{PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],move[4]}});
            //7- porque visualmente, por coordenadas, os movimentos são espelhados
            PlayPanel.getPanel().setVisible(true);
            PlayPanel.coordinates_field[linhaFIM][colunaFIM]=move.get(0);
            PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]=" ";
            
            PlayPanel.addToHistory(""+move.get(2)+"->"+move.get(3), !isPlayer1);

            //flipturn
            PlayPanel.turn.setVisible(false);
            PlayPanel.turn.setText(Screen.bn.getString("play.yourturn"));
            PlayPanel.turn.setVisible(true);
            
        }
        else if(data.get(0).equals("WON")){
            ScreenFunctions.information_message(Screen.bn.getString("play.won.content"), Screen.bn.getString("play.won.title"));
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
            Screen.menuBar.setVisible(true);
        }
        else if(data.get(0).equals("LOST")){
            ScreenFunctions.information_message(Screen.bn.getString("play.lost.content"), Screen.bn.getString("play.lost.title"));
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
            Screen.menuBar.setVisible(true);
        }
        else if(data.get(0).equals("FORFEIT")){
            ScreenFunctions.information_message(Screen.bn.getString("play.won.content"), Screen.bn.getString("play.won.title"));
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
            Screen.menuBar.setVisible(true);
        }
    }

    public void listenForMessage(Screen screen){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msg;
                while(socket.isConnected()){
                    try{
                        msg = bufferedReader.readLine();
                        dealer(line_break(msg,'-'));
                    }catch(IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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
