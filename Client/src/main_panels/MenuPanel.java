package main_panels;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import screen.Screen;
import screen.ScreenFunctions;

public class MenuPanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static final JButton ranking = new JButton();
    private static final JButton play = new JButton();
    private static final JButton leave = new JButton();
    private static JProgressBar loadingBar = new JProgressBar();
    private Loading loading = new Loading();
    private static JLabel loadingLabel = new JLabel();
    private static JButton stop = new JButton();
        
    public static JButton getRanking() {return ranking;}
    public static JButton getPlay() {return play;}
    public static JButton getLeave() {return leave;}
    public static JLabel getLoadingLabel() {return loadingLabel;}
    public static JProgressBar getLoadingBar() {return loadingBar;}
    public static JButton getStop() {return stop;}

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

        ScreenFunctions.label_setup(loadingLabel, "Buscando partida", false, WIDTH/2-WIDTH/8, HEIGHT/3-HEIGHT/40, WIDTH/4, HEIGHT/40, panel);
        loadingLabel.setOpaque(true);
        ScreenFunctions.label_edit(loadingLabel, new Font("Arial",Font.PLAIN,16), new Color(255,255,255,0), Color.WHITE);
        loadingLabel.setVisible(false);

        ScreenFunctions.bar_setup(loadingBar, Color.WHITE, new Color(162,154,83), 0, 0, 100, WIDTH/2-WIDTH/8, HEIGHT/3, WIDTH/4, HEIGHT/40, panel);
        loading.start();
        loadingBar.setVisible(false);

        ScreenFunctions.button_setup(stop, "Parar de procurar", WIDTH/2-WIDTH/8, HEIGHT/3+HEIGHT/40, WIDTH/4, HEIGHT/40, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(stop, new Font("Arial", Font.PLAIN, 16), new Color(112,104,83), Color.WHITE);
        stop.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        stop.setFocusPainted(false);
        stop.setContentAreaFilled(false);
        stop.setOpaque(true);
        stop.setVisible(false);

        ScreenFunctions.image_setup(backgroundImage, "./src/images/teste.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    class Loading extends Thread {
    
        @Override
        public void run() {
            loadingBar.setValue(0);
            while(true){
                loadingBar.setValue(loadingBar.getValue()+1);
                if(loadingBar.getValue()==100){loadingBar.setValue(0);}
                try {Thread.sleep(50);} catch (InterruptedException e) {}
            }
        }
    }

}
