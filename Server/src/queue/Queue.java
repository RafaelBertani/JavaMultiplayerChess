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

    public void remove(Node n) {

        if (n == null || this.QueueLength == 0) return;

        if (n == this.head) {
            this.head = n.after;
            if (this.head != null) this.head.before = null;
        } else if (n == this.tail) {
            this.tail = n.before;
            if (this.tail != null) this.tail.after = null;
        } else {
            if (n.before != null) n.before.after = n.after;
            if (n.after != null) n.after.before = n.before;
        }

        n.before = null;
        n.after = null;

        this.QueueLength--;

        if (this.QueueLength == 0) {
            this.head = null;
            this.tail = null;
        }
    }

    public boolean removeByPlayer(Player p) {
        if (p == null || this.QueueLength == 0) return false;

        Node atual = this.head;
        while (atual != null) {
            if (atual.player.equals(p)) {
                // Reutiliza a lógica de remoção direta
                if (atual == this.head) {
                    this.head = atual.after;
                    if (this.head != null) this.head.before = null;
                } else if (atual == this.tail) {
                    this.tail = atual.before;
                    if (this.tail != null) this.tail.after = null;
                } else {
                    if (atual.before != null) atual.before.after = atual.after;
                    if (atual.after != null) atual.after.before = atual.before;
                }

                atual.before = null;
                atual.after = null;
                this.QueueLength--;

                // Se a fila ficou vazia
                if (this.QueueLength == 0) {
                    this.head = null;
                    this.tail = null;
                }

                return true; // Remoção feita
            }

            atual = atual.after;
        }

        return false; // Player não encontrado
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
