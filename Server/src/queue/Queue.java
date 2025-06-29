package queue;

public class Queue{
    
    public Node head;
    public Node tail;
    public int QueueLength;

    public Queue(){
        this.head=null;
        this.tail=null;
        this.QueueLength=0;
    }

    public void enqueue(Node n){
        if(this.QueueLength==0){
            this.head=n;
            this.tail=n;
            this.QueueLength=1;
        }
        else{
            n.before=this.tail; //o antes do novo é o antigo novo
            n.after=null;  //não há nada depois do novo
            this.tail.after=n;   //o depois do antigo novo é o novo novo
            this.tail=n;    //a cauda da fila vira o novo
            this.QueueLength++;
        }
    }

    public Player dequeue(){
                
        if(this.QueueLength==0){
            System.out.println("Empty queue");
            return null;
        }
        else if(this.QueueLength==1){ 
            Player dequeued = this.head.player;
            this.head = null; 
            this.tail = null;
            this.QueueLength=0;
            return dequeued;
        }
        else{
            Player dequeued = this.head.player;
            this.head=this.head.after; //o primeiro da fila é o antigo segundo
            this.head.before=null; //o antes do primeiro (antigo segundo) vira null
            this.QueueLength--;
            return dequeued;
        }
    
    }

    public void print_queue(){
        if(this.QueueLength==0){System.out.println("\nEmpty List");}
        System.out.print("Queue start: ");
        Node percorre=this.head;
        while(percorre!=null){
            System.out.print(percorre.player.getName()+" ");
            percorre=percorre.after;
        }
        System.out.println();
    }

}
