package Controller;

import Dao.Repositorio;
import Model.Usuario;

public class UsuarioController {

    private Repositorio repositorio;
    private Usuario usuario;

    public UsuarioController() {
        this.repositorio = new Repositorio();
        this.usuario = repositorio.carregarUsuario();
    }

    public boolean existeUsuario() {
        return usuario != null;
    }

    public boolean cadastrar(String userName, String password) {
        if (existeUsuario()) {
            return false;
        }
        Usuario u = new Usuario(userName, password);
        this.usuario = u;
        repositorio.salvarUsuario(u);
        return true;
    }

    public boolean login(String userName, String password) {
        if (usuario == null) {
            return false;
        }
        return usuario.getUserName().equals(userName) && usuario.getPassword().equals(password);
    }
}
