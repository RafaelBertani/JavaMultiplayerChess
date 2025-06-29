package main_panels;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import screen.Screen;
import screen.ScreenFunctions;

public class MenuPanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static final JButton ranking = new JButton();
    private static final JButton play = new JButton();
    private static final JButton leave = new JButton();

    public static JButton getRanking() {return ranking;}
    public static JButton getPlay() {return play;}
    public static JButton getLeave() {return leave;}

    public static JPanel getPanel() {return panel;}

    public MenuPanel(){

        int WIDTH = Screen.getWIDTH();
        int HEIGHT = Screen.getHEIGHT();

        //maior
        //prioridade
        //menor

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        ScreenFunctions.button_setup(ranking, "Ver Ranking", 0, 11*HEIGHT/20, WIDTH/3, HEIGHT/10, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(ranking, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        ranking.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        ranking.setFocusPainted(false);
        ranking.setContentAreaFilled(false);
        ranking.setOpaque(true);
        
        ScreenFunctions.button_setup(play, "Buscar partida", WIDTH/3, 11*HEIGHT/20, WIDTH/3, HEIGHT/10, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(play, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        play.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        play.setOpaque(true);
        
        ScreenFunctions.button_setup(leave, "Sair", 2*WIDTH/3, 11*WIDTH/20, WIDTH/3, HEIGHT/10, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(leave, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        leave.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        leave.setFocusPainted(false);
        leave.setContentAreaFilled(false);
        leave.setOpaque(true);

        ScreenFunctions.image_setup(backgroundImage, "./src/images/teste.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

}
