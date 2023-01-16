package com.example.mad;

public class ProfileUser {
    private String username, email, matricID, password;

    public ProfileUser(String username, String email, String matricID, String password) {
        this.username = username;
        this.email = email;
        this.matricID = matricID;
        this.password = password;
    }

    public ProfileUser() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricID() {
        return matricID;
    }

    public void setMatricID(String matricID) {
        this.matricID = matricID;
    }
}
