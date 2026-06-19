package Controller;

import Dao.Repositorio;


public class Autenticacao {
    
    private boolean logado;
    
    public Autenticacao(){
        this.logado = false;
    }
    
    public boolean login(String userName, String password){
        if (userName.equals("Wagner") && password.equals("password") ){
            logado = true;
            return true;
        }
        return false;
    }
    
    public void logout(){
        logado = false;
    }
    
    public boolean isLogado(){
        return logado;
    }
}
