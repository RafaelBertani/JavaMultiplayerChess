package screen;

import client.Client;
import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import listeners.MyActionListener;
import listeners.MyMouseListener;
import listeners.MyMouseListenerDragDrop;

public class Screen extends JFrame{

    /*
    * Esta classe possui o frame principal (e único)
    * e o painel principal, sobre o qual serão colocados
    * outros paineis
    */

    public static ResourceBundle bn = ResourceBundle.getBundle("Resources/PACK", new Locale("pt","BR"));
    
    public static JFrame mainFrame = new JFrame();
    public static JPanel mainPanel = new JPanel();
    public static JMenuBar menuBar = new JMenuBar();
    public static JMenu lenguage = new JMenu(Screen.bn.getString("screen.languages"));
    public static JMenuItem portuguese = new JMenuItem(Screen.bn.getString("screen.languages.portuguese"));
    public static JMenuItem english = new JMenuItem(Screen.bn.getString("screen.languages.english"));
    public static JMenuItem spanish = new JMenuItem(Screen.bn.getString("screen.languages.spanish"));
    public static JMenuItem french = new JMenuItem(Screen.bn.getString("screen.languages.french"));
    public static JMenuItem italian = new JMenuItem(Screen.bn.getString("screen.languages.italian"));

    public static Client client;

    public static MyActionListener myActionListener = new MyActionListener();
    public static MyMouseListener myMouseListener = new MyMouseListener();
    public static MyMouseListenerDragDrop myMouseListenerDragDrop = new MyMouseListenerDragDrop();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static int getWIDTH() {return WIDTH;}
    public static int getHEIGHT() {return HEIGHT;}
    
    public Screen(Client c){

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

        ComponentCreator.panelSetup(mainPanel, mainFrame, 0, 0, WIDTH, HEIGHT);
        ComponentCreator.panelEdit(mainPanel,true,Color.WHITE);
        
        ComponentCreator.frameSetup(mainFrame, true, JFrame.EXIT_ON_CLOSE, WIDTH, HEIGHT);
        ComponentCreator.frameEdit(mainFrame, Screen.bn.getString("screen.title"), null);
        
        //recebe o cliente para que a classe Interface poder interagir com a classe Client
        this.client=c;
        //começa thread de ouvir mensagem
        c.listenForMessage(this);
    }
    
}
