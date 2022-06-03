package vn.uit.nt213.m21.team8.file_manager.Model;

public class RegisterUserDTO {

    private String login;
    private String email;
    private String password;
    private String langKey;

    public RegisterUserDTO() {
        this.login = "";
        this.email = "";
        this.password = "";
        this.langKey = "";
    }


    public RegisterUserDTO(String login, String email, String password, String langKey) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.langKey = langKey;
    }
}
