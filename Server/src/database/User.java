package database;

import java.util.Date;

public class User {
    
    private int id;
    private String username;
    private String password; // hash SHA-256 (64 caracteres hex)
    private int wins;
    private int games;
    private Date joined;

    public User(int id, String username, String password, int wins, int games, Date joined) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.games = games;
        this.joined = joined;
    }

    public User(String username, String password, int wins, int games, Date joined) {
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.games = games;
        this.joined = joined;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

}