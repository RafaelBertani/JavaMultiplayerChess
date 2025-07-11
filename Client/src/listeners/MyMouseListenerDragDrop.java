package listeners;

import client.Client;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
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
            if(!PlayPanel.turn.getText().equals(Screen.bn.getString("play.yourturn"))){
                //nada
            }
            //conferir jogada, se possível, manda, caso contrário manda aviso e em todos os casos, reverte troca
            else if(confere(holding, subs)){
                
                ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
    
                int linhaINICIO = Integer.parseInt(""+holding.charAt(1))-1;
                int colunaINICIO = c.indexOf(""+holding.charAt(0));
    
                int linhaFIM = Integer.parseInt(""+subs.charAt(1))-1;
                int colunaFIM = c.indexOf(""+subs.charAt(0));
              
                //System.out.println(linhaINICIO+""+c.get(colunaINICIO)+" "+linhaFIM+""+c.get(colunaFIM)+" "+PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]);

                boolean isPlayer1 = Client.player_1_or_2==1;

                if(PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains("King")){Screen.client.sendMessage("END-"+Screen.client.userName+"- ");}
                else{

                    if(PlayPanel.coordinates_field[linhaINICIO][colunaINICIO].contains("Pawn") && linhaFIM==(isPlayer1?7:0)){

                        troca_imagem(
                            PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],
                            PlayPanel.coordinates_field[linhaFIM][colunaFIM],
                            //porque o posicionamento é espelhado:
                            isPlayer1?linhaINICIO:7-linhaINICIO,
                            isPlayer1?colunaINICIO:7-colunaINICIO,
                            isPlayer1?linhaFIM:7-linhaFIM,
                            isPlayer1?colunaFIM:7-colunaFIM
                        );
                        //System.out.println(PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]);
                        int option = -1;
                        do{
                            option = ScreenFunctions.options_message(
                                Screen.bn.getString("play.promotion.content"),
                                Screen.bn.getString("play.promotion.title"),
                                new String[]{
                                    Screen.bn.getString("play.queen"),
                                    Screen.bn.getString("play.bishop"),
                                    Screen.bn.getString("play.rook"),
                                    Screen.bn.getString("play.knight")
                                }
                            );
                        }while(option==-1);
                        String[] type = new String[]{"Queen","Bishop","Rook","Knight"};
                        troca_imagem(
                            PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],
                            PlayPanel.coordinates_field[linhaFIM][colunaFIM],
                            //porque o posicionamento é espelhado:
                            isPlayer1?linhaINICIO:7-linhaINICIO,
                            isPlayer1?colunaINICIO:7-colunaINICIO,
                            isPlayer1?linhaFIM:7-linhaFIM,
                            isPlayer1?colunaFIM:7-colunaFIM
                        );
                        promotion(PlayPanel.coordinates_field[linhaINICIO][colunaINICIO], type[option]);
                        Screen.client.sendMessage("PROMOTION-"+Screen.client.userName+"-"+PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]+"/"+PlayPanel.coordinates_field[linhaFIM][colunaFIM]+"/"+holding+"/"+subs+"/"+type[option]);
                        PlayPanel.promoted.add(Arrays.asList(PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],type[option]));
                        PlayPanel.coordinates_field[linhaFIM][colunaFIM]=PlayPanel.coordinates_field[linhaINICIO][colunaINICIO];
                        PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]=" ";

                    }
                    else{
                        troca_imagem(
                            PlayPanel.coordinates_field[linhaINICIO][colunaINICIO],
                            PlayPanel.coordinates_field[linhaFIM][colunaFIM],
                            //porque o posicionamento é espelhado:
                            isPlayer1?linhaINICIO:7-linhaINICIO,
                            isPlayer1?colunaINICIO:7-colunaINICIO,
                            isPlayer1?linhaFIM:7-linhaFIM,
                            isPlayer1?colunaFIM:7-colunaFIM
                        );
                        //System.out.println(PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]);
                        Screen.client.sendMessage("MOVEMENT-"+Screen.client.userName+"-"+PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]+"/"+PlayPanel.coordinates_field[linhaFIM][colunaFIM]+"/"+holding+"/"+subs);

                        PlayPanel.coordinates_field[linhaFIM][colunaFIM]=PlayPanel.coordinates_field[linhaINICIO][colunaINICIO];
                        PlayPanel.coordinates_field[linhaINICIO][colunaINICIO]=" ";
                    }

                    PlayPanel.addToHistory(""+holding+"->"+subs, isPlayer1);

                }

                for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            ArrayList<String> letters = new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
                            PlayPanel.transparent[i][j].setVisible(false);
                            PlayPanel.transparent[i][j].setName(""+letters.get(isPlayer1?j:7-j)+""+(isPlayer1?7-i+1:i+1));
                            PlayPanel.transparent[i][j].setBounds(50+(550/9)*j, 50+(550/9)*i, (550/9), (550/9));
                            PlayPanel.transparent[i][j].setVisible(true);
                        }
                }

            }
            else{
                ScreenFunctions.error_message(Screen.bn.getString("play.error.content"),Screen.bn.getString("play.error.title"));
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
        //else if(nomeINICIO.contains("Rook") && ( (nomeFIM.contains(isPlayer1?"Black":"White")) || nomeFIM.equals(" ") )){
        else if(
            (nomeINICIO.contains("Rook") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")) ||
            (nomeINICIO.contains("Pawn") && PlayPanel.promoted.contains(Arrays.asList(nomeINICIO,"Rook")) && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black"))       
        ){
            
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
        else if(
            (nomeINICIO.contains("Knight") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")) ||
            (nomeINICIO.contains("Pawn") && PlayPanel.promoted.contains(Arrays.asList(nomeINICIO,"Knight")) && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black"))
        ){
            
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
        else if(
            (nomeINICIO.contains("Bishop") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")) ||
            (nomeINICIO.contains("Pawn") && PlayPanel.promoted.contains(Arrays.asList(nomeINICIO,"Bishop")) && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black"))
        ){
            
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            if(Math.abs(linhaINICIO - linhaFIM) != Math.abs(colunaINICIO - colunaFIM)){return false;}
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
        else if(
            (nomeINICIO.contains("Queen") && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black")) ||
            (nomeINICIO.contains("Pawn") && PlayPanel.promoted.contains(Arrays.asList(nomeINICIO,"Queen")) && !PlayPanel.coordinates_field[linhaFIM][colunaFIM].contains(isPlayer1?"White":"Black"))
        ){

            int deltaLinha = linhaFIM - linhaINICIO;
            int deltaColuna = colunaFIM - colunaINICIO;

            // A rainha se move na diagonal OU linha OU coluna
            if (deltaLinha != 0 && deltaColuna != 0 && Math.abs(deltaLinha) != Math.abs(deltaColuna)) {
                return false; // movimento inválido
            }

            // Determinar os passos (direção)
            int passoLinha = Integer.compare(deltaLinha, 0);   // retorna -1, 0 ou 1
            int passoColuna = Integer.compare(deltaColuna, 0); // retorna -1, 0 ou 1

            int linha = linhaINICIO + passoLinha;
            int coluna = colunaINICIO + passoColuna;

            // Verifica se há peças no caminho (exceto no destino)
            while (linha != linhaFIM || coluna != colunaFIM) {
                if (!PlayPanel.coordinates_field[linha][coluna].equals(" ")) {
                    return false; // obstáculo encontrado
                }
                linha += passoLinha;
                coluna += passoColuna;
            }

            return true; // movimento válido
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

    public static void promotion(String nomeINICIO, String option){
        boolean isPlayer1 = nomeINICIO.contains("White");
        if(nomeINICIO.equals("Pawn_White_A")){change(PlayPanel.Pawn_White_A,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_B")){change(PlayPanel.Pawn_White_B,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_C")){change(PlayPanel.Pawn_White_C,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_D")){change(PlayPanel.Pawn_White_D,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_E")){change(PlayPanel.Pawn_White_E,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_F")){change(PlayPanel.Pawn_White_F,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_G")){change(PlayPanel.Pawn_White_G,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_White_H")){change(PlayPanel.Pawn_White_H,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_A")){change(PlayPanel.Pawn_Black_A,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_B")){change(PlayPanel.Pawn_Black_B,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_C")){change(PlayPanel.Pawn_Black_C,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_D")){change(PlayPanel.Pawn_Black_D,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_E")){change(PlayPanel.Pawn_Black_E,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_F")){change(PlayPanel.Pawn_Black_F,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_G")){change(PlayPanel.Pawn_Black_G,option,isPlayer1);}
        else if(nomeINICIO.equals("Pawn_Black_H")){change(PlayPanel.Pawn_Black_H,option,isPlayer1);}
    }

    public static void change(JLabel peca, String option, boolean isPlayer1){
            ImageIcon icon = new ImageIcon("./src/images/"+option+"_"+(isPlayer1?"White":"Black")+".png");
            Image scaledImage = icon.getImage().getScaledInstance(550/15, 550/15, Image.SCALE_SMOOTH);
            peca.setIcon(new ImageIcon(scaledImage));
    }

}