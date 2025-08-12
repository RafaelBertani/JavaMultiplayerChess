package screen;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Screen extends JFrame{
    
    private static final JFrame frame = new JFrame();
    private static JTextArea area = new JTextArea();

    public static void setAreaText(String text){
        area.setText(text);
    }
    public static String getAreaText(){
        return area.getText();
    }

    public Screen(){

        area.setRows(14);
        area.setColumns(30);
        area.setText("");
        area.setBackground(new Color(64,64,64));
        area.setForeground(Color.WHITE);
        area.setEditable(false);
        JPanel scrollpanel = new JPanel();        
        TitledBorder border = new TitledBorder(new EtchedBorder(), "Command history");
        border.setTitleColor(new Color(222,222,222));
        scrollpanel.setBorder(border);
        scrollpanel.setBounds(50,150,100,100);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpanel.add(scrollPane);
        scrollpanel.setBackground(new Color(74,74,114));
        frame.add(scrollpanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400,300);
        frame.setTitle("Server");
        frame.getContentPane().setBackground(new Color(74,74,114));

    }

}
