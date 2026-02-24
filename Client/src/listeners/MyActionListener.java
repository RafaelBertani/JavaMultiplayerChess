package listeners;

import client.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import screen.ComponentCreator;
import screen.CreatePanel;
import screen.InitialPanel;
import screen.LoginPanel;
import screen.MenuPanel;
import screen.PlayPanel;
import screen.RankingPanel;
import screen.Screen;

public class MyActionListener implements ActionListener {

    /*
     * Esta classe trata das ações por botões,
     * enviando a mensagem para o servidor quando
     * for o caso 
    */

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

            if( //any blank field
                CreatePanel.getUsernametext().getText().equals("") ||
                CreatePanel.getPassword1text().getPassword().length==0 ||
                CreatePanel.getPassword2text().getPassword().length==0
            ){
                ComponentCreator.errorMessage(Screen.bn.getString("create.blank.content"), Screen.bn.getString("create.blank.title"));
            }
            else if(
                !Arrays.equals(
                    CreatePanel.getPassword1text().getPassword(),
                    CreatePanel.getPassword2text().getPassword()
                )
            ){
                ComponentCreator.errorMessage(Screen.bn.getString("create.match.content"), Screen.bn.getString("create.match.title"));
            }
            else{
                //envia
                Screen.client.sendMessage("CREATE-"+CreatePanel.getUsernametext().getText()+"-"+new String(CreatePanel.getPassword1text().getPassword()));
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
            if( //any blank field
                LoginPanel.getUsernametext().getText().equals("") ||
                LoginPanel.getPasswordtext().getPassword().length==0
            ){
                ComponentCreator.errorMessage(Screen.bn.getString("login.blank.content"), Screen.bn.getString("login.blank.title"));
            }
            else{
                //envia
                Screen.client.sendMessage("LOGIN-"+LoginPanel.getUsernametext().getText()+"-"+new String(LoginPanel.getPasswordtext().getPassword()));
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

            if(MenuPanel.getStop().isVisible()){
                //envia
                Screen.client.sendMessage("DEQUEUE- - ");
            }
            
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
            Screen.client.sendMessage("RANKING-WINS-"+(RankingPanel.selectOrder.getSelectedIndex()==0?"ASC":"DESC"));
        }
        else if(e.getSource()==RankingPanel.getGames()){ //RANKINGPANEL: GAMES
            //envia
            Screen.client.sendMessage("RANKING-GAMES-"+(RankingPanel.selectOrder.getSelectedIndex()==0?"ASC":"DESC"));
        }
        else if(e.getSource()==RankingPanel.getWinrate()){ //RANKINGPANEL: WINRATE
            //envia
            Screen.client.sendMessage("RANKING-WINRATE-"+(RankingPanel.selectOrder.getSelectedIndex()==0?"ASC":"DESC"));
        }
        else if(e.getSource()==RankingPanel.getJoined()){ //RANKINGPANEL: JOINED
            //envia
            Screen.client.sendMessage("RANKING-JOINED-"+(RankingPanel.selectOrder.getSelectedIndex()==0?"ASC":"DESC"));
        }
        else if(e.getSource()==PlayPanel.getForfeit()){ //PLAYPANEL: FORFEIT
            int op = ComponentCreator.optionsMessage(Screen.bn.getString("play.quit.content"), Screen.bn.getString("play.quit.title"), new String[]{Screen.bn.getString("play.quit.yes"),Screen.bn.getString("play.quit.no")});
            if(op==0){
                //envia
                Screen.client.sendMessage("FORFEIT-"+Client.userName+"- ");
                int option = -1;
                do{
                    option = ComponentCreator.optionsMessage(
                        Screen.bn.getString("play.lost.content"),
                        Screen.bn.getString("play.lost.title"),
                        new String[]{Screen.bn.getString("play.after.leave"),Screen.bn.getString("play.after.leavesave")}
                    );
                }while(option==-1);
                if(option==1){ ComponentCreator.saveHistory(); }
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
            Screen.lenguage.setText(Screen.bn.getString("screen.languages"));
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
            Screen.lenguage.setText(Screen.bn.getString("screen.languages"));
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
            Screen.lenguage.setText(Screen.bn.getString("screen.languages"));
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
            Screen.lenguage.setText(Screen.bn.getString("screen.languages"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();
        }
        else if(e.getSource()==Screen.menuBar.getMenu(0).getItem(4)){ //SCREEN: ITALIAN
            Screen.bn = ResourceBundle.getBundle("Resources/PACK", new Locale("it","IT"));
            Screen.mainFrame.setTitle(Screen.bn.getString("screen.title"));
            Screen.lenguage.setText(Screen.bn.getString("screen.languages"));
            InitialPanel.updateLanguage();
            CreatePanel.updateLanguage();
            LoginPanel.updateLanguage();
            MenuPanel.updateLanguage();
            PlayPanel.update_language();
            RankingPanel.updateLanguage();
        }

    }
    
}
