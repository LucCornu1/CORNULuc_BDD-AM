package com.example.cornuluc_persistance_bdd_mysql;

public class User
{
    private String username;
    private String email;
    private String localite;


    // Constructor
    public User(String username, String email, String localite)
    {
        this.username = username;
        this.email = email;
        this.localite = localite;
    }

    public User()
    {
        this.username = null;
        this.email = null;
        this.localite = null;
    }


    // Getters (Assesseur)
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getLocalite() { return localite; }

    // Setters (Mutateurs)
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setLocalite(String localite) { this.localite = localite; }
}
