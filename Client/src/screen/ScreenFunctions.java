package screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import listeners.MyActionListener;

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

    public static void textarea_setup(JTextArea textarea_name, String texto_textarea, int x, int y, int width, int height, boolean editable, boolean borda, JPanel painel_do_textarea){

        textarea_name.setText(texto_textarea);
        textarea_name.setBounds(x, y, width, height);
        textarea_name.setEditable(editable);
        if(borda==false){textarea_name.setBorder(null);}
    
        painel_do_textarea.add(textarea_name);

    }

    public static void textarea_edit(JTextArea textarea_name, Font f, Color back, Color fore){
        if(f!=null){textarea_name.setFont(f);}
        if(back!=null){textarea_name.setBackground(back);}
        if(fore!=null){textarea_name.setForeground(fore);}
    }

    public static void scrollpane_setup(JTextArea textarea_name, JFrame scroll_frame, String scrollpane_text, int lines, int columns, int x, int y, int width, int height){
        
        textarea_name.setRows(lines);
        textarea_name.setColumns(columns);
        textarea_name.setText(scrollpane_text);
        JPanel panel_do_scroll = new JPanel();
        
        //panel.setBorder(new TitledBorder(new EtchedBorder(),"Display area"));
        panel_do_scroll.setBounds(x,y,width,height);
        //panel.setBackground(new Color(255,255,0));

        JScrollPane scrollPane = new JScrollPane(textarea_name);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel_do_scroll.add(scrollPane);

        scroll_frame.add(panel_do_scroll);

    }

    public static void scrollpane_in_separated_panel_setup(JTextArea textarea_name, JPanel scroll_panel, JFrame scroll_frame, String scrollpane_text, int lines, int columns, int x, int y, int width, int height){
        
        textarea_name.setRows(lines);
        textarea_name.setColumns(columns);
        textarea_name.setText(scrollpane_text);
        
        //panel.setBorder(new TitledBorder(new EtchedBorder(),"Display area"));
        scroll_panel.setBounds(x,y,width,height);
        //panel.setBackground(new Color(255,255,0));

        JScrollPane scrollPane = new JScrollPane(textarea_name);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_panel.add(scrollPane);

        scroll_frame.add(scroll_panel);
    }

    public static void scrollpane_edit(JTextArea nome_textarea, Font f, Color back, Color fore){
        if(f!=null){nome_textarea.setFont(f);}
        if(back!=null){nome_textarea.setBackground(back);}
        if(fore!=null){nome_textarea.setForeground(fore);}
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

        public static void table_setup(List<Object[]> object_list, JTable table, String[] columns_name, int x, int y, int width, int height, int[] columns_width, JPanel table_panel, JFrame table_frame){

        DefaultTableModel modelTABLE = new DefaultTableModel(null, columns_name);
        for(int i=0;i<object_list.size();i++){
            modelTABLE.addRow(object_list.get(i));
        }
        table = new JTable(modelTABLE);

        table.setPreferredScrollableViewportSize(new Dimension(width,height));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        for(int i=0;i<columns_width.length;i++){table.getColumnModel().getColumn(i).setPreferredWidth(columns_width[i]);}

        table_panel.add(table);
        table_panel.add(new JScrollPane(table));
        table_panel.setBounds(x,y,width+50,height+50);
        table_frame.add(table_panel);

    }

    public static void table_update(List<Object[]> object_list, JTable table, String[] columns_name, int[] columns_width, JPanel table_panel, JFrame table_frame){
        
        // DefaultTableModel modelTABLE = new DefaultTableModel(null, columns_name);
        // for(int i=0;i<lista_objetos.size();i++){
        //     modelTABLE.addRow(lista_objetos.get(i));
        // }
        // table.setModel(modelTABLE);

        table_panel.removeAll();
        table_setup(object_list,table,columns_name,table_panel.getBounds().x,table_panel.getBounds().y,table_panel.getBounds().width-50,table_panel.getBounds().height-50,columns_width,table_panel,table_frame);

    }

    public static void image_setup(JLabel label_name, String complete_path, int x, int y, int width, int height, JPanel label_panel) {
        // Define os limites do JLabel
        label_name.setBounds(x, y, width, height);

        // Carrega a imagem do caminho fornecido
        ImageIcon icon = new ImageIcon(complete_path);

        // Redimensiona a imagem para as dimensões especificadas
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Define o novo ícone redimensionado no JLabel
        label_name.setIcon(new ImageIcon(scaledImage));

        // Adiciona o JLabel ao painel
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

    public static void free_drag_dop_setup(JLabel label_name, String complete_path, String label_title, int initial_x, int initial_y, int width, int height, JPanel label_panel){

        label_name.setName(label_title);
        label_name.setBounds(initial_x,initial_y,width,height);
        label_name.setIcon(new ImageIcon(complete_path));

        MouseListener ml = new MouseListener() {

            PointerInfo a = MouseInfo.getPointerInfo();

            int initial_X = -1;
            int initial_Y = -1;
            int final_X = -1;
            int final_Y = -1;
            
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                a = MouseInfo.getPointerInfo();
                initial_X = (int) a.getLocation().getX();
                initial_Y = (int) a.getLocation().getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                a = MouseInfo.getPointerInfo();
                final_X = (int) a.getLocation().getX();
                final_Y = (int) a.getLocation().getY();
                
                if(initial_X>=label_name.getX() && initial_X<=label_name.getX()+label_name.getWidth() && initial_Y>=label_name.getY() && initial_Y-60<=label_name.getY()+label_name.getHeight()){
                    if(final_X>=label_panel.getX() && final_X<=label_panel.getX()+label_panel.getWidth() && final_Y>=label_panel.getY() && final_Y<=label_panel.getY()+label_panel.getHeight()){
                        label_name.setBounds(final_X-25, final_Y-70, label_name.getWidth(), label_name.getHeight());
                    }
                }
            
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        
        };

        label_name.addMouseListener(ml);
        label_panel.add(label_name);

    }

    public static void menubar_setup(JMenuBar nome_menubar, String[] principais, List<String[]> secundarios, List<String[]> terciario, MyActionListener actionclass, JFrame jframe_da_menubar){
        
        int contador = 0;
        int subitemEmenu = -1; //localização da linha no terceario com os subsubitens
        for(String item : principais){
            JMenu menu = new JMenu(item);
            for(String subitem : secundarios.get(contador)){    
                
                subitemEmenu = -1;
                // key1,value,value,value
                // key2,value,value,value
                for(int k=0;k<terciario.size();k++){if(terciario.get(k)[0].equals(subitem)){subitemEmenu=k;}}

                if(subitemEmenu!=-1){ //se item é menu e possui subitens
                    JMenu i = new JMenu(subitem); //item é um menu
                    for(int j=1;j<terciario.get(subitemEmenu).length;j++){ //percorre todos os subitens do menu que foi descoberto
                        JMenuItem subsubitem = new JMenuItem(terciario.get(subitemEmenu)[j]); //item do menu descoberto
                        i.add(subsubitem); //adiciona item no menu (ou em outras palavras, subitem no submenu)
                        subsubitem.addActionListener(actionclass); //actionlistener no subitem
                    }
                    menu.add(i); //menu principal recebe o menu (agora arrumado) descoberto
                }
                else{JMenuItem i = new JMenuItem(subitem);menu.add(i);i.addActionListener(actionclass);}
                subitemEmenu=-1;

            }
            nome_menubar.add(menu);
            contador++;
        }


        jframe_da_menubar.setJMenuBar(nome_menubar);

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

    public static void bar_update_color(JProgressBar bar_name, int value, double[] maximum_percentage_color, Color[] colors){
        
        if(maximum_percentage_color.length==colors.length){
            bar_name.setValue(value);
        
            double porcentagem = (
                Float.parseFloat(""+ (bar_name.getValue()-bar_name.getMinimum()) )
                /
                Float.parseFloat(""+ (bar_name.getMaximum()-bar_name.getMinimum()) )
            );
            for(int i=0;i<maximum_percentage_color.length;i++){
                if(porcentagem<=maximum_percentage_color[i]){bar_name.setForeground(colors[i]);break;}
            }
                
        }
        bar_name.setValue(value);
        
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

    public static File filechooser_setup(String title, boolean any_archive_type, boolean only_images, String archive_type){

        JFileChooser j = new JFileChooser();
        j.setDialogTitle(title);
        if(any_archive_type==false){
            j.setAcceptAllFileFilterUsed(false);

            
            FileNameExtensionFilter t;

            if(only_images){
                t = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif", "bmp");
            }
            else{
                //FileNameExtensionFilter t = new FileNameExtensionFilter("Only .txt files","txt"); //"JPEG file", "jpg", "jpeg"
                t = new FileNameExtensionFilter("Only ."+archive_type+" files",archive_type);
            }
            
            j.addChoosableFileFilter(t);

        }

        j.showOpenDialog(null);
        
        return j.getSelectedFile();

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

}