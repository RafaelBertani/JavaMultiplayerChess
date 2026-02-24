package server;

import queue.Player;

public class Room {
    
    public Player p1;
    public Player p2;

    public int ID;

    // 1 or 2
    public int turn=1;

    public Room(Player p1, Player p2, int ID){
        this.p1=p1;
        this.p2=p2;
        this.ID=ID;
    }

}
