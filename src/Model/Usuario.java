package Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;

    public Usuario(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName == null || !userName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("O nome de utilizador so pode conter letras.");
        }
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("A password deve ter no minimo 6 caracteres.");
        }
        this.password = password;
    }
}
