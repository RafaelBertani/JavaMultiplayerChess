package queue;

public class Node{

    public Player player;
    public Node before;
    public Node after;

    public Node(Player p){
        this.player=p;
        this.before=null;
        this.after=null;
    }
    
}
