package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

import client.Client;
import java.awt.Panel;
import main_panels.PlayPanel;
import screen.Screen;
import screen.ScreenFunctions;

public class MyMouseListenerDragDrop implements MouseListener{
    
    public String holding = "";
    public String subs = "";

    //CLICKED E RELEASED SÓ FUNCIONAM COM (MOUSEPRESSED)TransferHandler.MOVE, ENQUANTO COM (MOUSEPRESSED)TransferHandler.COPY FUNCIONAM ENTERED E EXITED
    @Override
    public void mouseClicked(MouseEvent e){
        //System.out.println("Mouse clicked in: "+((JLabel)e.getSource()).getName());
    }
    @Override
    public void mouseReleased(MouseEvent e){
        //System.out.println("Mouse released in: "+((JLabel)e.getSource()).getName());
    }
    @Override
    public void mousePressed(MouseEvent e){
        JComponent jc = (JComponent)e.getSource();
        TransferHandler th = jc.getTransferHandler();
        th.exportAsDrag(jc,e,TransferHandler.COPY);
        //System.out.println("Mouse holding: "+((JLabel)e.getSource()).getName());
        
        holding=""+((JLabel)e.getSource()).getName();
    }
    @Override
    public void mouseEntered(MouseEvent e){
        //System.out.println("Mouse entered in: "+((JLabel)e.getSource()).getName());
        
        subs=""+((JLabel)e.getSource()).getName();
        //001B[30m ~ 001B[37m sendo o \u001B[0m o reset
        if(!holding.equals("") && !subs.equals("") && !holding.equals(subs)){
            
            //System.out.println("\u001B[33m"+holding+" put in the slot of "+subs+"\u001B[0m");
            if(!PlayPanel.turn.getText().equals("Sua vez")){
                //nada
            }
            //conferir jogada, se possível, manda, caso contrário manda aviso e em todos os casos, reverte troca
            else if(confere(holding, subs)){
                
                ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
    
                int linhaINICIO = Integer.parseInt(""+holding.charAt(1))-1;
                int colunaINICIO = c.indexOf(""+holding.charAt(0));
    
                int linhaFIM = Integer.parseInt(""+subs.charAt(1))-1;
                int colunaFIM = c.indexOf(""+subs.charAt(0));
              
                if(PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains("King")){Screen.client.sendMessage("END-"+Screen.client.userName+"- ");}
                else{

                    boolean isPlayer1 = Client.player_1_or_2==1;

                    troca_imagem(
                        PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],
                        PlayPanel.coordinates_field[linhaFIM][colunaFIM],
                        isPlayer1?linhaINICIO:7-linhaINICIO,
                        isPlayer1?colunaINICIO:7-colunaINICIO,
                        isPlayer1?linhaFIM:7-linhaFIM,
                        isPlayer1?colunaFIM:7-colunaFIM
                    );
                    Screen.client.sendMessage("MOVEMENT-"+Screen.client.userName+"-"+PlayPanel.coordinates_field[linhaINICIO][7-colunaINICIO]+"/"+PlayPanel.coordinates_field[linhaFIM][colunaFIM]+"/"+holding+"/"+subs);

                    PlayPanel.coordinates_field[linhaFIM][colunaFIM]=PlayPanel.coordinates_field[linhaINICIO][colunaINICIO];
                    PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]=" ";

                    for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            ArrayList<String> letters = new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
                            PlayPanel.transparent[i][j].setName(""+letters.get(isPlayer1?j:7-j)+""+(isPlayer1?7-i+1:i+1));
                            PlayPanel.transparent[i][j].setBounds(50+(550/9)*j, 50+(550/9)*i, (550/9), (550/9));
                        }
                    }
                }

            }
            else{
                ScreenFunctions.error_message("Jogada inválida","Erro");
            }
        }
        holding="";
        subs="";
    }
    @Override
    public void mouseExited(MouseEvent e){
        //System.out.println("Mouse exited in: "+((JLabel)e.getSource()).getName());
    }

    public boolean confere(String holding, String subs){
        ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        
        int linhaINICIO = Integer.parseInt(""+holding.charAt(1))-1;
        int colunaINICIO = c.indexOf(""+holding.charAt(0));
        String nomeINICIO=PlayPanel.coordinates_field[linhaINICIO][colunaINICIO];       

        int linhaFIM = Integer.parseInt(""+subs.charAt(1))-1;
        int colunaFIM = c.indexOf(""+subs.charAt(0));
        String nomeFIM=PlayPanel.coordinates_field[linhaFIM][colunaFIM];       

        //System.out.println(nomeINICIO);

        boolean isPlayer1 = Client.player_1_or_2==1;
        
        if(nomeINICIO.equals(" ")){return false;}
        //se o peão avança para um espaço com um adversário ou para um espaço vazio (ou seja, se ele não avança para um aliado)
        else if(nomeINICIO.contains("Pawn") && ( (nomeFIM.contains(isPlayer1?"Black":"White")) || nomeFIM.equals(" ") ) ){
            
            //se peão chegou ao fim sem comer
            if(linhaFIM==(isPlayer1?7:0) && linhaINICIO==(isPlayer1?6:1) && PlayPanel.coordinates_field[linhaFIM][colunaFIM].equals(" ")){return true;}
            //se peão chegou ao fim comendo
            else if(linhaFIM==(isPlayer1?7:0) && (colunaINICIO==colunaFIM-1 || colunaINICIO==colunaFIM+1) && linhaFIM==linhaINICIO+(isPlayer1?1:-1) && PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"Black":"White")){return true;}
            //se é a primeira jogada do peão
            else if(linhaINICIO==(isPlayer1?1:6) && colunaINICIO==colunaFIM && (linhaFIM==linhaINICIO+(isPlayer1?1:-1) || linhaFIM==linhaINICIO+(isPlayer1?2:-2)) && PlayPanel.coordinates_field[linhaFIM][colunaFIM].equals(" ")){return true;}
            //se é a segunda jogada do peão
            else if(colunaINICIO==colunaFIM && linhaFIM==linhaINICIO+(isPlayer1?1:-1) && PlayPanel.coordinates_field[linhaFIM][colunaFIM].equals(" ")){return true;}
            //se peão está comendo
            else if( (colunaINICIO==colunaFIM-1 || colunaINICIO==colunaFIM+1) && linhaFIM==linhaINICIO+(isPlayer1?1:-1) && PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"Black":"White")){return true;}
            else{return false;}

        }
        //else if(nomeINICIO.contains("Rook") && ( (nomeFIM.contains(isPlayer1?"Black":"White")) || nomeFIM.equals(" ") )){
        else if(nomeINICIO.contains("Rook") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")){
            
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            if(linhaINICIO<linhaFIM && colunaINICIO==colunaFIM){jogada1=true;}
            else if(linhaINICIO==linhaFIM && colunaINICIO<colunaFIM){jogada2=true;}
            else if(linhaINICIO>linhaFIM && colunaINICIO==colunaFIM){jogada3=true;}
            else if(linhaINICIO==linhaFIM && colunaINICIO>colunaFIM){jogada4=true;}
            
            //jogada 1
            if(jogada1){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){
                    if(!PlayPanel.coordinates_field[linhaINICIO+i][colunaINICIO].equals(" ")){valida=false;}
                }
                //if( PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black") ){valida=false;}
                return valida;
            }
            //jogada 2
            else if(jogada2){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){
                    if(!PlayPanel.coordinates_field[linhaINICIO][colunaINICIO+i].equals(" ")){valida=false;}
                }
                //if( PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black") ){valida=false;}
                return valida;
            }
            //jogada 3
            else if(jogada3){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){
                    if(!PlayPanel.coordinates_field[linhaINICIO-i][colunaINICIO].equals(" ")){valida=false;}
                }
                //if( PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black") ){valida=false;}
                return valida;
            }
            //jogada 4
            else if(jogada4){
                boolean valida=true;
                for(int i=1;i<(colunaINICIO-colunaFIM);i++){
                    if(!PlayPanel.coordinates_field[linhaINICIO][colunaINICIO-i].equals(" ")){valida=false;}
                }
                //if( PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black") ){valida=false;}
                return valida;
            }
            else{return false;}
        }
        else if(nomeINICIO.contains("Knight") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")){
            
            //jogada 1
            if(linhaINICIO==linhaFIM+2 && (colunaINICIO==colunaFIM+1 || colunaINICIO==colunaFIM-1) ){return true;}
            //jogada 2
            else if(linhaINICIO==linhaFIM-2 && (colunaINICIO==colunaFIM+1 || colunaINICIO==colunaFIM-1) ){return true;}
            //jogada 3
            else if(colunaINICIO==colunaFIM+2 && (linhaINICIO==linhaFIM+1 || linhaINICIO==linhaFIM-1) ){return true;}
            //jogada 4
            else if(colunaINICIO==colunaFIM-2 && (linhaINICIO==linhaFIM+1 || linhaINICIO==linhaFIM-1) ){return true;}
            else{return false;}
        
        }
        else if(nomeINICIO.contains("Bishop") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")){
            
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            if(linhaINICIO-linhaFIM!=colunaINICIO-colunaFIM){return false;}
            else if(linhaINICIO<linhaFIM && colunaINICIO<colunaFIM){jogada1=true;}
            else if(linhaINICIO>linhaFIM && colunaINICIO<colunaFIM){jogada2=true;}
            else if(linhaINICIO>linhaFIM && colunaINICIO>colunaFIM){jogada3=true;}
            else if(linhaINICIO<linhaFIM && colunaINICIO>colunaFIM){jogada4=true;}
            else{return false;}

            //jogada 1
            if(jogada1){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO+i][colunaINICIO+i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 2
            else if(jogada2){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO-i][colunaINICIO+i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 3
            else if(jogada3){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.coordinates_field[linhaINICIO-i][colunaINICIO-i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 4
            else if(jogada4){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO+i][colunaINICIO-i].equals(" ")){valida=false;}}
                return valida;
            }
            else{return false;}

        }
        else if(nomeINICIO.contains("King") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")){
            
            //jogada 1
            if(linhaINICIO==linhaFIM+1 && colunaINICIO==colunaFIM){return true;}
            //jogada 2
            else if(linhaINICIO==linhaFIM+1 && colunaINICIO==colunaFIM+1){return true;}
            //jogada 3
            else if(linhaINICIO==linhaFIM && colunaINICIO==colunaFIM+1){return true;}
            //jogada 4
            else if(linhaINICIO==linhaFIM-1 && colunaINICIO==colunaFIM+1){return true;}
            //jogada 5
            else if(linhaINICIO==linhaFIM-1 && colunaINICIO==colunaFIM){return true;}
            //jogada 6
            else if(linhaINICIO==linhaFIM-1 && colunaINICIO==colunaFIM-1){return true;}
            //jogada 7
            else if(linhaINICIO==linhaFIM && colunaINICIO==colunaFIM-1){return true;}
            //jogada 8
            else if(linhaINICIO==linhaFIM+1 && colunaINICIO==colunaFIM-1){return true;}
            else{return false;}
        
        }
        else if(nomeINICIO.contains("Queen") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")){
            
            //bishop
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            if(linhaINICIO<linhaFIM && colunaINICIO<colunaFIM){jogada1=true;}
            else if(linhaINICIO>linhaFIM && colunaINICIO<colunaFIM){jogada2=true;}
            else if(linhaINICIO<linhaFIM && colunaINICIO>colunaFIM){jogada3=true;}
            else if(linhaINICIO>linhaFIM && colunaINICIO>colunaFIM){jogada4=true;}
            //rook
            boolean jogada5=false;
            boolean jogada6=false;
            boolean jogada7=false;
            boolean jogada8=false;
            if(linhaINICIO<linhaFIM && colunaINICIO==colunaFIM){jogada5=true;}
            else if(linhaINICIO==linhaFIM && colunaINICIO<colunaFIM){jogada6=true;}
            else if(linhaINICIO>linhaFIM && colunaINICIO==colunaFIM){jogada7=true;}
            else if(linhaINICIO==linhaFIM && colunaINICIO>colunaFIM){jogada8=true;}
            
            //jogada 1
            if(jogada1){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO+i][colunaINICIO+i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 2
            else if(jogada2){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO-i][colunaINICIO+i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 3
            else if(jogada3){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.coordinates_field[linhaINICIO-i][colunaINICIO-i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 4
            else if(jogada4){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO+i][colunaINICIO-i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 5
            else if(jogada5){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO+i][colunaINICIO].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 6
            else if(jogada6){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.coordinates_field[linhaINICIO][colunaINICIO+i].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 7
            else if(jogada7){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.coordinates_field[linhaINICIO-i][colunaINICIO].equals(" ")){valida=false;}}
                return valida;
            }
            //jogada 8
            else if(jogada8){
                boolean valida=true;
                for(int i=1;i<(colunaINICIO-colunaFIM);i++){if(!PlayPanel.coordinates_field[linhaINICIO][colunaINICIO-i].equals(" ")){valida=false;}}
                return valida;
            }
            else{return false;}
        }
        else{return false;}
    }

    public void troca_imagem(String nomeINICIO,String nomeFIM,int linhaINICIO,int colunaINICIO,int linhaFIM,int colunaFIM){
        ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        if(nomeINICIO.equals("Pawn_White_A")){PlayPanel.coordinates(PlayPanel.Pawn_White_A,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_B")){PlayPanel.coordinates(PlayPanel.Pawn_White_B,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_C")){PlayPanel.coordinates(PlayPanel.Pawn_White_C,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_D")){PlayPanel.coordinates(PlayPanel.Pawn_White_D,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_E")){PlayPanel.coordinates(PlayPanel.Pawn_White_E,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_F")){PlayPanel.coordinates(PlayPanel.Pawn_White_F,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_G")){PlayPanel.coordinates(PlayPanel.Pawn_White_G,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_White_H")){PlayPanel.coordinates(PlayPanel.Pawn_White_H,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Rook_White_A")){PlayPanel.coordinates(PlayPanel.Rook_White_A,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Knight_White_B")){PlayPanel.coordinates(PlayPanel.Knight_White_B,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Bishop_White_C")){PlayPanel.coordinates(PlayPanel.Bishop_White_C,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Queen_White_D")){PlayPanel.coordinates(PlayPanel.Queen_White_D,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("King_White_E")){PlayPanel.coordinates(PlayPanel.King_White_E,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Bishop_White_F")){PlayPanel.coordinates(PlayPanel.Bishop_White_F,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Knight_White_G")){PlayPanel.coordinates(PlayPanel.Knight_White_G,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Rook_White_H")){PlayPanel.coordinates(PlayPanel.Rook_White_H,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_A")){PlayPanel.coordinates(PlayPanel.Pawn_Black_A,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_B")){PlayPanel.coordinates(PlayPanel.Pawn_Black_B,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_C")){PlayPanel.coordinates(PlayPanel.Pawn_Black_C,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_D")){PlayPanel.coordinates(PlayPanel.Pawn_Black_D,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_E")){PlayPanel.coordinates(PlayPanel.Pawn_Black_E,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_F")){PlayPanel.coordinates(PlayPanel.Pawn_Black_F,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_G")){PlayPanel.coordinates(PlayPanel.Pawn_Black_G,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Pawn_Black_H")){PlayPanel.coordinates(PlayPanel.Pawn_Black_H,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Rook_Black_A")){PlayPanel.coordinates(PlayPanel.Rook_Black_A,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Knight_Black_B")){PlayPanel.coordinates(PlayPanel.Knight_Black_B,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Bishop_Black_C")){PlayPanel.coordinates(PlayPanel.Bishop_Black_C,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Queen_Black_D")){PlayPanel.coordinates(PlayPanel.Queen_Black_D,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("King_Black_E")){PlayPanel.coordinates(PlayPanel.King_Black_E,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Bishop_Black_F")){PlayPanel.coordinates(PlayPanel.Bishop_Black_F,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Knight_Black_G")){PlayPanel.coordinates(PlayPanel.Knight_Black_G,linhaFIM+1,c.get(colunaFIM));}
        else if(nomeINICIO.equals("Rook_Black_H")){PlayPanel.coordinates(PlayPanel.Rook_Black_H,linhaFIM+1,c.get(colunaFIM));}

        if(!nomeFIM.equals(" ")){
            if(nomeFIM.equals("Pawn_White_A")){PlayPanel.Pawn_White_A.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_B")){PlayPanel.Pawn_White_B.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_C")){PlayPanel.Pawn_White_C.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_D")){PlayPanel.Pawn_White_D.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_E")){PlayPanel.Pawn_White_E.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_F")){PlayPanel.Pawn_White_F.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_G")){PlayPanel.Pawn_White_G.setVisible(false);}
            else if(nomeFIM.equals("Pawn_White_H")){PlayPanel.Pawn_White_H.setVisible(false);}
            else if(nomeFIM.equals("Rook_White_A")){PlayPanel.Rook_White_A.setVisible(false);}
            else if(nomeFIM.equals("Knight_White_B")){PlayPanel.Knight_White_B.setVisible(false);}
            else if(nomeFIM.equals("Bishop_White_C")){PlayPanel.Bishop_White_C.setVisible(false);}
            else if(nomeFIM.equals("Queen_White_D")){PlayPanel.Queen_White_D.setVisible(false);}
            else if(nomeFIM.equals("King_White_E")){PlayPanel.King_White_E.setVisible(false);}
            else if(nomeFIM.equals("Bishop_White_F")){PlayPanel.Bishop_White_F.setVisible(false);}
            else if(nomeFIM.equals("Knight_White_G")){PlayPanel.Knight_White_G.setVisible(false);}
            else if(nomeFIM.equals("Rook_White_H")){PlayPanel.Rook_White_H.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_A")){PlayPanel.Pawn_Black_A.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_B")){PlayPanel.Pawn_Black_B.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_C")){PlayPanel.Pawn_Black_C.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_D")){PlayPanel.Pawn_Black_D.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_E")){PlayPanel.Pawn_Black_E.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_F")){PlayPanel.Pawn_Black_F.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_G")){PlayPanel.Pawn_Black_G.setVisible(false);}
            else if(nomeFIM.equals("Pawn_Black_H")){PlayPanel.Pawn_Black_H.setVisible(false);}
            else if(nomeFIM.equals("Rook_Black_A")){PlayPanel.Rook_Black_A.setVisible(false);}
            else if(nomeFIM.equals("Knight_Black_B")){PlayPanel.Knight_Black_B.setVisible(false);}
            else if(nomeFIM.equals("Bishop_Black_C")){PlayPanel.Bishop_Black_C.setVisible(false);}
            else if(nomeFIM.equals("Queen_Black_D")){PlayPanel.Queen_Black_D.setVisible(false);}
            else if(nomeFIM.equals("King_Black_E")){PlayPanel.King_Black_E.setVisible(false);}
            else if(nomeFIM.equals("Bishop_Black_F")){PlayPanel.Bishop_Black_F.setVisible(false);}
            else if(nomeFIM.equals("Knight_Black_G")){PlayPanel.Knight_Black_G.setVisible(false);}
            else if(nomeFIM.equals("Rook_Black_H")){PlayPanel.Rook_Black_H.setVisible(false);}
        }
    }

    public static String sufixo(String linha, int quantidadeDEcaracteres){
     
        String resultado="";
        for(int i=0;i<linha.length()-quantidadeDEcaracteres;i++){
            resultado+=linha.charAt(i);
        }

        return resultado;

    }


}