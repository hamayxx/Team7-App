package com.example.team7_app.Model;

import java.io.Serializable;

public class LoginAuthenticateDTO implements Serializable {
    private String username;
    private String password;
    private boolean rememberMe;

    public LoginAuthenticateDTO(String username, String password) {
        this.username = username;
        this.password = password;
        this.rememberMe = false;
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

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
