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

public class ComponentCreator {

    /*
     * Esta classe contém vários métodos para facilitar
     * a montagem de interfaces gráficas
    */

    public static void frameSetup(JFrame frameName, boolean setVisible, int setDefaultCloseOperation, int width, int height){
        frameName.setDefaultCloseOperation(setDefaultCloseOperation);
        frameName.setVisible(true);
        frameName.setSize(width,height);
    }

    public static void frameEdit(JFrame frameName, String title, Color back){
        frameName.setTitle(title);
        if(back!=null){frameName.getContentPane().setBackground(back);}
    }

    public static void panelSetup(JPanel panelName, JFrame frame, int x, int y, int width, int height){

        panelName.setBounds(x,y,width,height);
        panelName.setLayout(null);
        
        frame.add(panelName);
        
    }

    public static void panelOnPanelSetup(JPanel panelName, JPanel panel_of_panel, int x, int y, int width, int height){

        panelName.setBounds(x,y,width,height);
        panelName.setLayout(null);
        
        panel_of_panel.add(panelName);
        
    }

    public static void panelEdit(JPanel panelName, boolean transparent, Color back){
        if(transparent){panelName.setOpaque(false); panelName.setBackground(new Color(0,0,0,64));}
        else{panelName.setOpaque(true); panelName.setBackground(back);}
    }
    
    public static void labelSetup(JLabel label, String text_label, boolean borda, int x, int y, int width, int height, JPanel labelPanel){

        label.setText(text_label);
        label.setBounds(x, y, width, height);
        if(borda==false){label.setBorder(null);}
        else{label.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));}
        label.setHorizontalAlignment(JLabel.CENTER);
        
        labelPanel.add(label);

    }
    
    public static void labelEdit(JLabel label, Font f, Color back, Color fore){
        if(f!=null){label.setFont(f);}
        if(back!=null){label.setOpaque(true); label.setBackground(back);}
        if(fore!=null){label.setForeground(fore);}
    }

    public static void textfieldSetup(JTextField textfield, String text, int x, int y, int width, int height, boolean isEditable, boolean border, JPanel textfieldPanel){

        textfield.setBounds(x, y, width, height);
        textfield.setEditable(isEditable);
        textfield.setText(text);
        if(border==false){textfield.setBorder(null);}
        textfield.setHorizontalAlignment(JTextField.LEFT); //left

        textfieldPanel.add(textfield);

    }

    public static void textfieldEdit(JTextField textfield, Font f, Color back, Color fore){
        if(f!=null){textfield.setFont(f);}
        if(back!=null){textfield.setBackground(back);}
        if(fore!=null){textfield.setForeground(fore);}
    }

    public static void passwordfieldSetup(JPasswordField passwordfield, int x, int y, int height, int width, boolean border, JPanel passwordfieldPanel){

        passwordfield.setBounds(x, y, height, width);
        passwordfield.setEditable(true);
        passwordfield.setText("");
        if(border==false){passwordfield.setBorder(null);}
        passwordfield.setHorizontalAlignment(JTextField.LEFT);

        passwordfieldPanel.add(passwordfield);

    }
    
    public static void passwordfieldEdit(JTextField passwordfield, Font f, Color back, Color fore){
        if(f!=null){passwordfield.setFont(f);}
        if(back!=null){passwordfield.setBackground(back);}
        if(fore!=null){passwordfield.setForeground(fore);}
    }

    public static void buttonSetup(JButton button, String button_text, int x, int y, int width, int height, MyActionListener actionclass, JPanel panel_button){
        
        button.setText(button_text);
        button.addActionListener(actionclass);
        button.setBounds(x,y,width,height);

        panel_button.add(button);

    }

    public static void buttonEdit(JButton button, Font f, Color back, Color fore){
        if(f!=null){button.setFont(f);}
        if(back!=null){button.setBackground(back);}
        if(fore!=null){button.setForeground(fore);}
    }

    public static void comboboxSetup(JComboBox<String> combobox, int x, int y, int width, int height, int nOfLines, int initialItem, JPanel comboboxPanel){
    
        combobox.setMaximumRowCount(nOfLines);
        combobox.setEditable(false);
        combobox.setBounds(x,y,width,height);
        combobox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        combobox.setSelectedIndex(initialItem);
        
        //centralize
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        combobox.setRenderer(dlcr);
        
        comboboxPanel.add(combobox);
        
    }

    public static void comboboxEdit(JComboBox<String> combobox, Font f, Color back, Color fore){
        if(f!=null){combobox.setFont(f);}
        if(back!=null){combobox.setBackground(back);}
        if(fore!=null){combobox.setForeground(fore);}
    }
   
    public static void radiobuttonSetup(JRadioButton rb, String radiobuttonText, int x, int y, int width, int height, ButtonGroup bg, JPanel rbPanel){
        
        rb.setText(radiobuttonText);
        rb.setBounds(x,y,width,height);    
        
        rb.setActionCommand(rb.getText());
        
        bg.add(rb);

        rbPanel.add(rb);
    
    }

    public static void radiobuttonEdit(JRadioButton rb, Font f, Color back, Color fore){

        if(f!=null){rb.setFont(f);}
        if(back!=null){rb.setBackground(back);}
        if(fore!=null){rb.setForeground(fore);}

    }

    public static void imageSetupNoResize(JLabel label, String completePath, int x, int y, int width, int height, JPanel labelPanel) {

        label.setBounds(x, y, width, height);
        label.setIcon(new ImageIcon(completePath));

        labelPanel.add(label);

    }

    public static void imageSetup(JLabel label, String completePath, int x, int y, int width, int height, JPanel labelPanel) {
        label.setBounds(x, y, width, height);
        ImageIcon icon = new ImageIcon(completePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));

        labelPanel.add(label);
    }

    public static void dragAndDropSetup(JLabel label, String completePath, String labelTitle, int x, int y, int width, int height, MouseListener ml, JPanel labelPanel){

        label.setName(labelTitle);
        label.setBounds(x,y,width,height);
        label.setIcon(new ImageIcon(completePath));
        
        label.addMouseListener(ml);
        label.setTransferHandler(new TransferHandler("icon"));
        labelPanel.add(label);

    }

    public static void barSetup(JProgressBar bar, java.awt.Color back, java.awt.Color fore, int value, int minimun, int maximum, int x, int y, int width, int height, JPanel barPanel){
        bar.setBackground(back);
        bar.setForeground(fore);
        //bar.setOrientation(JProgressBar.CENTER);
        bar.setMinimum(minimun);
        bar.setMaximum(maximum);
        bar.setValue(value);
        bar.setBounds(x,y,width,height);
        barPanel.add(bar);
    }

    public static void checkboxSetup(JCheckBox checkbox, String text, int x, int y, int width, int height, boolean selected, JPanel checkboxPanel){
        checkbox.setText(text);
        checkbox.setBounds(x, y, width, height);
        checkbox.setSelected(selected);
        checkboxPanel.add(checkbox);
    }

    public static void checkboxEdit(JCheckBox checkbox, Font font, Color back, Color fore){
        if(font!=null){checkbox.setFont(font);}
        if(back!=null){checkbox.setBackground(back);}
        if(fore!=null){checkbox.setForeground(fore);}
    }

    public static File folderchooserSetup(String title){
        
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(title);
        j.setAcceptAllFileFilterUsed(false);
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.showOpenDialog(null);
    
        return j.getSelectedFile();
    
    }

    public static void errorMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static int optionsMessage(String message, String title, String options[]){
        return JOptionPane.showOptionDialog(null,message,title,JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,options,null);
    }

    public static void informationMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean writeFile(String pathPlusName, ArrayList<String> lines){
        try{
            FileWriter myWriter = new FileWriter(pathPlusName);
            for(String line : lines){
                myWriter.write(line);
                myWriter.write("\n");
            }
            myWriter.close();
            return true;
        }catch(IOException e){return false;}
    }

    public static void saveHistory(){
        File path;
        do{
            path = ComponentCreator.folderchooserSetup(Screen.bn.getString("play.after.directory"));
        }while(path==null);
        boolean success = ComponentCreator.writeFile(path.toString()+("/Chess_Match_"+System.currentTimeMillis())+".txt", PlayPanel.historyList);
        if(success){ComponentCreator.informationMessage(Screen.bn.getString("play.after.saved.content"),Screen.bn.getString("play.after.saved.title"));}
        else{ComponentCreator.informationMessage(Screen.bn.getString("play.after.error.content"),Screen.bn.getString("play.after.error.title"));}
    }
}
