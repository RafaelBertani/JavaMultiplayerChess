package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import main_panels.CreatePanel;
import main_panels.InitialPanel;
import main_panels.LoginPanel;
import main_panels.MenuPanel;
import screen.Screen;
import screen.ScreenFunctions;
import sha256.Sha256;

public class MyActionListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==InitialPanel.getCreatelogin()){ //INITIALPANEL: CREATE USER
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(CreatePanel.getPanel());
            Screen.mainPanel.setVisible(true);
        }
        else if(e.getSource()==InitialPanel.getEnterlogin()){ //INITIALPANEL: LOGIN
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(LoginPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        }
        else if(e.getSource()==CreatePanel.getBack()){ //CREATEPANEL: BACK
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(InitialPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        }
        else if(e.getSource()==CreatePanel.getCreate()){ //CREATEPANEL: CREATE

            if( //any black field
                CreatePanel.getUsernametext().getText().equals("") ||
                CreatePanel.getPassword1text().getPassword().length==0 ||
                CreatePanel.getPassword2text().getPassword().length==0
            ){
                ScreenFunctions.error_message("Nenhum campo pode ficar em branco", "Erro!");
            }
            else if(
                !Arrays.equals(
                    CreatePanel.getPassword1text().getPassword(),
                    CreatePanel.getPassword2text().getPassword()
                )
            ){
                ScreenFunctions.error_message("As senhas devem ser iguais", "Erro");
            }
            else{
                //envia
                Screen.client.sendMessage("CREATE-"+CreatePanel.getUsernametext().getText()+"-"+Sha256.hash(new String(CreatePanel.getPassword1text().getPassword())));
            }

        }
        else if(e.getSource()==LoginPanel.getBack()){ //LOGINPANEL: BACK
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(InitialPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        }
        else if(e.getSource()==LoginPanel.getLogin()){ //LOGINPANEL: LOGIN
            if( //any black field
                LoginPanel.getUsernametext().getText().equals("") ||
                LoginPanel.getPasswordtext().getPassword().length==0
            ){
                ScreenFunctions.error_message("Nenhum campo pode ficar em branco", "Erro!");
            }
            else{
                //envia
                Screen.client.sendMessage("LOGIN-"+LoginPanel.getUsernametext().getText()+"-"+Sha256.hash(new String(LoginPanel.getPasswordtext().getPassword())));
            }

        }
        else if(e.getSource()==MenuPanel.getRanking()){ //MENUPANEL: RANKING

        }
        else if(e.getSource()==MenuPanel.getPlay()){ //MENUPANEL: PLAY

        }
        else if(e.getSource()==MenuPanel.getLeave()){ //MENUPANEL: LEAVE

        }

    }
    
}
