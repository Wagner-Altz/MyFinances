
import Controller.UsuarioController;
import View.TelaLogin;
import View.TelaCadastro;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioController usuarioController = new UsuarioController();

            if (usuarioController.existeUsuario()) {
                new TelaLogin(usuarioController).setVisible(true);
            } else {
                new TelaCadastro(usuarioController).setVisible(true);
            }
        });
    }
}
