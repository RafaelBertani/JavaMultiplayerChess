package screen;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreatePanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static final JPanel backarea = new JPanel();
    private static final JLabel title = new JLabel();
    private static final JLabel usernameLabel = new JLabel();
    private static final JTextField usernameText = new JTextField();
    private static final JLabel password1Label = new JLabel();
    private static final JPasswordField password1Text = new JPasswordField();
    private static final JLabel password2Label = new JLabel();
    private static final JPasswordField password2Text = new JPasswordField();
    private static final JButton create = new JButton();
    private static final JButton back = new JButton();

    public static JButton getCreate() {return create;}
    public static JButton getBack() {return back;}

    public static JTextField getUsernametext() {return usernameText;}
    public static JPasswordField getPassword1text() {return password1Text;}
    public static JPasswordField getPassword2text() {return password2Text;}

    public static JPanel getPanel() {return panel;}

    public CreatePanel(){

        int WIDTH = Screen.getWIDTH();
        int HEIGHT = Screen.getHEIGHT()+200;

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        //title
        ComponentCreator.labelSetup(title, Screen.bn.getString("create.title"), false, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/10, panel);
        ComponentCreator.labelEdit(title, new Font("Arial",Font.PLAIN,21), null, Color.WHITE);

        //label username
        ComponentCreator.labelSetup(usernameLabel, Screen.bn.getString("create.username"), false, WIDTH/4, 6*HEIGHT/20, WIDTH/5, HEIGHT/20, panel);
        ComponentCreator.labelEdit(usernameLabel, new Font("Arial",Font.PLAIN,16), null, Color.WHITE);

        //text username
        ComponentCreator.textfieldSetup(usernameText, "", 9*WIDTH/20, 6*HEIGHT/20, 5*WIDTH/20, HEIGHT/20, true, true, panel);
        ComponentCreator.textfieldEdit(usernameText, new Font("Arial",Font.PLAIN,16), new Color(32,32,32), Color.WHITE);

        //label password1
        ComponentCreator.labelSetup(password1Label, Screen.bn.getString("create.password"), false, WIDTH/4, 15*HEIGHT/40, WIDTH/5, HEIGHT/20, panel);
        ComponentCreator.labelEdit(password1Label, new Font("Arial",Font.PLAIN,16), null, Color.WHITE);

        //text password1
        ComponentCreator.passwordfieldSetup(password1Text, 9*WIDTH/20, 15*HEIGHT/40, 5*WIDTH/20, HEIGHT/20, true, panel);
        ComponentCreator.passwordfieldEdit(password1Text, new Font("Arial",Font.PLAIN,16), new Color(32,32,32), Color.WHITE);

        //label password2
        ComponentCreator.labelSetup(password2Label, Screen.bn.getString("create.passwordagain"), false, WIDTH/4, 18*HEIGHT/40, WIDTH/5, HEIGHT/20, panel);
        ComponentCreator.labelEdit(password2Label, new Font("Arial",Font.PLAIN,16), null, Color.WHITE);

        //text password2
        ComponentCreator.passwordfieldSetup(password2Text, 9*WIDTH/20, 18*HEIGHT/40, 5*WIDTH/20, HEIGHT/20, true, panel);
        ComponentCreator.passwordfieldEdit(password2Text, new Font("Arial",Font.PLAIN,16), new Color(32,32,32), Color.WHITE);

        ComponentCreator.buttonSetup(back, Screen.bn.getString("create.back"), 3*WIDTH/10, 22*HEIGHT/40, 4*WIDTH/20, HEIGHT/20, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(back, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        back.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(true);

        ComponentCreator.buttonSetup(create, Screen.bn.getString("create.ok"), 5*WIDTH/10, 22*HEIGHT/40, 4*WIDTH/20, HEIGHT/20, Screen.myActionListener, panel);
        ComponentCreator.buttonEdit(create, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        create.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        create.setFocusPainted(false);
        create.setContentAreaFilled(false);
        create.setOpaque(true);

        ComponentCreator.panelOnPanelSetup(backarea, panel, WIDTH/4, 3*HEIGHT/20, WIDTH/2, HEIGHT/2);
        ComponentCreator.panelEdit(backarea, false, new Color(0,0,0,200));

        ComponentCreator.imageSetup(backgroundImage, "./src/Images/main.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    public static void updateLanguage() {
        title.setText(Screen.bn.getString("create.title"));
        usernameLabel.setText(Screen.bn.getString("create.username"));
        password1Label.setText(Screen.bn.getString("create.password"));
        password2Label.setText(Screen.bn.getString("create.passwordagain"));
        back.setText(Screen.bn.getString("create.back"));
        create.setText(Screen.bn.getString("create.ok"));
    }

}
