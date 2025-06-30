package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import main_panels.CreatePanel;
import main_panels.InitialPanel;
import main_panels.LoginPanel;
import main_panels.MenuPanel;
import main_panels.RankingPanel;
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

            CreatePanel.getUsernametext().setText("");
            CreatePanel.getPassword1text().setText("");
            CreatePanel.getPassword2text().setText("");
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

            LoginPanel.getUsernametext().setText("");
            LoginPanel.getPasswordtext().setText("");
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
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(RankingPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        }
        else if(e.getSource()==MenuPanel.getPlay() && !MenuPanel.getLoadingLabel().isVisible()){ //MENUPANEL: PLAY
            //envia
            Screen.client.sendMessage("QUEUE- - ");
            // MenuPanel.getLoadingLabel().setVisible(true);
            // MenuPanel.getLoadingBar().setVisible(true);
            // MenuPanel.getLoadingBar().setValue(0);
            // MenuPanel.getStop().setVisible(true);
        }
        else if(e.getSource()==MenuPanel.getLeave()){ //MENUPANEL: LEAVE
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(InitialPanel.getPanel());
            Screen.mainPanel.setVisible(true);

            //envia
            Screen.client.sendMessage("LOGOUT- - ");

        }
        else if(e.getSource()==MenuPanel.getStop()){ //MENUPANEL: STOP

            //envia
            Screen.client.sendMessage("DEQUEUE- - ");
            // MenuPanel.getLoadingLabel().setVisible(false);
            // MenuPanel.getLoadingBar().setVisible(false);
            // MenuPanel.getStop().setVisible(false);

        }
        else if(e.getSource()==RankingPanel.getBack()){ //RANKINGPANEL: BACK
            Screen.mainPanel.setVisible(false);
            Screen.mainPanel.removeAll();
            Screen.mainPanel.add(MenuPanel.getPanel());
            Screen.mainPanel.setVisible(true);
        }
        else if(e.getSource()==RankingPanel.getWins()){ //RANKINGPANEL: WINS
            //envia
            Screen.client.sendMessage("RANKING-WINS- ");
        }
        else if(e.getSource()==RankingPanel.getGames()){ //RANKINGPANEL: GAMES
            //envia
            Screen.client.sendMessage("RANKING-GAMES- ");
        }
        else if(e.getSource()==RankingPanel.getWinrate()){ //RANKINGPANEL: WINRATE
            //envia
            Screen.client.sendMessage("RANKING-WINRATE- ");
        }
        else if(e.getSource()==RankingPanel.getJoined()){ //RANKINGPANEL: JOINED
            //envia
            Screen.client.sendMessage("RANKING-JOINED- ");
        }

    }
    
}
