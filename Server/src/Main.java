import database.Database;
import java.net.ServerSocket;
import screen.Screen;
import server.Server;

public class Main {

    public static void main(String[] args) throws Exception{
        
        Screen screen = new Screen();

        try {
            Database.createDatabase(); //cria base de dados, caso ainda não exista
        } catch (Exception e) {
            Screen.setAreaText(Screen.getAreaText()+"\nServer: Erro ao criar base de dados.");
        }
        Database.createTable_Users(); //cria tabela, caso ainda não exista

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }

}
