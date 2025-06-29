package room;

public class Movement{

    public String mv1;
    public String mv2;
    public int ID;
    
    public Movement(String mv1, String mv2, int ID){
        this.mv1=mv1;
        this.mv2=mv2;
        this.ID=ID;
    }

    public void clear(){
        mv1="";
        mv2="";
    }

}
