package main_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import screen.Screen;
import screen.ScreenFunctions;

public class RankingPanel {
    
    private static final JPanel panel = new JPanel();
    private static final JLabel backgroundImage = new JLabel();
    private static final JButton back = new JButton();
    private static JPanel panelTABLE = new JPanel();
    private static JTable table = new JTable();
    private static ArrayList<Object[]> data = new ArrayList<>();
    private static ArrayList<String[]> userList = new ArrayList<>();
    private static final JLabel rankinglabel = new JLabel();
    private static final JLabel order = new JLabel();
    private static final ButtonGroup bg = new ButtonGroup();
    private static final JRadioButton wins = new JRadioButton();
    private static final JRadioButton games = new JRadioButton();
    private static final JRadioButton winrate = new JRadioButton();
    private static final JRadioButton joined = new JRadioButton();
    
    public static JButton getBack() {return back;}

    public static ArrayList<String[]> getItemList(){return RankingPanel.userList;}
    public static void setItemList(ArrayList<String[]> userList){RankingPanel.userList=userList;}
    public static JLabel getBackgroundImage(){return backgroundImage;}

    public static JRadioButton getWins() {return wins;}
    public static JRadioButton getGames() {return games;}
    public static JRadioButton getWinrate() {return winrate;}
    public static JRadioButton getJoined() {return joined;}

    public static JPanel getPanel() {return panel;}

    public RankingPanel(){

        int WIDTH = Screen.getWIDTH();
        int HEIGHT = Screen.getHEIGHT();

        //maior
        //prioridade
        //menor

        panel.setBounds(0,0,WIDTH,HEIGHT);
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(new Color(0,0,0));

        ScreenFunctions.label_setup(rankinglabel, "Ranking do top 100 jogadores", false, WIDTH/10, HEIGHT/40, 8*WIDTH/10, HEIGHT/10, panel);
        ScreenFunctions.label_edit(rankinglabel, new Font("Arial",Font.PLAIN,26), new Color(0,0,0,224), Color.WHITE);

        update_table();
        
        Font f = new Font("Arial",Font.PLAIN,19);
        
        ScreenFunctions.label_setup(order, "Obter os 100 melhores jogadores ordenados por:", false, WIDTH/10, 9*HEIGHT/20, 8*WIDTH/10, HEIGHT/20, panel);
        ScreenFunctions.label_edit(order, new Font("Arial",Font.PLAIN,16), new Color(0,0,0,224), Color.WHITE);
        
        ScreenFunctions.radiobutton_setup(wins, "Wins", WIDTH/10, HEIGHT/2, 2*WIDTH/10, HEIGHT/20, bg, panel);
        ScreenFunctions.radiobutton_edit(wins, f, new Color(255,255,255,0), Color.WHITE);
        wins.addActionListener(Screen.myActionListener);
        wins.setFocusPainted(false);     // Remove o foco visual
        wins.setBorderPainted(false);    // Remove borda no hover
        wins.setContentAreaFilled(false); // Remove fundo ao passar o mouse
        wins.setOpaque(false);           // Garante que o fundo seja transparente

        ScreenFunctions.radiobutton_setup(games, "Games", 3*WIDTH/10, HEIGHT/2, 2*WIDTH/10, HEIGHT/20, bg, panel);
        ScreenFunctions.radiobutton_edit(games, f, new Color(255,255,255,0), Color.WHITE);
        games.addActionListener(Screen.myActionListener);
        games.setFocusPainted(false);     // Remove o foco visual
        games.setBorderPainted(false);    // Remove borda no hover
        games.setContentAreaFilled(false); // Remove fundo ao passar o mouse
        games.setOpaque(false);           // Garante que o fundo seja transparente

        ScreenFunctions.radiobutton_setup(winrate, "Win/Rate", 5*WIDTH/10, HEIGHT/2, 2*WIDTH/10, HEIGHT/20, bg, panel);
        ScreenFunctions.radiobutton_edit(winrate, f, new Color(255,255,255,0), Color.WHITE);
        winrate.addActionListener(Screen.myActionListener);
        winrate.setFocusPainted(false);     // Remove o foco visual
        winrate.setBorderPainted(false);    // Remove borda no hover
        winrate.setContentAreaFilled(false); // Remove fundo ao passar o mouse
        winrate.setOpaque(false);           // Garante que o fundo seja transparente

        ScreenFunctions.radiobutton_setup(joined, "Date of join", 7*WIDTH/10, HEIGHT/2, 2*WIDTH/10, HEIGHT/20, bg, panel);
        ScreenFunctions.radiobutton_edit(joined, f, new Color(255,255,255,0), Color.WHITE);
        joined.addActionListener(Screen.myActionListener);
        joined.setFocusPainted(false);     // Remove o foco visual
        joined.setBorderPainted(false);    // Remove borda no hover
        joined.setContentAreaFilled(false); // Remove fundo ao passar o mouse
        joined.setOpaque(false);           // Garante que o fundo seja transparente

        ScreenFunctions.button_setup(back, "Voltar", WIDTH/3, 25*HEIGHT/40, WIDTH/3, HEIGHT/20, Screen.myActionListener, panel);
        ScreenFunctions.button_edit(back, new Font("Arial", Font.PLAIN, 19), new Color(112,104,83), Color.WHITE);
        back.addMouseListener(Screen.myMouseListener);
        //remover preenchimento automático de fundo quando clicar
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(true);
        
        ScreenFunctions.image_setup(backgroundImage, "./src/images/teste.png", 0, 0, WIDTH, 7*HEIGHT/10, panel);

    }

    public static void update_table(){

        table.removeAll();
        panelTABLE.removeAll();
        data.clear();

        String[] columnsName = {"ID","Username","Wins","Games","Win/Rate","Joined"};
        int[] columnsWidth = {50,100,100,100,100,100};

        for(String[] str : userList){
            Object item[] = {str[0],str[1],str[2],str[3],(""+(Double.parseDouble(str[2])/Double.parseDouble(str[3]))),str[4]};
            data.add(item);
        }

        DefaultTableModel modelTABLE = new DefaultTableModel(null, columnsName);
        for(int i=0;i<data.size();i++){
            modelTABLE.addRow(data.get(i));
        }
        table = new JTable(modelTABLE);

        table.setPreferredScrollableViewportSize(new Dimension(8*Screen.getWIDTH()/10, 5*Screen.getHEIGHT()/20));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        DefaultTableCellRenderer centralizer = new DefaultTableCellRenderer();
        centralizer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<columnsWidth.length;i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(columnsWidth[i]);
            table.getColumnModel().getColumn(i).setCellRenderer(centralizer);
        }

        panelTABLE.add(table);
        panelTABLE.add(new JScrollPane(table));
        panelTABLE.setBounds(Screen.getWIDTH()/10, 3*Screen.getHEIGHT()/20, (int) ((8*Screen.getWIDTH()/10)*1.03), (int) ((5*Screen.getHEIGHT()/20)*1.15));
        // panel.setOpaque(true);
        // panel.setBackground(new Color(255,255,255,0));
        panel.add(panelTABLE);
        
        table.getTableHeader().setBackground(new Color(48,48,48));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(new Color(64,64,64));
        table.setForeground(Color.WHITE);

    }

}
