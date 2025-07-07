package listeners;

import client.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import main_panels.CreatePanel;
import main_panels.InitialPanel;
import main_panels.LoginPanel;
import main_panels.MenuPanel;
import main_panels.PlayPanel;
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
                ScreenFunctions.error_message(Screen.bn.getString("create.blank.content"), Screen.bn.getString("create.blank.title"));
            }
            else if(
                !Arrays.equals(
                    CreatePanel.getPassword1text().getPassword(),
                    CreatePanel.getPassword2text().getPassword()
                )
            ){
                ScreenFunctions.error_message(Screen.bn.getString("create.match.content"), Screen.bn.getString("create.match.title"));
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
                ScreenFunctions.error_message(Screen.bn.getString("login.blank.content"), Screen.bn.getString("login.blank.title"));
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
        else if(e.getSource()==PlayPanel.getForfeit()){ //PLAYPANEL: FORFEIT
            int op = ScreenFunctions.options_message(Screen.bn.getString("play.quit.content"), Screen.bn.getString("play.quit.title"), new String[]{Screen.bn.getString("play.quit.yes"),Screen.bn.getString("play.quit.no")});
            if(op==0){
                //envia
                Screen.client.sendMessage("FORFEIT-"+Client.userName+"- ");
                ScreenFunctions.information_message(Screen.bn.getString("play.lost.content"),Screen.bn.getString("play.lost.title"));
                Screen.mainPanel.setVisible(false);
                Screen.mainPanel.removeAll();
                Screen.mainPanel.add(MenuPanel.getPanel());
                Screen.mainPanel.setVisible(true);
                Screen.menuBar.setVisible(true);
            }
        }
        else if(e.getSource()==Screen.menuBar.getMenu(0).getItem(0)){ //SCREEN: PORTUGUESE
            Screen.bn = ResourceBundle.getBundle("Resources/PACK", new Locale("pt","BR"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();
        }
        else if(e.getSource()==Screen.menuBar.getMenu(0).getItem(1)){ //SCREEN: ENGLISH
            Screen.bn = ResourceBundle.getBundle("Resources/PACK", Locale.US);
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();
        }
        else if(e.getSource()==Screen.menuBar.getMenu(0).getItem(2)){ //SCREEN: SPANISH
            Screen.bn = ResourceBundle.getBundle("Resources/PACK", new Locale("es","ES"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();    
        }
        else if(e.getSource()==Screen.menuBar.getMenu(0).getItem(3)){ //SCREEN: FRENCH
            Screen.bn = ResourceBundle.getBundle("Resources/PACK", new Locale("fr","FR"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();
        }
        else if(e.getSource()==Screen.menuBar.getMenu(0).getItem(4)){ //SCREEN: ITALIAN
            Screen.bn = ResourceBundle.getBundle("Resources/PACK", new Locale("fr","FR"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();
        }

    }
    
}
