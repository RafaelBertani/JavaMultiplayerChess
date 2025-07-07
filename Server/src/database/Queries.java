package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {
    
    public static boolean usernameExists(String username){
        
        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{
            
            pstm = c.prepareStatement("select 1 from Users where username = ? LIMIT 1");
            pstm.setString(1, username);
            resultQUERY=pstm.executeQuery();
            
            return resultQUERY.next();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally{Database.closeConnection(c);}
        
    }

    public static boolean createUser(String username, String password){
        
        boolean successful = true;

        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{ 
            pstm = c.prepareStatement("insert into Users (username, password, wins, games, joined) values (?,?,?,?,?)");
            pstm.setString(1, username);
            pstm.setString(2, password);
            pstm.setInt(3, 0);
            pstm.setInt(4, 0);
            pstm.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            pstm.execute();

            pstm = c.prepareStatement("SELECT MAX( id ) FROM Users;");
            resultQUERY = pstm.executeQuery();
            resultQUERY.next();

        }catch(SQLException e){
            e.printStackTrace();
            successful = false;
        }finally{Database.closeConnection(c);}    
    
        return successful;
        
    }

    public static boolean loginValid(String username, String password){
        
        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{
            
            pstm = c.prepareStatement("SELECT 1 FROM Users WHERE username = ? AND password = ? LIMIT 1");
            pstm.setString(1, username);
            pstm.setString(2, password);
            resultQUERY=pstm.executeQuery();
            
            return resultQUERY.next();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally{Database.closeConnection(c);}
        
    }

    public static String getRankingWins(){
        
        String response = "";

        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{
            
            pstm = c.prepareStatement("SELECT id, username, wins, games, joined FROM Users ORDER BY wins DESC LIMIT 100;");
            resultQUERY=pstm.executeQuery();
            while(resultQUERY.next()){
                response+=
                    resultQUERY.getInt(1)+"|"
                    +resultQUERY.getString(2)+"|"
                    +resultQUERY.getInt(3)+"|"
                    +resultQUERY.getInt(4)+"|"
                    +(""+resultQUERY.getDate(5)).replace('-', '/');

                response+="*"; //quebra de item
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{Database.closeConnection(c);}
        
        return response;
    }

    public static String getRankingGames(){
        
        String response = "";

        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{
            
            pstm = c.prepareStatement("SELECT id, username, wins, games, joined FROM Users ORDER BY games DESC LIMIT 100;");
            resultQUERY=pstm.executeQuery();
            while(resultQUERY.next()){
                response+=
                    resultQUERY.getInt(1)+"|"
                    +resultQUERY.getString(2)+"|"
                    +resultQUERY.getInt(3)+"|"
                    +resultQUERY.getInt(4)+"|"
                    +(""+resultQUERY.getDate(5)).replace('-', '/');

                response+="*"; //quebra de item
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{Database.closeConnection(c);}
        
        return response;
    }

    public static String getRankingWinrate(){
        
        String response = "";

        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{
            
            pstm = c.prepareStatement("SELECT id, username, wins, games, joined, (wins * 1.0 / games) AS win_rate FROM Users ORDER BY win_rate DESC LIMIT 100;");
            resultQUERY=pstm.executeQuery();
            while(resultQUERY.next()){
                response+=
                    resultQUERY.getInt(1)+"|"
                    +resultQUERY.getString(2)+"|"
                    +resultQUERY.getInt(3)+"|"
                    +resultQUERY.getInt(4)+"|"
                    +(""+resultQUERY.getDate(5)).replace('-', '/');

                response+="*"; //quebra de item
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{Database.closeConnection(c);}
        
        return response;
    }

    public static String getRankingDate(){
        
        String response = "";

        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        ResultSet resultQUERY = null;
        try{
            
            pstm = c.prepareStatement("SELECT id, username, wins, games, joined FROM Users ORDER BY joined DESC;");
            resultQUERY=pstm.executeQuery();
            while(resultQUERY.next()){
                response+=
                    resultQUERY.getInt(1)+"|"
                    +resultQUERY.getString(2)+"|"
                    +resultQUERY.getInt(3)+"|"
                    +resultQUERY.getInt(4)+"|"
                    +(""+resultQUERY.getDate(5)).replace('-', '/');

                response+="*"; //quebra de item
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{Database.closeConnection(c);}
        
        return response;
    }

    public static void playerWon(String username){
        
        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        try{
            
            pstm = c.prepareStatement("UPDATE Users SET wins = wins + 1, games = games + 1 WHERE username = ?;");
            pstm.setString(1, username);
            pstm.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{Database.closeConnection(c);}
        
    }

    public static void playerLost(String username){

        Connection c = Database.getConnection();
        PreparedStatement pstm = null;
        try{
            
            pstm = c.prepareStatement("UPDATE Users SET games = games + 1 WHERE username = ?;");
            pstm.setString(1, username);
            pstm.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{Database.closeConnection(c);}

    }

}