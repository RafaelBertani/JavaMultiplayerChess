package database;

import java.sql.*;

public class Database {

    public static String url = "jdbc:mysql://localhost:3306/";
    public static String databaseName = "my_db_projeto";
    public static String userName = "root";
    public static String password = "db#1post"; // "imtdb" SENHA NOS COMPUTADORES IMT

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url+databaseName, userName, password);
        } catch ( ClassNotFoundException | SQLException ErroSQL) {
            throw new RuntimeException("Erro, não foi possível conectar com o banco de dados."+ErroSQL);
        }
    }

    public static void closeConnection(Connection c){
        try {
            if(c!=null){
                c.close();
            }
        } catch (SQLException ErroSQL) {
            throw new RuntimeException("Erro, não foi possível fechar o banco de dados."+ErroSQL);
        }
    }

    public static void createDatabase() throws Exception{
        
        try {

            Connection connection = DriverManager.getConnection(url,userName,password);

            Statement statement = connection.createStatement();

            String sql = "CREATE DATABASE " + databaseName;
            try{
                statement.executeUpdate(sql);
                //statement.close();
                System.out.println("Database created");
            }catch(SQLException e){
                System.out.println("Database already exists");
            }

            String use = "USE " + databaseName;
            statement.executeUpdate(use);
            statement.close();
            System.out.println("Database selected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void createTable_Users() {
        try {
            Connection connection = getConnection();

            boolean tabelaPresente = false;
            ResultSet tablesResult;
            DatabaseMetaData meta = connection.getMetaData();
            tablesResult = meta.getTables(null, null, "%", new String[] {"TABLE"});

            while(tablesResult.next()) {
                if(tablesResult.getString("TABLE_NAME").equals("Users")) {
                    tabelaPresente = true;
                }
            }

            if(!tabelaPresente) {
                String sql = "CREATE TABLE Users (id INT AUTO_INCREMENT NOT NULL, username VARCHAR(50) NOT NULL, password CHAR(64) NOT NULL, wins INT NOT NULL, games INT NOT NULL, joined DATE NOT NULL, PRIMARY KEY (id) )";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                System.out.println("Table 'Users' created");
            }
            else{
                System.out.println("Table 'Users' already created");
            }
        } catch (SQLException e) {
            System.out.println("Table 'Users' already exists");
        }
    }
    
}