package com.example.team7_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDTO {

    private String login;
    private String email;
    private String password;
    private String langKey;
}
