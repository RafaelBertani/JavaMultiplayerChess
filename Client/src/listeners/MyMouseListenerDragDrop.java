package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
        System.out.println("Mouse clicked in: "+((JLabel)e.getSource()).getName());
    }
    @Override
    public void mouseReleased(MouseEvent e){
        System.out.println("Mouse released in: "+((JLabel)e.getSource()).getName());
    }
    @Override
    public void mousePressed(MouseEvent e){
        JComponent jc = (JComponent)e.getSource();
        TransferHandler th = jc.getTransferHandler();
        th.exportAsDrag(jc,e,TransferHandler.COPY);
        System.out.println("Mouse holding: "+((JLabel)e.getSource()).getName());
        
        holding=""+((JLabel)e.getSource()).getName();
    }
    @Override
    public void mouseEntered(MouseEvent e){
        //System.out.println("Mouse entered in: "+((JLabel)e.getSource()).getName());
        
        subs=""+((JLabel)e.getSource()).getName();
        //001B[30m ~ 001B[37m sendo o \u001B[0m o reset
        if(holding!="" && subs!=""){


            System.out.println("\u001B[33m"+holding+" put in the slot of "+subs+"\u001B[0m");
            if(false){}
            if(!PlayPanel.vez.getText().equals("Sua vez")){
                
            }
            //conferir jogada, se possível, manda, caso contrário manda aviso e em todos os casos, reverte troca
            else if(confere(holding, subs)){
                
                ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
    
                int linhaINICIO = Integer.parseInt(""+holding.charAt(1))-1;
                int colunaINICIO = c.indexOf(""+holding.charAt(0));
    
                int linhaFIM = Integer.parseInt(""+subs.charAt(1))-1;
                int colunaFIM = c.indexOf(""+subs.charAt(0));            

                if(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals("ADV_ReiE")){Screen.client.sendMessage("END-"+Screen.client.userName+"- ");}
                else{
                    troca_imagem(PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO],PlayPanel.campo_coordenadas[linhaFIM][colunaFIM],linhaINICIO,colunaINICIO,linhaFIM,colunaFIM);
                    Screen.client.sendMessage("MOVEMENT-"+Screen.client.userName+"-"+PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO]+"/"+PlayPanel.campo_coordenadas[linhaFIM][colunaFIM]+"/"+holding+"/"+subs);

                    PlayPanel.campo_coordenadas[linhaFIM][colunaFIM]=PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO];
                    PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO]=" ";
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
        String nomeINICIO=PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO];       

        int linhaFIM = Integer.parseInt(""+subs.charAt(1))-1;
        int colunaFIM = c.indexOf(""+subs.charAt(0));
        String nomeFIM=PlayPanel.campo_coordenadas[linhaFIM][colunaFIM];       

        if(nomeINICIO.equals(" ")){return false;}
        else if(sufixo(nomeINICIO,1).equals("Peão") && ( (nomeFIM.charAt(0)=='A') || nomeFIM.equals(" ") ) ){
            //se peão chegou ao fim sem comer
            if(linhaFIM==7 && linhaINICIO==6 && PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ")){return true;}
            //se peão chegou ao fim comendo
            else if(linhaFIM==7 && (colunaINICIO==colunaFIM-1 || colunaINICIO==colunaFIM+1) && linhaFIM==linhaINICIO+1 && PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")){return true;}
            //se é a primeira jogada do peão
            else if(linhaINICIO==1  && colunaINICIO==colunaFIM && (linhaFIM==linhaINICIO+1 || linhaFIM==linhaINICIO+2) && PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ")){return true;}
            //se é a segunda jogada do peão
            else if(colunaINICIO==colunaFIM && linhaFIM==linhaINICIO+1 && PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ")){return true;}
            //se peão está comendo
            else if( (colunaINICIO==colunaFIM-1 || colunaINICIO==colunaFIM+1) && linhaFIM==linhaINICIO+1 && PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].charAt(0)=='A'){return true;}
            else{return false;}
        }
        else if(sufixo(nomeINICIO,1).equals("Torre")){
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            for(int i=1;i<=8;i++){if(linhaINICIO+i==linhaFIM && colunaINICIO==colunaFIM){jogada1=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO==linhaFIM && colunaINICIO+i==colunaFIM){jogada2=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO-i==linhaFIM && colunaINICIO==colunaFIM){jogada3=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO==linhaFIM && colunaINICIO-i==colunaFIM){jogada4=true;}}
            
            //jogada 1
            if(jogada1){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO+i][colunaINICIO].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 2
            else if(jogada2){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO+i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 3
            else if(jogada3){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO-i][colunaINICIO].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 4
            else if(jogada4){
                boolean valida=true;
                for(int i=1;i<(colunaINICIO-colunaFIM);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO-i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            else{return false;}
        }
        else if(sufixo(nomeINICIO,1).equals("Cavalo")){
            //jogada 1
            if(linhaINICIO==linhaFIM+2 && (colunaINICIO==colunaFIM+1 || colunaINICIO==colunaFIM-1) && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_"))){return true;}
            //jogada 2
            else if(linhaINICIO==linhaFIM-2 && (colunaINICIO==colunaFIM+1 || colunaINICIO==colunaFIM-1) && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_"))){return true;}
            //jogada 3
            else if(colunaINICIO==colunaFIM+2 && (linhaINICIO==linhaFIM+1 || linhaINICIO==linhaFIM-1) && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_"))){return true;}
            //jogada 4
            else if(colunaINICIO==colunaFIM-2 && (linhaINICIO==linhaFIM+1 || linhaINICIO==linhaFIM-1) && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_"))){return true;}
            else{return false;}
        }
        else if(sufixo(nomeINICIO,1).equals("Bispo")){
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            for(int i=1;i<=8;i++){if(linhaINICIO+i==linhaFIM && colunaINICIO+i==colunaFIM){jogada1=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO-i==linhaFIM && colunaINICIO+i==colunaFIM){jogada2=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO-i==linhaFIM && colunaINICIO-i==colunaFIM){jogada3=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO+i==linhaFIM && colunaINICIO-i==colunaFIM){jogada4=true;}}
            //jogada 1
            if(jogada1){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO+i][colunaINICIO+i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 2
            else if(jogada2){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO-i][colunaINICIO+i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 3
            else if(jogada3){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO-i][colunaINICIO-i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 4
            else if(jogada4){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO+i][colunaINICIO-i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            else{return false;}
        }
        else if(sufixo(nomeINICIO,1).equals("Rei")){
            //jogada 1
            if(linhaINICIO==linhaFIM+1 && colunaINICIO==colunaFIM && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 2
            else if(linhaINICIO==linhaFIM+1 && colunaINICIO==colunaFIM+1 && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 3
            else if(linhaINICIO==linhaFIM && colunaINICIO==colunaFIM+1 && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 4
            else if(linhaINICIO==linhaFIM-1 && colunaINICIO==colunaFIM+1 && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 5
            else if(linhaINICIO==linhaFIM-1 && colunaINICIO==colunaFIM && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 6
            else if(linhaINICIO==linhaFIM-1 && colunaINICIO==colunaFIM-1 && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 7
            else if(linhaINICIO==linhaFIM && colunaINICIO==colunaFIM-1 && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            //jogada 8
            else if(linhaINICIO==linhaFIM+1 && colunaINICIO==colunaFIM-1 && (PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_")) ){return true;}
            else{return false;}
        }
        else if(sufixo(nomeINICIO,1).equals("Rainha")){
            boolean jogada1=false;
            boolean jogada2=false;
            boolean jogada3=false;
            boolean jogada4=false;
            for(int i=1;i<=8;i++){if(linhaINICIO+i==linhaFIM && colunaINICIO+i==colunaFIM){jogada1=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO-i==linhaFIM && colunaINICIO+i==colunaFIM){jogada2=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO-i==linhaFIM && colunaINICIO-i==colunaFIM){jogada3=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO+i==linhaFIM && colunaINICIO-i==colunaFIM){jogada4=true;}}
            boolean jogada5=false;
            boolean jogada6=false;
            boolean jogada7=false;
            boolean jogada8=false;
            for(int i=1;i<=8;i++){if(linhaINICIO+i==linhaFIM && colunaINICIO==colunaFIM){jogada5=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO==linhaFIM && colunaINICIO+i==colunaFIM){jogada6=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO-i==linhaFIM && colunaINICIO==colunaFIM){jogada7=true;}}
            for(int i=1;i<=8;i++){if(linhaINICIO==linhaFIM && colunaINICIO-i==colunaFIM){jogada8=true;}}
            
            //jogada 1
            if(jogada1){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO+i][colunaINICIO+i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 2
            else if(jogada2){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO-i][colunaINICIO+i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 3
            else if(jogada3){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO-i][colunaINICIO-i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 4
            else if(jogada4){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO+i][colunaINICIO-i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 5
            else if(jogada5){
                boolean valida=true;
                for(int i=1;i<(linhaFIM-linhaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO+i][colunaINICIO].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 6
            else if(jogada6){
                boolean valida=true;
                for(int i=1;i<(colunaFIM-colunaINICIO);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO+i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 7
            else if(jogada7){
                boolean valida=true;
                for(int i=1;i<(linhaINICIO-linhaFIM);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO-i][colunaINICIO].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            //jogada 8
            else if(jogada8){
                boolean valida=true;
                for(int i=1;i<(colunaINICIO-colunaFIM);i++){if(!PlayPanel.campo_coordenadas[linhaINICIO][colunaINICIO-i].equals(" ")){valida=false;}}
                if( !(PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].contains("ADV_") || PlayPanel.campo_coordenadas[linhaFIM][colunaFIM].equals(" ") )){valida=false;}
                return valida;
            }
            else{return false;}
        }
        else{return false;}
    }

    public void troca_imagem(String nomeINICIO,String nomeFIM,int linhaINICIO,int colunaINICIO,int linhaFIM,int colunaFIM){
        ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        if(nomeINICIO.equals("PeãoA")){PlayPanel.coordenadas(PlayPanel.PeaoA,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoB")){PlayPanel.coordenadas(PlayPanel.PeaoB,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoC")){PlayPanel.coordenadas(PlayPanel.PeaoC,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoD")){PlayPanel.coordenadas(PlayPanel.PeaoD,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoE")){PlayPanel.coordenadas(PlayPanel.PeaoE,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoF")){PlayPanel.coordenadas(PlayPanel.PeaoF,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoG")){PlayPanel.coordenadas(PlayPanel.PeaoG,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("PeãoH")){PlayPanel.coordenadas(PlayPanel.PeaoH,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("TorreA")){PlayPanel.coordenadas(PlayPanel.TorreA,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("CavaloB")){PlayPanel.coordenadas(PlayPanel.CavaloB,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("BispoC")){PlayPanel.coordenadas(PlayPanel.BispoC,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("RainhaD")){PlayPanel.coordenadas(PlayPanel.RainhaD,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ReiE")){PlayPanel.coordenadas(PlayPanel.ReiE,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("BispoF")){PlayPanel.coordenadas(PlayPanel.BispoF,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("CavaloG")){PlayPanel.coordenadas(PlayPanel.CavaloG,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("TorreH")){PlayPanel.coordenadas(PlayPanel.TorreH,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoA")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoA,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoB")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoB,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoC")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoC,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoD")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoD,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoE")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoE,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoF")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoF,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoG")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoG,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_PeãoH")){PlayPanel.coordenadas(PlayPanel.ADV_PeaoH,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_TorreA")){PlayPanel.coordenadas(PlayPanel.ADV_TorreA,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_CavaloB")){PlayPanel.coordenadas(PlayPanel.ADV_CavaloB,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_BispoC")){PlayPanel.coordenadas(PlayPanel.ADV_BispoC,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_RainhaE")){PlayPanel.coordenadas(PlayPanel.ADV_RainhaE,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_ReiD")){PlayPanel.coordenadas(PlayPanel.ADV_ReiD,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_BispoF")){PlayPanel.coordenadas(PlayPanel.ADV_BispoF,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_CavaloG")){PlayPanel.coordenadas(PlayPanel.ADV_CavaloG,linhaFIM,c.get(colunaFIM));}
        else if(nomeINICIO.equals("ADV_TorreH")){PlayPanel.coordenadas(PlayPanel.ADV_TorreH,linhaFIM,c.get(colunaFIM));}

        if(!nomeFIM.equals(" ")){
            if(nomeFIM.equals("PeãoA")){PlayPanel.PeaoA.setVisible(false);}
            else if(nomeFIM.equals("PeãoB")){PlayPanel.PeaoB.setVisible(false);}
            else if(nomeFIM.equals("PeãoC")){PlayPanel.PeaoC.setVisible(false);}
            else if(nomeFIM.equals("PeãoD")){PlayPanel.PeaoD.setVisible(false);}
            else if(nomeFIM.equals("PeãoE")){PlayPanel.PeaoE.setVisible(false);}
            else if(nomeFIM.equals("PeãoF")){PlayPanel.PeaoF.setVisible(false);}
            else if(nomeFIM.equals("PeãoG")){PlayPanel.PeaoG.setVisible(false);}
            else if(nomeFIM.equals("PeãoH")){PlayPanel.PeaoH.setVisible(false);}
            else if(nomeFIM.equals("TorreA")){PlayPanel.TorreA.setVisible(false);}
            else if(nomeFIM.equals("CavaloB")){PlayPanel.CavaloB.setVisible(false);}
            else if(nomeFIM.equals("BispoC")){PlayPanel.BispoC.setVisible(false);}
            else if(nomeFIM.equals("RainhaD")){PlayPanel.RainhaD.setVisible(false);}
            else if(nomeFIM.equals("ReiE")){PlayPanel.ReiE.setVisible(false);}
            else if(nomeFIM.equals("BispoF")){PlayPanel.BispoF.setVisible(false);}
            else if(nomeFIM.equals("CavaloG")){PlayPanel.CavaloG.setVisible(false);}
            else if(nomeFIM.equals("TorreH")){PlayPanel.TorreH.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoA")){PlayPanel.ADV_PeaoA.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoB")){PlayPanel.ADV_PeaoB.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoC")){PlayPanel.ADV_PeaoC.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoD")){PlayPanel.ADV_PeaoD.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoE")){PlayPanel.ADV_PeaoE.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoF")){PlayPanel.ADV_PeaoF.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoG")){PlayPanel.ADV_PeaoG.setVisible(false);}
            else if(nomeFIM.equals("ADV_PeãoH")){PlayPanel.ADV_PeaoH.setVisible(false);}
            else if(nomeFIM.equals("ADV_TorreA")){PlayPanel.ADV_TorreA.setVisible(false);}
            else if(nomeFIM.equals("ADV_CavaloB")){PlayPanel.ADV_CavaloB.setVisible(false);}
            else if(nomeFIM.equals("ADV_BispoC")){PlayPanel.ADV_BispoC.setVisible(false);}
            else if(nomeFIM.equals("ADV_RainhaE")){PlayPanel.ADV_RainhaE.setVisible(false);}
            else if(nomeFIM.equals("ADV_ReiD")){PlayPanel.ADV_ReiD.setVisible(false);}
            else if(nomeFIM.equals("ADV_BispoF")){PlayPanel.ADV_BispoF.setVisible(false);}
            else if(nomeFIM.equals("ADV_CavaloG")){PlayPanel.ADV_CavaloG.setVisible(false);}
            else if(nomeFIM.equals("ADV_TorreH")){PlayPanel.ADV_TorreH.setVisible(false);}
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