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
    public String userName;
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
    
    private static int player_1_or_2 = 0;

    public void dealer(ArrayList<String> data){ //recebe a mensagem e age de acordo
        if(data.get(0).equals("LOGIN SUCCESSFUL")){
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        
            LoginPanel.getUsernametext().setText("");
            LoginPanel.getPasswordtext().setText("");
        }
        else if(data.get(0).equals("LOGIN FAILED")){
            ScreenFunctions.error_message("Nome ou senha incorretos","Erro");
        }
        else if(data.get(0).equals("CREATE SUCCESSFUL")){
            ScreenFunctions.information_message("Usuário criado com sucesso", "Sucesso!");
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(LoginPanel.getPanel());
            Screen.mainPanel.setVisible(true);

            CreatePanel.getUsernametext().setText("");
            CreatePanel.getPassword1text().setText("");
            CreatePanel.getPassword2text().setText("");
        }
        else if(data.get(0).equals("CREATE FAILED")){
            ScreenFunctions.error_message("Nome do usuário já existe","Erro");
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
            
            player_1_or_2=Integer.parseInt(data.get(1));
            if(player_1_or_2==1){PlayPanel.vez.setText("Sua vez");}
            else if(player_1_or_2==2){PlayPanel.vez.setText("Vez do oponente");}
            
            MenuPanel.getLoadingLabel().setVisible(false);
            MenuPanel.getLoadingBar().setVisible(false);
            MenuPanel.getStop().setVisible(false);

            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(PlayPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        
        }
        else if(data.get(0).equals("FLIPTURN")){
            PlayPanel.vez.setText("Vez do oponente");
            // if(i.tampa.isVisible()){i.vez.setText("Sua vez");i.tampa.setVisible(false);}
            // else{i.vez.setText("Vez do oponente");i.tampa.setVisible(true);}
        }
        else if(data.get(0).equals("MOVEMENT")){
            
            String[] move=line_break(data.get(1),4,'/');
            
            ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        
            int linhaINICIO = 7-(Integer.parseInt(""+move[2].charAt(1))-1);
            int colunaINICIO = c.indexOf(""+move[2].charAt(0));
        
            int linhaFIM = 7-(Integer.parseInt(""+move[3].charAt(1))-1);
            int colunaFIM = c.indexOf(""+move[3].charAt(0));            

            Screen.myMouseListenerDragDrop.troca_imagem("ADV_"+move[0],prefixo(move[1],4),linhaINICIO,colunaINICIO,linhaFIM,colunaFIM);
            PlayPanel.campo_coordenadas[linhaFIM][colunaFIM]="ADV_"+move[0];
            PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO]=" ";
            //i.campo_coordenadas[linhaINICIO-1][colunaINICIO]=" ";
            
            //flipturn
            PlayPanel.vez.setText("Sua vez");

        }
        else if(data.get(0).equals("WON")){ScreenFunctions.information_message("Você ganhou","Mensagem");}
        else if(data.get(0).equals("LOST")){ScreenFunctions.information_message("Você perdeu","Mensagem");}
    }

        public static String[] line_break(String linha, int n_de_itens, char delimiter){

        String[] array = new String[n_de_itens];
        for(int j=0;j<n_de_itens;j++){
            array[j]="";
        }

        int campo_do_array=0;
        for(int i=0;i<linha.length();i++){
            if(campo_do_array>=n_de_itens){System.out.println("oi"+campo_do_array+"Erro no uso da função Linebreak.lb, campo_do_array>n_de_itens");}
            else if(linha.charAt(i)==delimiter){campo_do_array++;}
            else if(linha.charAt(i)!=delimiter){array[campo_do_array]+=linha.charAt(i);}
        }
        if(campo_do_array!=(n_de_itens-1)){System.out.println("campo_do_array!=(n_de_itens-1)" + linha);}
        return array;
        
    }

    public static String prefixo(String linha, int quantidadeDEcaracteres){

        String resultado="";
        for(int i=quantidadeDEcaracteres;i<linha.length();i++){
            resultado+=linha.charAt(i);
        }

        return resultado;

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
