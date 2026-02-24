package listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import screen.CreatePanel;
import screen.InitialPanel;
import screen.LoginPanel;
import screen.MenuPanel;

public class MyMouseListener implements MouseListener {

    /*
     * Esta classe existe para mudar levemente a
     * cor dos botões quando o mouse clicar ou
     * passar por cima e fazer a cor voltar ao
     * padrão quando soltar o mouse ou sair
    */

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        //ArrayList contendo todos os botões principais
        ArrayList<JButton> buttonList = new ArrayList<>(){{
            add(InitialPanel.getCreatelogin());
            add(InitialPanel.getEnterlogin());
            add(CreatePanel.getCreate());
            add(CreatePanel.getBack());
            add(LoginPanel.getLogin());
            add(LoginPanel.getBack());
            add(MenuPanel.getRanking());
            add(MenuPanel.getPlay());
            add(MenuPanel.getLeave());
        }};

        if(buttonList.contains((JButton)e.getSource())){
            ((JButton) e.getSource()).setBackground(new Color(224,208,186));
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        //ArrayList contendo todos os botões principais
        ArrayList<JButton> buttonList = new ArrayList<>(){{
            add(InitialPanel.getCreatelogin());
            add(InitialPanel.getEnterlogin());
            add(CreatePanel.getCreate());
            add(CreatePanel.getBack());
            add(LoginPanel.getLogin());
            add(LoginPanel.getBack());
            add(MenuPanel.getRanking());
            add(MenuPanel.getPlay());
            add(MenuPanel.getLeave());
        }};

        if(buttonList.contains((JButton)e.getSource())){
            ((JButton) e.getSource()).setBackground(new Color(112,104,83));
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        //ArrayList contendo todos os botões principais
        ArrayList<JButton> buttonList = new ArrayList<>(){{
            add(InitialPanel.getCreatelogin());
            add(InitialPanel.getEnterlogin());
            add(CreatePanel.getCreate());
            add(CreatePanel.getBack());
            add(LoginPanel.getLogin());
            add(LoginPanel.getBack());
            add(MenuPanel.getRanking());
            add(MenuPanel.getPlay());
            add(MenuPanel.getLeave());
        }};

        if(buttonList.contains((JButton)e.getSource())){
            ((JButton) e.getSource()).setBackground(new Color(224,208,186));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

        //ArrayList contendo todos os botões principais
        ArrayList<JButton> buttonList = new ArrayList<>(){{
            add(InitialPanel.getCreatelogin());
            add(InitialPanel.getEnterlogin());
            add(CreatePanel.getCreate());
            add(CreatePanel.getBack());
            add(LoginPanel.getLogin());
            add(LoginPanel.getBack());
            add(MenuPanel.getRanking());
            add(MenuPanel.getPlay());
            add(MenuPanel.getLeave());
        }};

        if(buttonList.contains((JButton)e.getSource())){
            ((JButton) e.getSource()).setBackground(new Color(112,104,83));
        }

    }
    
}