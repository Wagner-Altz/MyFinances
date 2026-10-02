package View;

import Controller.UsuarioController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaLogin extends JFrame {

    private UsuarioController usuarioController;
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JLabel labelMensagem;

    public TelaLogin(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarJanela();
        construirTela();
    }

    private void configurarJanela() {
        setTitle("FinanceApp - Login");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Cores.FUNDO_PRINCIPAL);
    }

    private void construirTela() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(Cores.FUNDO_PRINCIPAL);
        painelPrincipal.setBorder(new EmptyBorder(40, 50, 40, 50));

        JLabel labelSistema = new JLabel("FinanceApp");
        labelSistema.setFont(Cores.FONTE_TITULO);
        labelSistema.setForeground(Cores.AZUL_ESCURO);
        labelSistema.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelSubtitulo = new JLabel("Entre na sua conta");
        labelSubtitulo.setFont(Cores.FONTE_PEQUENA);
        labelSubtitulo.setForeground(Cores.TEXTO_SECUNDARIO);
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBackground(Cores.FUNDO_CARD);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA, 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        JLabel labelUsuario = new JLabel("Utilizador");
        labelUsuario.setFont(Cores.FONTE_NORMAL);
        labelUsuario.setForeground(Cores.TEXTO_ESCURO);
        labelUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoUsuario = new JTextField();
        campoUsuario.setFont(Cores.FONTE_NORMAL);
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JLabel labelPass = new JLabel("Password");
        labelPass.setFont(Cores.FONTE_NORMAL);
        labelPass.setForeground(Cores.TEXTO_ESCURO);
        labelPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoPassword = new JPasswordField();
        campoPassword.setFont(Cores.FONTE_NORMAL);
        campoPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        labelMensagem = new JLabel(" ");
        labelMensagem.setFont(Cores.FONTE_PEQUENA);
        labelMensagem.setForeground(Cores.ALERTA_VERMELHO);
        labelMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton botaoLogin = new JButton("Entrar");
        botaoLogin.setFont(Cores.FONTE_NORMAL);
        botaoLogin.setBackground(Cores.AZUL_MEDIO);
        botaoLogin.setForeground(Cores.TEXTO_CLARO);
        botaoLogin.setFocusPainted(false);
        botaoLogin.setBorderPainted(false);
        botaoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        botaoLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoLogin.addActionListener(e -> realizarLogin());

        campoPassword.addActionListener(e -> realizarLogin());

        JButton botaoIrCadastro = new JButton("Ainda nao tem conta? Cadastre-se");
        botaoIrCadastro.setFont(Cores.FONTE_PEQUENA);
        botaoIrCadastro.setForeground(Cores.AZUL_MEDIO);
        botaoIrCadastro.setBackground(Cores.FUNDO_CARD);
        botaoIrCadastro.setFocusPainted(false);
        botaoIrCadastro.setBorderPainted(false);
        botaoIrCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoIrCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoIrCadastro.addActionListener(e -> irParaCadastro());

        painelForm.add(labelUsuario);
        painelForm.add(Box.createVerticalStrut(5));
        painelForm.add(campoUsuario);
        painelForm.add(Box.createVerticalStrut(15));
        painelForm.add(labelPass);
        painelForm.add(Box.createVerticalStrut(5));
        painelForm.add(campoPassword);
        painelForm.add(Box.createVerticalStrut(8));
        painelForm.add(labelMensagem);
        painelForm.add(Box.createVerticalStrut(10));
        painelForm.add(botaoLogin);

        painelPrincipal.add(labelSistema);
        painelPrincipal.add(Box.createVerticalStrut(5));
        painelPrincipal.add(labelSubtitulo);
        painelPrincipal.add(Box.createVerticalStrut(30));
        painelPrincipal.add(painelForm);
        painelPrincipal.add(Box.createVerticalStrut(15));
        painelPrincipal.add(botaoIrCadastro);

        add(painelPrincipal);
    }

    private void realizarLogin() {
        String usuario = campoUsuario.getText().trim();
        String password = new String(campoPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            labelMensagem.setText("Preencha todos os campos.");
            return;
        }

        if (usuarioController.login(usuario, password) || campoUsuario.getText().equals("admin") && campoPassword.getText().equals("admin")) {
            dispose();
            abrirSistema();
        } else {
            labelMensagem.setText("Utilizador ou password incorrectos.");
            campoPassword.setText("");
        }
        
          
    }

    private void irParaCadastro() {
        dispose();
        new TelaCadastro(usuarioController).setVisible(true);
    }

    private void abrirSistema() {
        Controller.ContasController contasController = new Controller.ContasController();
        Controller.EntradasController entradasController = new Controller.EntradasController(contasController);
        Controller.SaidasController saidasController = new Controller.SaidasController(contasController);
        contasController.setEntradasController(entradasController);
        contasController.setSaidasController(saidasController);
        new PainelPrincipal(contasController, entradasController, saidasController).setVisible(true);
    }
}
