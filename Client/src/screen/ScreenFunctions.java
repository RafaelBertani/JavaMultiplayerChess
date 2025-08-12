package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import listeners.MyActionListener;
import main_panels.PlayPanel;

public class ScreenFunctions{

    public static void frame_setup(JFrame frame_name, boolean setVisible, int setDefaultCloseOperation, int width, int height){
        frame_name.setDefaultCloseOperation(setDefaultCloseOperation);
        frame_name.setVisible(true);
        frame_name.setSize(width,height);
    }

    public static void frame_edit(JFrame frame_name, String title, Color back){
        frame_name.setTitle(title);
        if(back!=null){frame_name.getContentPane().setBackground(back);}
    }

    public static void panel_setup(JPanel panel_name, JFrame frame, int x, int y, int width, int height){

        panel_name.setBounds(x,y,width,height);
        panel_name.setLayout(null);
        
        frame.add(panel_name);
        
    }

    public static void panel_on_panel_setup(JPanel panel_name, JPanel panel_of_panel, int x, int y, int width, int height){

        panel_name.setBounds(x,y,width,height);
        panel_name.setLayout(null);
        
        panel_of_panel.add(panel_name);
        
    }

    public static void panel_edit(JPanel panel_name, boolean transparent, Color back){
        if(transparent){panel_name.setOpaque(false); panel_name.setBackground(new Color(0,0,0,64));}
        else{panel_name.setOpaque(true); panel_name.setBackground(back);}
    }

    public static void label_setup(JLabel label_name, String text_label, boolean borda, int x, int y, int width, int height, JPanel label_panel){

        label_name.setText(text_label);
        label_name.setBounds(x, y, width, height);
        if(borda==false){label_name.setBorder(null);}
        else{label_name.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));}
        label_name.setHorizontalAlignment(JLabel.CENTER);
        
        label_panel.add(label_name);

    }
    
    public static void label_edit(JLabel label_name, Font f, Color back, Color fore){
        if(f!=null){label_name.setFont(f);}
        if(back!=null){label_name.setOpaque(true);label_name.setBackground(back);}
        if(fore!=null){label_name.setForeground(fore);}
    }

    public static void textfield_setup(JTextField textfield_name, String textfield_text, int x, int y, int width, int height, boolean editable, boolean border, JPanel textfield_panel){

        textfield_name.setBounds(x, y, width, height);
        textfield_name.setEditable(editable);
        textfield_name.setText(textfield_text);
        if(border==false){textfield_name.setBorder(null);}
        textfield_name.setHorizontalAlignment(JTextField.LEFT); //left

        textfield_panel.add(textfield_name);

    }

    public static void textfield_edit(JTextField textfield_name, Font f, Color back, Color fore){
        if(f!=null){textfield_name.setFont(f);}
        if(back!=null){textfield_name.setBackground(back);}
        if(fore!=null){textfield_name.setForeground(fore);}
    }

    public static void passwordfield_setup(JPasswordField passwordfield_name, int x, int y, int height, int width, boolean border, JPanel passwordfield_panel){

        passwordfield_name.setBounds(x, y, height, width);
        passwordfield_name.setEditable(true);
        passwordfield_name.setText("");
        if(border==false){passwordfield_name.setBorder(null);}
        passwordfield_name.setHorizontalAlignment(JTextField.LEFT); //left

        passwordfield_panel.add(passwordfield_name);

    }
    
    public static void passwordfield_edit(JTextField passwordfield_name, Font f, Color back, Color fore){
        if(f!=null){passwordfield_name.setFont(f);}
        if(back!=null){passwordfield_name.setBackground(back);}
        if(fore!=null){passwordfield_name.setForeground(fore);}
    }

    public static void button_setup(JButton button_name, String button_text, int x, int y, int width, int height, MyActionListener actionclass, JPanel panel_button){
        
        button_name.setText(button_text);
        button_name.addActionListener(actionclass);
        button_name.setBounds(x,y,width,height);

        panel_button.add(button_name);

    }

    public static void button_edit(JButton button_name, Font f, Color back, Color fore){
        if(f!=null){button_name.setFont(f);}
        if(back!=null){button_name.setBackground(back);}
        if(fore!=null){button_name.setForeground(fore);}
    }

    public static void combobox_setup(JComboBox<String> combobox_name, int x, int y, int width, int height, int n_of_lines, int initial_item, JPanel combobox_panel){
    
        combobox_name.setMaximumRowCount(n_of_lines);
        combobox_name.setEditable(false);
        combobox_name.setBounds(x,y,width,height);
        combobox_name.setAlignmentX(JComboBox.CENTER_ALIGNMENT); //center
        combobox_name.setSelectedIndex(initial_item);
        
        //centralize
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        combobox_name.setRenderer(dlcr);
        
        combobox_panel.add(combobox_name);
        
    }

    public static void combobox_edit(JComboBox<String> combobox_name, Font f, Color back, Color fore){
        if(f!=null){combobox_name.setFont(f);}
        if(back!=null){combobox_name.setBackground(back);}
        if(fore!=null){combobox_name.setForeground(fore);}
    }
   
    public static void radiobutton_setup(JRadioButton rb_name, String radio_button_text, int x, int y, int width, int height, ButtonGroup bg_name, JPanel ra_panel){
        
        rb_name.setText(radio_button_text);
        rb_name.setBounds(x,y,width,height);    
        
        rb_name.setActionCommand(rb_name.getText());
        
        bg_name.add(rb_name);

        ra_panel.add(rb_name);
    
    }

    public static void radiobutton_edit(JRadioButton nome_rb, Font f, Color back, Color fore){

        if(f!=null){nome_rb.setFont(f);}
        if(back!=null){nome_rb.setBackground(back);}
        if(fore!=null){nome_rb.setForeground(fore);}

    }

    public static void image_setup_no_resize(JLabel label_name, String complete_path, int x, int y, int width, int height, JPanel label_panel) {

        label_name.setBounds(x, y, width, height);
        label_name.setIcon(new ImageIcon(complete_path));

        label_panel.add(label_name);

    }

    public static void image_setup(JLabel label_name, String complete_path, int x, int y, int width, int height, JPanel label_panel) {
        label_name.setBounds(x, y, width, height);
        ImageIcon icon = new ImageIcon(complete_path);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label_name.setIcon(new ImageIcon(scaledImage));

        label_panel.add(label_name);
    }

    public static void drag_drop_setup(JLabel label_name, String complete_path, String label_title, int x, int y, int width, int height, MouseListener ml, JPanel label_panel){

        label_name.setName(label_title);
        label_name.setBounds(x,y,width,height);
        label_name.setIcon(new ImageIcon(complete_path));
        
        label_name.addMouseListener(ml);
        label_name.setTransferHandler(new TransferHandler("icon"));
        label_panel.add(label_name);

    }

    public static void bar_setup(JProgressBar bar_name, java.awt.Color back, java.awt.Color fore, int value, int minimun, int maximum, int x, int y, int width, int height, JPanel bar_panel){
        bar_name.setBackground(back);
        bar_name.setForeground(fore);
        //bar_name.setOrientation(JProgressBar.CENTER);
        bar_name.setMinimum(minimun);
        bar_name.setMaximum(maximum);
        bar_name.setValue(value);
        bar_name.setBounds(x,y,width,height);
        bar_panel.add(bar_name);
    }

    public static void checkbox_setup(JCheckBox checkbox_name, String text, int x, int y, int width, int height, boolean selected, JPanel checkbox_panel){
        checkbox_name.setText(text);
        checkbox_name.setBounds(x, y, width, height);
        checkbox_name.setSelected(selected);
        checkbox_panel.add(checkbox_name);
    }

    public static void checkbox_edit(JCheckBox checkbox_name, Font font, Color back, Color fore){
        if(font!=null){checkbox_name.setFont(font);}
        if(back!=null){checkbox_name.setBackground(back);}
        if(fore!=null){checkbox_name.setForeground(fore);}
    }

    public static File folderchooser_setup(String title){
        
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(title);
        j.setAcceptAllFileFilterUsed(false);
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.showOpenDialog(null);
    
        return j.getSelectedFile();
    
    }

    public static void error_message(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static int options_message(String message, String title, String options[]){
        return JOptionPane.showOptionDialog(null,message,title,JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,options,null);
    }

    public static void information_message(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean write_file(String path_plus_name, ArrayList<String> lines){
        try{
            FileWriter myWriter = new FileWriter(path_plus_name);
            for(String line : lines){
                myWriter.write(line);
                myWriter.write("\n");
            }
            myWriter.close();
            return true;
        }catch(IOException e){return false;}
    }

    public static void save_history(){
        File path;
        do{
            path = ScreenFunctions.folderchooser_setup(Screen.bn.getString("play.after.directory"));
        }while(path==null);
        boolean success = ScreenFunctions.write_file(path.toString()+("/Chess_Match_"+System.currentTimeMillis())+".txt", PlayPanel.historyList);
        if(success){ScreenFunctions.information_message(Screen.bn.getString("play.after.saved.content"),Screen.bn.getString("play.after.saved.title"));}
        else{ScreenFunctions.information_message(Screen.bn.getString("play.after.error.content"),Screen.bn.getString("play.after.error.title"));}
    }
}
