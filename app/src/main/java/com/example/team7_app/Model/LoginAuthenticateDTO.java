package com.example.team7_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthenticateDTO {
    private String username;
    private String password;
    private boolean rememberMe;
}
