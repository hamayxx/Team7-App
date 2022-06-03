package vn.uit.nt213.m21.team8.file_manager.Model;

import java.io.Serializable;

public class LoginAuthenticateDTO implements Serializable {
    private String username;
    private String password;
    private boolean rememberMe;
    private String token;

    public LoginAuthenticateDTO(String username, String password) {
        this.username = username;
        this.password = password;
        this.rememberMe = false;
        this.token = "";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
