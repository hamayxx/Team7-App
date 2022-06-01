package com.example.team7_app.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String _id;
    private String birth;
    private String gender;
    private String login;

    private String email;
    private String password_hash;
    private String token;

    public User() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(String _id, String birth, String gender, String login, String email, String password_hash, String token) {
        this._id = _id;
        this.birth = birth;
        this.gender = gender;
        this.login = login;
        this.email = email;
        this.password_hash = password_hash;
        this.token = token;
    }
}
