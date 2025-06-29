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

    // public static boolean addItem(Item i){

    //     boolean successful = true;

    //     Connection c = Database.getConnection();
    //     PreparedStatement pstm = null;
    //     ResultSet resultQUERY = null;
    //     try{ 
    //         pstm = c.prepareStatement("insert into "+ Database.tableName +" (nome, preco, marca, validade, quantidade, setor) values (?,?,?,?,?,?)");
    //         pstm.setString(1, i.getNome());
    //         pstm.setDouble(2, i.getPreco());
    //         pstm.setString(3, i.getMarca());
    //         pstm.setDate(4, i.getValidade());
    //         pstm.setInt(5, i.getQuantidade());
    //         pstm.setInt(6, i.getSetor());
    //         pstm.execute();

    //         pstm = c.prepareStatement("SELECT MAX( id ) FROM "+ Database.tableName + ";");
    //         resultQUERY = pstm.executeQuery();
    //         resultQUERY.next();

    //         //adiciona o registro dessa ação na tabela de LOGSpanel
    //         Screen.getMy_LOGSpanel().getItemList().add(new Log("INSERT", "insert into "+ Database.tableName + " (nome, preco, marca, validade quantidade, setor) values ("+i.getNome()+", "+i.getPreco()+", "+i.getMarca()+", "+i.getValidade()+", "+i.getQuantidade()+", "+i.getSetor()+")", Log.currentTimestamp()));
    //         Screen.getMy_LOGSpanel().getPanel().setVisible(false);
    //         Screen.getMy_LOGSpanel().update_table();
    //         Screen.getMy_LOGSpanel().getPanel().setVisible(true);

    //     }catch(SQLException e){
    //         e.printStackTrace();
    //         successful = false;
    //     }finally{Database.closeConnection(c);}    
    
    //     return successful;

    // }

    // public static boolean editItem(int id, String column, String newINFO){

    //     boolean successful = true;

    //     Connection c = Database.getConnection();
    //     PreparedStatement pstm = null;
    //     try {
    //         pstm = c.prepareStatement("update "+ Database.tableName +" set "+column+"='"+newINFO+"' where id="+id);
    //         pstm.execute();

    //         //adiciona o registro dessa ação na tabela de LOGSpanel
    //         Screen.getMy_LOGSpanel().getItemList().add(new Log("UPDATE", "update "+ Database.tableName +" set "+column+"='"+newINFO+"' where id="+id, Log.currentTimestamp()));
    //         Screen.getMy_LOGSpanel().getPanel().setVisible(false);
    //         Screen.getMy_LOGSpanel().update_table();
    //         Screen.getMy_LOGSpanel().getPanel().setVisible(true);
            
    //     }catch(SQLException e){
    //         e.printStackTrace();
    //         successful = false;
    //     }finally{Database.closeConnection(c);}
        
    //     return successful;

    // }

    // public static boolean removeItem(int id){

    //     boolean successful = true;

    //     Connection c = Database.getConnection();
    //     PreparedStatement pstm = null;
    //     try{ 
    //         pstm = c.prepareStatement("delete from "+ Database.tableName +" where id="+id);
    //         pstm.execute();

    //         //adiciona o registro dessa ação na tabela de LOGSpanel
    //         Screen.getMy_LOGSpanel().getItemList().add(new Log("DELETE", "delete from "+ Database.tableName +" where id="+id, Log.currentTimestamp()));
    //         Screen.getMy_LOGSpanel().getPanel().setVisible(false);
    //         Screen.getMy_LOGSpanel().update_table();
    //         Screen.getMy_LOGSpanel().getPanel().setVisible(true);

    //     }catch(SQLException e){
    //         e.printStackTrace();
    //         successful = false;
    //     }finally{Database.closeConnection(c);}

    //     return successful;
        
    // }

    // public static ArrayList<Item> returnSearch(String column, String operator, String value){
        
    //     ArrayList<Item> resultados = new ArrayList<>();
    //     Connection c = Database.getConnection();
    //     PreparedStatement pstm = null;
    //     ResultSet resultQUERY = null;
    //     try{
            
    //         //para fazer comparações com variáveis do tipo DATE e VARCHAR é necessário que estajam entre aspas simples
    //         if(column.equals("validade") || column.equals("marca")  || column.equals("nome")){
    //             value="\'"+value+"\'";
    //         }

    //         pstm = c.prepareStatement("select * from "+ Database.tableName +" where "+column+operator+value);
    //         resultQUERY=pstm.executeQuery();
    //         while(resultQUERY.next()){
    //             //indexes começam no 1, ou seja, 1=id
    //             Item novo = new Item(
    //                 resultQUERY.getString(2), //nome
    //                 resultQUERY.getDouble(3), //preço
    //                 resultQUERY.getString(4), //marca
    //                 resultQUERY.getDate(5), //validade
    //                 resultQUERY.getInt(6), //quant
    //                 resultQUERY.getInt(7) //setor
    //             );
    //             novo.setId(resultQUERY.getInt(1));
    //             resultados.add(novo);
    //         }

    //     }catch(SQLException e){
    //         e.printStackTrace();
    //     }finally{Database.closeConnection(c);}

    //     return resultados;
        
    // }

    // public static ArrayList<Item> returnTable(){
        
    //     ArrayList<Item> resultados = new ArrayList<>();
    //     Connection c = Database.getConnection();
    //     PreparedStatement pstm = null;
    //     ResultSet resultQUERY = null;
    //     try{
            
    //         pstm = c.prepareStatement("select * from "+ Database.tableName);
    //         resultQUERY=pstm.executeQuery();
    //         while(resultQUERY.next()){
    //             //indexes começam no 1, ou seja, 1=id
    //             Item novo = new Item(
    //                 resultQUERY.getString(2), //nome
    //                 resultQUERY.getDouble(3), //preço
    //                 resultQUERY.getString(4), //marca
    //                 resultQUERY.getDate(5), //validade
    //                 resultQUERY.getInt(6), //setor
    //                 resultQUERY.getInt(7) //quant

    //             );
    //             novo.setId(resultQUERY.getInt(1));
    //             resultados.add(novo);
    //         }

    //     }catch(SQLException e){
    //         e.printStackTrace();
    //     }finally{Database.closeConnection(c);}
        
    //     return resultados;
    // }

    // public static ArrayList<Item> sortByColumn(String column, boolean ASC){
        
    //     ArrayList<Item> resultados = new ArrayList<>();
    //     Connection c = Database.getConnection();
    //     PreparedStatement pstm = null;
    //     ResultSet resultQUERY = null;
    //     Item novo;
    //     try{ 
    //         if(ASC){pstm = c.prepareStatement("SELECT * FROM "+ Database.tableName +" ORDER BY "+ column +" ASC");}
    //         else{pstm = c.prepareStatement("SELECT * FROM "+ Database.tableName +" ORDER BY "+ column +" DESC");}
    //         resultQUERY=pstm.executeQuery();
    //         while(resultQUERY.next()){
    //             //indexes começam no 1, ou seja, 1=id
    //             novo = new Item(
    //                 resultQUERY.getString(2), //nome
    //                 resultQUERY.getDouble(3), //preço
    //                 resultQUERY.getString(4), //marca
    //                 resultQUERY.getDate(5), //validade
    //                 resultQUERY.getInt(6), //quant
    //                 resultQUERY.getInt(7) //setor
    //             );
    //             novo.setId(resultQUERY.getInt(1)); //id
    //             resultados.add(novo);
    //         }

    //     } catch(SQLException e){JOptionPane.showMessageDialog(null, "ERRO! Falha na conexão com o Banco de Dados", "Erro de conexão", JOptionPane.ERROR_MESSAGE);
    //     }finally{Database.closeConnection(c);}
        
    //     return resultados;
    // }

}