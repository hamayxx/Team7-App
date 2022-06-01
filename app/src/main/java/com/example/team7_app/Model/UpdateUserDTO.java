package com.example.team7_app.Model;

public class UpdateUserDTO {
    private String login;
    private String firstName; // GENDER
    private String lastName; // birthDay
    private String password;
    private String langKey;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UpdateUserDTO(String login, String firstName, String lastName, String password, String langKey) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.langKey = langKey;
        this.password = password;
    }
}
