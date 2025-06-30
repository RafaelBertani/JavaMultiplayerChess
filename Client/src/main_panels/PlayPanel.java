package main_panels;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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

    public JLabel[][] transparent = new JLabel[8][8];

    public static JLabel PeaoA = new JLabel();
    public static JLabel PeaoB = new JLabel();
    public static JLabel PeaoC = new JLabel();
    public static JLabel PeaoD = new JLabel();
    public static JLabel PeaoE = new JLabel();
    public static JLabel PeaoF = new JLabel();
    public static JLabel PeaoG = new JLabel();
    public static JLabel PeaoH = new JLabel();
    public static JLabel ADV_PeaoA = new JLabel();
    public static JLabel ADV_PeaoB = new JLabel();
    public static JLabel ADV_PeaoC = new JLabel();
    public static JLabel ADV_PeaoD = new JLabel();
    public static JLabel ADV_PeaoE = new JLabel();
    public static JLabel ADV_PeaoF = new JLabel();
    public static JLabel ADV_PeaoG = new JLabel();
    public static JLabel ADV_PeaoH = new JLabel();
    public static JLabel TorreA = new JLabel();
    public static JLabel TorreH = new JLabel();
    public static JLabel ADV_TorreA = new JLabel();
    public static JLabel ADV_TorreH = new JLabel();
    public static JLabel CavaloB = new JLabel();
    public static JLabel CavaloG = new JLabel();
    public static JLabel ADV_CavaloB = new JLabel();
    public static JLabel ADV_CavaloG = new JLabel();
    public static JLabel BispoC = new JLabel();
    public static JLabel BispoF = new JLabel();
    public static JLabel ADV_BispoC = new JLabel();
    public static JLabel ADV_BispoF = new JLabel();
    public static JLabel ReiE = new JLabel();
    public static JLabel ADV_ReiD = new JLabel();
    public static JLabel RainhaD = new JLabel();
    public static JLabel ADV_RainhaE = new JLabel();
    
    public static JLabel vez = new JLabel();

    public static String[][] campo_coordenadas = new String[8][8];

    public static void coordenadas(JLabel peca, int linha, String coluna){
        ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
        int x=c.indexOf(coluna);
        int y=linha;
        peca.setBounds(50+(550/9)*x,50+(550/9)*y,(550/9),(550/9));
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
                ArrayList<String> c=new ArrayList<String>(){{this.add("A");this.add("B");this.add("C");this.add("D");this.add("E");this.add("F");this.add("G");this.add("H");}};
                ScreenFunctions.drag_drop_setup(transparent[i][j], "", ""+c.get(j)+""+(i+1), 50+(550/9)*j, 50+(550/9)*i, (550/9), (550/9), Screen.myMouseListenerDragDrop, panel);
            }
        }


        //segunda camada, com as peças
        ScreenFunctions.image_setup_no_resize(PeaoA,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoA,1,"A");
        ScreenFunctions.image_setup_no_resize(PeaoB,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoB,1,"B");
        ScreenFunctions.image_setup_no_resize(PeaoC,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoC,1,"C");
        ScreenFunctions.image_setup_no_resize(PeaoD,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoD,1,"D");
        ScreenFunctions.image_setup_no_resize(PeaoE,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoE,1,"E");
        ScreenFunctions.image_setup_no_resize(PeaoF,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoF,1,"F");
        ScreenFunctions.image_setup_no_resize(PeaoG,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoG,1,"G");
        ScreenFunctions.image_setup_no_resize(PeaoH,"./src/peças/Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(PeaoH,1,"H");

        ScreenFunctions.image_setup_no_resize(ADV_PeaoA,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoA,6,"A");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoB,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoB,6,"B");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoC,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoC,6,"C");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoD,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoD,6,"D");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoE,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoE,6,"E");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoF,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoF,6,"F");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoG,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoG,6,"G");
        ScreenFunctions.image_setup_no_resize(ADV_PeaoH,"./src/peças/ADV_Peão.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_PeaoH,6,"H");

        ScreenFunctions.image_setup_no_resize(ReiE,"./src/peças/Rei.png",0,0,(550/9),(550/9),panel);
        coordenadas(ReiE,0,"E");
        ScreenFunctions.image_setup_no_resize(RainhaD,"./src/peças/Rainha.png",0,0,(550/9),(550/9),panel);
        coordenadas(RainhaD,0,"D");

        ScreenFunctions.image_setup_no_resize(ADV_ReiD,"./src/peças/ADV_Rei.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_ReiD,7,"D");
        ScreenFunctions.image_setup_no_resize(ADV_RainhaE,"./src/peças/ADV_Rainha.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_RainhaE,7,"E");

        ScreenFunctions.image_setup_no_resize(BispoC,"./src/peças/Bispo.png",0,0,(550/9),(550/9),panel);
        coordenadas(BispoC,0,"C");
        ScreenFunctions.image_setup_no_resize(BispoF,"./src/peças/Bispo.png",0,0,(550/9),(550/9),panel);
        coordenadas(BispoF,0,"F");

        ScreenFunctions.image_setup_no_resize(ADV_BispoC,"./src/peças/ADV_Bispo.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_BispoC,7,"C");
        ScreenFunctions.image_setup_no_resize(ADV_BispoF,"./src/peças/ADV_Bispo.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_BispoF,7,"F");

        ScreenFunctions.image_setup_no_resize(CavaloB,"./src/peças/Cavalo.png",0,0,(550/9),(550/9),panel);
        coordenadas(CavaloB,0,"B");
        ScreenFunctions.image_setup_no_resize(CavaloG,"./src/peças/Cavalo.png",0,0,(550/9),(550/9),panel);
        coordenadas(CavaloG,0,"G");

        ScreenFunctions.image_setup_no_resize(ADV_CavaloB,"./src/peças/ADV_Cavalo.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_CavaloB,7,"B");
        ScreenFunctions.image_setup_no_resize(ADV_CavaloG,"./src/peças/ADV_Cavalo.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_CavaloG,7,"G");

        ScreenFunctions.image_setup_no_resize(TorreA,"./src/peças/Torre.png",0,0,(550/9),(550/9),panel);
        coordenadas(TorreA,0,"A");
        ScreenFunctions.image_setup_no_resize(TorreH,"./src/peças/Torre.png",0,0,(550/9),(550/9),panel);
        coordenadas(TorreH,0,"H");

        ScreenFunctions.image_setup_no_resize(ADV_TorreA,"./src/peças/ADV_Torre.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_TorreA,7,"A");
        ScreenFunctions.image_setup_no_resize(ADV_TorreH,"./src/peças/ADV_Torre.png",0,0,(550/9),(550/9),panel);
        coordenadas(ADV_TorreH,7,"H");

        
        //terceira camada, com o tabuleiro
        for(int i=7;i>=0;i--){
            for(int j=0;j<8;j++){
                if( ((7-i)*(j+1))%2==0 && (j*((7-i)+1))%2==0 ){ //brancas if( (i*(j+1))%2==0 && (j*(i+1))%2==0 ){
                    field[7-i][j] = new JLabel();
                    ScreenFunctions.label_setup(field[7-i][j], "", false, 50+(550/9)*i, 50+(550/9)*j, 550/9, 550/9, panel);
                    ScreenFunctions.label_edit(field[7-i][j], null, Color.BLACK, null);
                }
                if( ((7-i)*(j+1))%2==1 || (j*((7-i)+1))%2==1 ){ //pretas if( (i*(j+1))%2==1 || (j*(i+1))%2==1 ){
                    field[7-i][j] = new JLabel();
                    ScreenFunctions.label_setup(field[7-i][j], "", false, 50+(550/9)*i, 50+(550/9)*j, 550/9, 550/9, panel);
                    ScreenFunctions.label_edit(field[7-i][j], null, new Color(200,200,200), null);
                }
            }
        }

                campo_coordenadas[1][0]="PeãoA";
        campo_coordenadas[1][1]="PeãoB";
        campo_coordenadas[1][2]="PeãoC";
        campo_coordenadas[1][3]="PeãoD";
        campo_coordenadas[1][4]="PeãoE";
        campo_coordenadas[1][5]="PeãoF";
        campo_coordenadas[1][6]="PeãoG";
        campo_coordenadas[1][7]="PeãoH";
        campo_coordenadas[0][0]="TorreA";
        campo_coordenadas[0][1]="CavaloB";
        campo_coordenadas[0][2]="BispoC";
        campo_coordenadas[0][3]="RainhaD";
        campo_coordenadas[0][4]="ReiE";
        campo_coordenadas[0][5]="BispoF";
        campo_coordenadas[0][6]="CavaloG";
        campo_coordenadas[0][7]="TorreH";

        campo_coordenadas[6][0]="ADV_PeãoA";
        campo_coordenadas[6][1]="ADV_PeãoB";
        campo_coordenadas[6][2]="ADV_PeãoC";
        campo_coordenadas[6][3]="ADV_PeãoD";
        campo_coordenadas[6][4]="ADV_PeãoE";
        campo_coordenadas[6][5]="ADV_PeãoF";
        campo_coordenadas[6][6]="ADV_PeãoG";
        campo_coordenadas[6][7]="ADV_PeãoH";
        campo_coordenadas[7][0]="ADV_TorreA";
        campo_coordenadas[7][1]="ADV_CavaloB";
        campo_coordenadas[7][2]="ADV_BispoC";
        campo_coordenadas[7][3]="ADV_RainhaE";
        campo_coordenadas[7][4]="ADV_ReiD";
        campo_coordenadas[7][5]="ADV_BispoF";
        campo_coordenadas[7][6]="ADV_CavaloG";
        campo_coordenadas[7][7]="ADV_TorreH";

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        ScreenFunctions.image_setup(backgroundImage, "./src/images/field.jpg", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

}
