package screen;

import client.Client;
import java.awt.Color;
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

    public static JFrame mainFrame = new JFrame();
    public static JPanel mainPanel = new JPanel();
    public static JMenuBar menuBar = new JMenuBar();

    public static Client client;

    public static MyActionListener myActionListener = new MyActionListener();
    public static MyMouseListener myMouseListener = new MyMouseListener();
    public static MyMouseListenerDragDrop myMouseListenerDragDrop = new MyMouseListenerDragDrop();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static int getWIDTH() {return WIDTH;}
    public static int getHEIGHT() {return WIDTH;}
    
    public Screen(Client c){

        //maior
        //prioridade
        //menor

        //inicia todos os panels
        new InitialPanel();
        new CreatePanel();
        new LoginPanel();
        new MenuPanel();
        new RankingPanel();
        new PlayPanel();
        
        // outros paineis não são adicionados diretamente no frame principal porque
        // ao serem chaveados requisitariam frame.setVisible(false), o que faria a tela
        // sumir e reaparecer, então, é melhor apenas panel.setVisible(false)
        mainPanel.add(InitialPanel.getPanel());

        ScreenFunctions.panel_setup(mainPanel, mainFrame, 0, 0, WIDTH, HEIGHT);
        ScreenFunctions.panel_edit(mainPanel,true,Color.WHITE);
        
        ScreenFunctions.frame_setup(mainFrame, true, JFrame.EXIT_ON_CLOSE, WIDTH, HEIGHT);
        ScreenFunctions.frame_edit(mainFrame, "Jogo X - Trabalho Java 2º Semestre", null);
        
        //recebe o cliente para que a classe Interface poder interagir com a classe Client
        this.client=c;
        //começa thread de ouvir mensagem
        c.listenForMessage(this);
    }
    
}


