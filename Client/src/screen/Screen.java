package screen;

import client.Client;
import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import listeners.MyActionListener;
import listeners.MyMouseListener;
import listeners.MyMouseListenerDragDrop;
import main_panels.CreatePanel;
import main_panels.InitialPanel;
import main_panels.LoginPanel;
import main_panels.MenuPanel;
import main_panels.PlayPanel;
import main_panels.RankingPanel;

public class Screen extends JFrame{

    public static ResourceBundle bn = ResourceBundle.getBundle("Resources/PACK", new Locale("pt","BR"));
    //bn.getString("initial.title")

    public static JFrame mainFrame = new JFrame();
    public static JPanel mainPanel = new JPanel();
    public static JMenuBar menuBar = new JMenuBar();
    public static JMenu lenguage = new JMenu(Screen.bn.getString("screen.lenguages"));
    public static JMenuItem portuguese = new JMenuItem(Screen.bn.getString("screen.lenguages.portuguese"));
    public static JMenuItem english = new JMenuItem(Screen.bn.getString("screen.lenguages.english"));
    public static JMenuItem spanish = new JMenuItem(Screen.bn.getString("screen.lenguages.spanish"));
    public static JMenuItem french = new JMenuItem(Screen.bn.getString("screen.lenguages.french"));
    public static JMenuItem italian = new JMenuItem(Screen.bn.getString("screen.lenguages.italian"));

    public static Client client;

    public static MyActionListener myActionListener = new MyActionListener();
    public static MyMouseListener myMouseListener = new MyMouseListener();
    public static MyMouseListenerDragDrop myMouseListenerDragDrop = new MyMouseListenerDragDrop();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static int getWIDTH() {return WIDTH;}
    public static int getHEIGHT() {return HEIGHT;}
    
    public Screen(Client c){

        //maior
        //prioridade
        //menor

        //inicia todos os panels
        InitialPanel initialPanel = new InitialPanel();
        CreatePanel createPanel = new CreatePanel();
        LoginPanel loginPanel = new LoginPanel();
        MenuPanel menuPanel = new MenuPanel();
        RankingPanel rankingPanel = new RankingPanel();
        PlayPanel playPanel = new PlayPanel();
        
        // outros paineis não são adicionados diretamente no frame principal porque
        // ao serem chaveados requisitariam frame.setVisible(false), o que faria a tela
        // sumir e reaparecer, então, é melhor apenas panel.setVisible(false)
        mainPanel.add(InitialPanel.getPanel());

        lenguage.add(portuguese);
        lenguage.add(english);
        lenguage.add(spanish);
        lenguage.add(french);
        lenguage.add(italian);
        portuguese.addActionListener(myActionListener);
        english.addActionListener(myActionListener);
        spanish.addActionListener(myActionListener);
        french.addActionListener(myActionListener);
        italian.addActionListener(myActionListener);
        //menuBar.add(Box.createHorizontalGlue());
        menuBar.add(lenguage);
        mainFrame.setJMenuBar(menuBar);

        ScreenFunctions.panel_setup(mainPanel, mainFrame, 0, 0, WIDTH, HEIGHT);
        ScreenFunctions.panel_edit(mainPanel,true,Color.WHITE);
        
        ScreenFunctions.frame_setup(mainFrame, true, JFrame.EXIT_ON_CLOSE, WIDTH, HEIGHT);
        ScreenFunctions.frame_edit(mainFrame, Screen.bn.getString("screen.title"), null);
        
        //recebe o cliente para que a classe Interface poder interagir com a classe Client
        this.client=c;
        //começa thread de ouvir mensagem
        c.listenForMessage(this);
    }
    
}


