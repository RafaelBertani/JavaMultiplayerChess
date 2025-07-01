package main_panels;

import client.Client;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import screen.Screen;
import screen.ScreenFunctions;

public class PlayPanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static JLabel[][] field = new JLabel[8][8];
    private static String[] letters = new String[]{"A","B","C","D","E","F","G","H"};
    private static JLabel[] lettersLabel = new JLabel[8];
    private static JLabel[] numbersLabel = new JLabel[8];

    public static JLabel[][] transparent = new JLabel[8][8];

    public static JLabel Pawn_White_A = new JLabel();
    public static JLabel Pawn_White_B = new JLabel();
    public static JLabel Pawn_White_C = new JLabel();
    public static JLabel Pawn_White_D = new JLabel();
    public static JLabel Pawn_White_E = new JLabel();
    public static JLabel Pawn_White_F = new JLabel();
    public static JLabel Pawn_White_G = new JLabel();
    public static JLabel Pawn_White_H = new JLabel();
    public static JLabel Pawn_Black_A = new JLabel();
    public static JLabel Pawn_Black_B = new JLabel();
    public static JLabel Pawn_Black_C = new JLabel();
    public static JLabel Pawn_Black_D = new JLabel();
    public static JLabel Pawn_Black_E = new JLabel();
    public static JLabel Pawn_Black_F = new JLabel();
    public static JLabel Pawn_Black_G = new JLabel();
    public static JLabel Pawn_Black_H = new JLabel();
    public static JLabel Rook_White_A = new JLabel();
    public static JLabel Rook_White_H = new JLabel();
    public static JLabel Rook_Black_A = new JLabel();
    public static JLabel Rook_Black_H = new JLabel();
    public static JLabel Knight_White_B = new JLabel();
    public static JLabel Knight_White_G = new JLabel();
    public static JLabel Knight_Black_B = new JLabel();
    public static JLabel Knight_Black_G = new JLabel();
    public static JLabel Bishop_White_C = new JLabel();
    public static JLabel Bishop_White_F = new JLabel();
    public static JLabel Bishop_Black_C = new JLabel();
    public static JLabel Bishop_Black_F = new JLabel();
    public static JLabel King_White_E = new JLabel();
    public static JLabel King_Black_E = new JLabel();
    public static JLabel Queen_White_D = new JLabel();
    public static JLabel Queen_Black_D = new JLabel();
    
    public static JLabel turn = new JLabel();

    public static String[][] coordinates_field = new String[8][8];

    public static void coordinates(JLabel image, int line, String column){
        ArrayList<String> c = new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        int x = c.indexOf(column);
        int y = 7-(line-1);
        image.setBounds(50+(550/9)*x+(100/9), 50+(550/9)*y+(110/9), (550/15), (550/15));
    }

    public static JPanel getPanel() {return panel;}

    public PlayPanel(){

        int WIDTH = Screen.getWIDTH();
        int HEIGHT = Screen.getHEIGHT();

        //maior
        //prioridade
        //menor

        for(int j=0;j<8;j++){
            numbersLabel[j] = new JLabel();
            lettersLabel[j] = new JLabel();
            ScreenFunctions.label_setup(numbersLabel[j], ""+(j+1), false, 0, 50+(550/9)*j, 50, 550/9, panel);
            ScreenFunctions.label_edit(numbersLabel[j], new Font("Arial",Font.BOLD,16), null, Color.WHITE);
            ScreenFunctions.label_setup(lettersLabel[j], letters[j], false, 50+(550/9)*j, 0, 550/9, 50, panel);
            ScreenFunctions.label_edit(lettersLabel[j], new Font("Arial",Font.BOLD,16), null, Color.WHITE);
        }


        //primeira camada, invisivel e com mouselistener
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                transparent[i][j] = new JLabel();
                transparent[i][j].setOpaque(true);
                transparent[i][j].setBackground(new Color(255,255,255,0));
                //transparent[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                ArrayList<String> c = new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
                ScreenFunctions.drag_drop_setup(transparent[i][j], "", ""+c.get(j)+""+(i+1), 50+(550/9)*j, 50+(550/9)*i, (550/9), (550/9), Screen.myMouseListenerDragDrop, panel);
            }
        }

        final int size = (550/15);
        //segunda camada, com as peças
        ScreenFunctions.image_setup(Pawn_White_A,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_B,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_C,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_D,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_E,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_F,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_G,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_White_H,"./src/images/Pawn_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_A,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_B,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_C,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_D,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_E,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_F,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_G,"./src/images/Pawn_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Pawn_Black_H,"./src/images/Pawn_Black.png",0,0,size,size,panel);

        ScreenFunctions.image_setup(King_White_E,"./src/images/King_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Queen_White_D,"./src/images/Queen_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(King_Black_E,"./src/images/King_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Queen_Black_D,"./src/images/Queen_Black.png",0,0,size,size,panel);
        
        ScreenFunctions.image_setup(Bishop_White_C,"./src/images/Bishop_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Bishop_White_F,"./src/images/Bishop_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Bishop_Black_C,"./src/images/Bishop_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Bishop_Black_F,"./src/images/Bishop_Black.png",0,0,size,size,panel);

        ScreenFunctions.image_setup(Knight_White_B,"./src/images/Knight_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Knight_White_G,"./src/images/Knight_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Knight_Black_B,"./src/images/Knight_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Knight_Black_G,"./src/images/Knight_Black.png",0,0,size,size,panel);

        ScreenFunctions.image_setup(Rook_White_A,"./src/images/Rook_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Rook_White_H,"./src/images/Rook_White.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Rook_Black_A,"./src/images/Rook_Black.png",0,0,size,size,panel);
        ScreenFunctions.image_setup(Rook_Black_H,"./src/images/Rook_Black.png",0,0,size,size,panel);

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                coordinates_field[i][j]=" ";
            }
        }

        coordinates_field[1][0]="Pawn_White_A";
        coordinates_field[1][1]="Pawn_White_B";
        coordinates_field[1][2]="Pawn_White_C";
        coordinates_field[1][3]="Pawn_White_D";
        coordinates_field[1][4]="Pawn_White_E";
        coordinates_field[1][5]="Pawn_White_F";
        coordinates_field[1][6]="Pawn_White_G";
        coordinates_field[1][7]="Pawn_White_H";
        coordinates_field[0][0]="Rook_White_A";
        coordinates_field[0][1]="Knight_White_B";
        coordinates_field[0][2]="Bishop_White_C";
        coordinates_field[0][3]="Queen_White_D";
        coordinates_field[0][4]="King_White_E";
        coordinates_field[0][5]="Bishop_White_F";
        coordinates_field[0][6]="Knight_White_G";
        coordinates_field[0][7]="Rook_White_H";

        coordinates_field[6][0]="Pawn_Black_H";
        coordinates_field[6][1]="Pawn_Black_G";
        coordinates_field[6][2]="Pawn_Black_F";
        coordinates_field[6][3]="Pawn_Black_E";
        coordinates_field[6][4]="Pawn_Black_D";
        coordinates_field[6][5]="Pawn_Black_C";
        coordinates_field[6][6]="Pawn_Black_B";
        coordinates_field[6][7]="Pawn_Black_A";
        coordinates_field[7][0]="Rook_Black_H";
        coordinates_field[7][1]="Knight_Black_G";
        coordinates_field[7][2]="Bishop_Black_F";
        coordinates_field[7][3]="King_Black_E";
        coordinates_field[7][4]="Queen_Black_D";
        coordinates_field[7][5]="Bishop_Black_C";
        coordinates_field[7][6]="Knight_Black_B";
        coordinates_field[7][7]="Rook_Black_A";

        
        //terceira camada, com o tabuleiro
        for(int i=7;i>=0;i--){
            for(int j=0;j<8;j++){
                field[7-i][j] = new JLabel();
                if( ((7-i)*(j+1))%2==0 && (j*((7-i)+1))%2==0 ){ //brancas if( (i*(j+1))%2==0 && (j*(i+1))%2==0 ){
                    ScreenFunctions.label_setup(field[7-i][j], "", false, 50+(550/9)*i, 50+(550/9)*j, 550/9, 550/9, panel);
                }
                if( ((7-i)*(j+1))%2==1 || (j*((7-i)+1))%2==1 ){ //pretas if( (i*(j+1))%2==1 || (j*(i+1))%2==1 ){
                    ScreenFunctions.label_setup(field[7-i][j], "", false, 50+(550/9)*i, 50+(550/9)*j, 550/9, 550/9, panel);
                }
            }
        }

        ScreenFunctions.label_setup(turn, "", true, 7*WIDTH/10, 50, WIDTH/4, HEIGHT/20, panel);
        ScreenFunctions.label_edit(turn, new Font("Arial",Font.PLAIN,19), new Color(0,0,0,200), Color.WHITE);

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        ScreenFunctions.image_setup(backgroundImage, "./src/images/field.jpg", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    public static void position(boolean isPlayer1){

        //posicionar letras e números
        for(int j=0;j<8;j++){
            numbersLabel[isPlayer1?j:7-j].setText(""+(8-j));
            lettersLabel[isPlayer1?j:7-j].setText(letters[j]);
        }


        //posiciona primeira camada
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                ArrayList<String> c = new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
                transparent[i][j].setName(""+c.get(isPlayer1?j:7-j)+""+(isPlayer1?7-i+1:i+1));
            }
        }


        //posiciona peças

        coordinates(Pawn_White_A, isPlayer1?2:7, "A");
        coordinates(Pawn_White_B, isPlayer1?2:7, "B");
        coordinates(Pawn_White_C, isPlayer1?2:7, "C");
        coordinates(Pawn_White_D, isPlayer1?2:7, "D");
        coordinates(Pawn_White_E, isPlayer1?2:7, "E");
        coordinates(Pawn_White_F, isPlayer1?2:7, "F");
        coordinates(Pawn_White_G, isPlayer1?2:7, "G");
        coordinates(Pawn_White_H, isPlayer1?2:7, "H");
        coordinates(Pawn_Black_A, isPlayer1?7:2, "A");
        coordinates(Pawn_Black_B, isPlayer1?7:2, "B");
        coordinates(Pawn_Black_C, isPlayer1?7:2, "C");
        coordinates(Pawn_Black_D, isPlayer1?7:2, "D");
        coordinates(Pawn_Black_E, isPlayer1?7:2, "E");
        coordinates(Pawn_Black_F, isPlayer1?7:2, "F");
        coordinates(Pawn_Black_G, isPlayer1?7:2, "G");
        coordinates(Pawn_Black_H, isPlayer1?7:2, "H");
        
        coordinates(King_White_E, isPlayer1?1:8, "E");
        coordinates(Queen_White_D, isPlayer1?1:8, "D");
        coordinates(King_Black_E, isPlayer1?8:1, "E");
        coordinates(Queen_Black_D, isPlayer1?8:1, "D");

        coordinates(Bishop_White_C, isPlayer1?1:8, "C");
        coordinates(Bishop_White_F, isPlayer1?1:8, "F");
        coordinates(Bishop_Black_C, isPlayer1?8:1, "C");
        coordinates(Bishop_Black_F, isPlayer1?8:1, "F");

        coordinates(Knight_White_B, isPlayer1?1:8, "B");
        coordinates(Knight_White_G, isPlayer1?1:8, "G");
        coordinates(Knight_Black_B, isPlayer1?8:1, "B");
        coordinates(Knight_Black_G, isPlayer1?8:1, "G");

        coordinates(Rook_White_A, isPlayer1?1:8, "A");
        coordinates(Rook_White_H, isPlayer1?1:8, "H");
        coordinates(Rook_Black_A, isPlayer1?8:1, "A");
        coordinates(Rook_Black_H, isPlayer1?8:1, "H");


        //posiciona tabuleiro
        for(int i=7;i>=0;i--){
            for(int j=0;j<8;j++){
                if( ((7-i)*(j+1))%2==0 && (j*((7-i)+1))%2==0 ){ //brancas if( (i*(j+1))%2==0 && (j*(i+1))%2==0 ){
                    ScreenFunctions.label_edit(field[7-i][j], null, isPlayer1?Color.BLACK:Color.WHITE, null);
                }
                if( ((7-i)*(j+1))%2==1 || (j*((7-i)+1))%2==1 ){ //pretas if( (i*(j+1))%2==1 || (j*(i+1))%2==1 ){
                    ScreenFunctions.label_edit(field[7-i][j], null, isPlayer1?new Color(200,200,200):Color.BLACK, null);
                }
            }
        }

    }

}
