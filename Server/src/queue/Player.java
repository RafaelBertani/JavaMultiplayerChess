package queue;

import server.ClientHandler;

public class Player{
    
    private String name;
    private ClientHandler clientHandler;

    public Player(String n, ClientHandler ch){
        this.name=n;
        this.clientHandler=ch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

}
