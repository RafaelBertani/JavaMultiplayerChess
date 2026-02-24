package screen;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

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
        int HEIGHT = Screen.getHEIGHT()+200;

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        ComponentCreator.buttonSetup(ranking, Screen.bn.getString("menu.ranking"), 0, 11*HEIGHT/20, WIDTH/3, HEIGHT/10, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(ranking, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        ranking.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        ranking.setFocusPainted(false);
        ranking.setContentAreaFilled(false);
        ranking.setOpaque(true);
        
        ComponentCreator.buttonSetup(play, Screen.bn.getString("menu.match"), WIDTH/3, 11*HEIGHT/20, WIDTH/3, HEIGHT/10, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(play, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        play.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        play.setOpaque(true);
        
        ComponentCreator.buttonSetup(leave, Screen.bn.getString("menu.leave"), 2*WIDTH/3, 11*WIDTH/20, WIDTH/3, HEIGHT/10, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(leave, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        leave.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        leave.setFocusPainted(false);
        leave.setContentAreaFilled(false);
        leave.setOpaque(true);

        ComponentCreator.labelSetup(loadingLabel, Screen.bn.getString("menu.searching"), false, WIDTH/2-WIDTH/8, HEIGHT/3-HEIGHT/40, WIDTH/4, HEIGHT/40, panel);
        loadingLabel.setOpaque(true);
        ComponentCreator.labelEdit(loadingLabel, new Font("Arial",Font.PLAIN,16), new Color(255,255,255,0), Color.WHITE);
        loadingLabel.setVisible(false);

        ComponentCreator.barSetup(loadingBar, Color.WHITE, new Color(162,154,83), 0, 0, 100, WIDTH/2-WIDTH/8, HEIGHT/3, WIDTH/4, HEIGHT/40, panel);
        loading.start();
        loadingBar.setVisible(false);

        ComponentCreator.buttonSetup(stop, Screen.bn.getString("menu.stop"), WIDTH/2-WIDTH/8, HEIGHT/3+HEIGHT/40, WIDTH/4, HEIGHT/40, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(stop, new Font("Arial", Font.PLAIN, 16), new Color(112,104,83), Color.WHITE);
        stop.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        stop.setFocusPainted(false);
        stop.setContentAreaFilled(false);
        stop.setOpaque(true);
        stop.setVisible(false);

        ComponentCreator.imageSetup(backgroundImage, "./src/Images/main.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

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

    public static void updateLanguage() {
        ranking.setText(Screen.bn.getString("menu.ranking"));
        play.setText(Screen.bn.getString("menu.match"));
        leave.setText(Screen.bn.getString("menu.leave"));
        loadingLabel.setText(Screen.bn.getString("menu.searching"));
        stop.setText(Screen.bn.getString("menu.stop"));
    }


}
