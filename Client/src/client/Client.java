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

import main_panels.InitialPanel;
import main_panels.LoginPanel;
import main_panels.MenuPanel;
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

    public void dealer(ArrayList<String> data){ //recebe a mensagem e age de acordo
        if(data.get(0).equals("LOGIN SUCCESSFUL")){
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
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
        }
        else if(data.get(0).equals("CREATE FAILED")){
            ScreenFunctions.error_message("Nome do usuário já existe","Erro");
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
