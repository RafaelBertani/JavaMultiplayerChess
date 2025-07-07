package main_panels;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import screen.Screen;
import screen.ScreenFunctions;

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

        ScreenFunctions.label_setup(title, Screen.bn.getString("initial.title"), false, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/10, panel);
        ScreenFunctions.label_edit(title, new Font("Arial",Font.PLAIN,21), null, Color.WHITE);

        ScreenFunctions.button_setup(createLogin, Screen.bn.getString("initial.create"), 3*WIDTH/10, 3*HEIGHT/10, 4*WIDTH/10, HEIGHT/10, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(createLogin, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        createLogin.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        createLogin.setFocusPainted(false);
        createLogin.setContentAreaFilled(false);
        createLogin.setOpaque(true);

        ScreenFunctions.button_setup(enterLogin, Screen.bn.getString("initial.login"), 3*WIDTH/10, 9*HEIGHT/20, 4*WIDTH/10, HEIGHT/10, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(enterLogin, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        enterLogin.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        enterLogin.setFocusPainted(false);
        enterLogin.setContentAreaFilled(false);
        enterLogin.setOpaque(true);

        ScreenFunctions.panel_on_panel_setup(back, panel, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/2);
        ScreenFunctions.panel_edit(back, false, new Color(0,0,0,200));

        ScreenFunctions.image_setup(backgroundImage, "./src/images/teste.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    public static void updateLanguage() {
        title.setText(Screen.bn.getString("initial.title"));
        createLogin.setText(Screen.bn.getString("initial.create"));
        enterLogin.setText(Screen.bn.getString("initial.login"));
    }

}
