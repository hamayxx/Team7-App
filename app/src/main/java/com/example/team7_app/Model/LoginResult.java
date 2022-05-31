package com.example.team7_app.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("token_id")
    private String token_id;

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public LoginResult(String token_id) {
        this.token_id = token_id;
    }
}
