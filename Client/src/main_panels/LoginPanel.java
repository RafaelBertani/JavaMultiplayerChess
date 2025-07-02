package main_panels;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import screen.Screen;
import screen.ScreenFunctions;

public class LoginPanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static final JPanel backarea = new JPanel();
    private static final JLabel title = new JLabel();
    private static final JLabel usernameLabel = new JLabel();
    private static final JTextField usernameText = new JTextField();
    private static final JLabel passwordLabel = new JLabel();
    private static final JPasswordField passwordText = new JPasswordField();
    private static final JButton login = new JButton();
    private static final JButton back = new JButton();

    public static JButton getLogin() {return login;}
    public static JButton getBack() {return back;}

    public static JTextField getUsernametext() {return usernameText;}
    public static JPasswordField getPasswordtext() {return passwordText;}

    public static JPanel getPanel() {return panel;}

    public LoginPanel(){

        int WIDTH = Screen.getWIDTH();
        int HEIGHT = Screen.getHEIGHT()+200;

        //maior
        //prioridade
        //menor

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        //title
        ScreenFunctions.label_setup(title, Screen.bn.getString("login.title"), false, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/10, panel);
        ScreenFunctions.label_edit(title, new Font("Arial",Font.PLAIN,21), null, Color.WHITE);

        //label username
        ScreenFunctions.label_setup(usernameLabel, Screen.bn.getString("login.username"), false, WIDTH/4, 6*HEIGHT/20, WIDTH/5, HEIGHT/20, panel);
        ScreenFunctions.label_edit(usernameLabel, new Font("Arial",Font.PLAIN,16), null, Color.WHITE);

        //text username
        ScreenFunctions.textfield_setup(usernameText, "", 9*WIDTH/20, 6*HEIGHT/20, 5*WIDTH/20, HEIGHT/20, true, true, panel);
        ScreenFunctions.textfield_edit(usernameText, new Font("Arial",Font.PLAIN,16), new Color(32,32,32), Color.WHITE);

        //label password
        ScreenFunctions.label_setup(passwordLabel, Screen.bn.getString("login.password"), false, WIDTH/4, 15*HEIGHT/40, WIDTH/5, HEIGHT/20, panel);
        ScreenFunctions.label_edit(passwordLabel, new Font("Arial",Font.PLAIN,16), null, Color.WHITE);

        //text password
        ScreenFunctions.passwordfield_setup(passwordText, 9*WIDTH/20, 15*HEIGHT/40, 5*WIDTH/20, HEIGHT/20, true, panel);
        ScreenFunctions.passwordfield_edit(passwordText, new Font("Arial",Font.PLAIN,16), new Color(32,32,32), Color.WHITE);

        ScreenFunctions.button_setup(back, Screen.bn.getString("login.back"), 3*WIDTH/10, 22*HEIGHT/40, 4*WIDTH/20, HEIGHT/20, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(back, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        back.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(true);

        ScreenFunctions.button_setup(login, Screen.bn.getString("login.ok"), 5*WIDTH/10, 22*HEIGHT/40, 4*WIDTH/20, HEIGHT/20, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(login, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        login.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        login.setFocusPainted(false);
        login.setContentAreaFilled(false);
        login.setOpaque(true);

        ScreenFunctions.panel_on_panel_setup(backarea, panel, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/2);
        ScreenFunctions.panel_edit(backarea, false, new Color(0,0,0,200));

        ScreenFunctions.image_setup(backgroundImage, "./src/images/teste.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    public static void updateLanguage() {
        title.setText(Screen.bn.getString("login.title"));
        usernameLabel.setText(Screen.bn.getString("login.username"));
        passwordLabel.setText(Screen.bn.getString("login.password"));
        back.setText(Screen.bn.getString("login.back"));
        login.setText(Screen.bn.getString("login.ok"));
    }


}
