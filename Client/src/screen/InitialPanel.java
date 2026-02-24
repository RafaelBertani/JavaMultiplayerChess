package screen;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InitialPanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static final JPanel back = new JPanel();
    private static final JLabel title = new JLabel();
    private static final JButton createLogin =  new JButton();
    private static final JButton enterLogin =  new JButton();

    public static JButton getCreatelogin() {return createLogin;}
    public static JButton getEnterlogin() {return enterLogin;}

    public static JPanel getPanel() {return panel;}
    
    public InitialPanel(){

        int WIDTH = Screen.getWIDTH();
        int HEIGHT = Screen.getHEIGHT()+200;

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        ComponentCreator.labelSetup(title, Screen.bn.getString("initial.title"), false, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/10, panel);
        ComponentCreator.labelEdit(title, new Font("Arial",Font.PLAIN,21), null, Color.WHITE);

        ComponentCreator.buttonSetup(createLogin, Screen.bn.getString("initial.create"), 3*WIDTH/10, 3*HEIGHT/10, 4*WIDTH/10, HEIGHT/10, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(createLogin, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        createLogin.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        createLogin.setFocusPainted(false);
        createLogin.setContentAreaFilled(false);
        createLogin.setOpaque(true);

        ComponentCreator.buttonSetup(enterLogin, Screen.bn.getString("initial.login"), 3*WIDTH/10, 9*HEIGHT/20, 4*WIDTH/10, HEIGHT/10, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(enterLogin, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        enterLogin.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        enterLogin.setFocusPainted(false);
        enterLogin.setContentAreaFilled(false);
        enterLogin.setOpaque(true);

        ComponentCreator.panelOnPanelSetup(back, panel, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/2);
        ComponentCreator.panelEdit(back, false, new Color(0,0,0,200));

        ComponentCreator.imageSetup(backgroundImage, "./src/Images/main.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    public static void updateLanguage() {
        title.setText(Screen.bn.getString("initial.title"));
        createLogin.setText(Screen.bn.getString("initial.create"));
        enterLogin.setText(Screen.bn.getString("initial.login"));
    }

}
