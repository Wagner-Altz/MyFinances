package View;

import Controller.UsuarioController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaCadastro extends JFrame {

    private UsuarioController usuarioController;
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JPasswordField campoConfirmar;
    private JLabel labelMensagem;

    public TelaCadastro(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarJanela();
        construirTela();
    }

    private void configurarJanela() {
        setTitle("FinanceApp - Cadastro");
        setSize(420, 540);
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

        JLabel labelSubtitulo = new JLabel("Crie a sua conta");
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

        JLabel labelUsuario = new JLabel("Utilizador (somente letras)");
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

        JLabel labelPass = new JLabel("Password (minimo 6 caracteres)");
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

        JLabel labelConfirmar = new JLabel("Confirmar Password");
        labelConfirmar.setFont(Cores.FONTE_NORMAL);
        labelConfirmar.setForeground(Cores.TEXTO_ESCURO);
        labelConfirmar.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoConfirmar = new JPasswordField();
        campoConfirmar.setFont(Cores.FONTE_NORMAL);
        campoConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoConfirmar.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoConfirmar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        labelMensagem = new JLabel(" ");
        labelMensagem.setFont(Cores.FONTE_PEQUENA);
        labelMensagem.setForeground(Cores.ALERTA_VERMELHO);
        labelMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setFont(Cores.FONTE_NORMAL);
        botaoCadastrar.setBackground(Cores.AZUL_MEDIO);
        botaoCadastrar.setForeground(Cores.TEXTO_CLARO);
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.setBorderPainted(false);
        botaoCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoCadastrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        botaoCadastrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoCadastrar.addActionListener(e -> realizarCadastro());

        JButton botaoIrLogin = new JButton("Ja tem conta? Faca login");
        botaoIrLogin.setFont(Cores.FONTE_PEQUENA);
        botaoIrLogin.setForeground(Cores.AZUL_MEDIO);
        botaoIrLogin.setBackground(Cores.FUNDO_CARD);
        botaoIrLogin.setFocusPainted(false);
        botaoIrLogin.setBorderPainted(false);
        botaoIrLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoIrLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoIrLogin.addActionListener(e -> irParaLogin());

        painelForm.add(labelUsuario);
        painelForm.add(Box.createVerticalStrut(5));
        painelForm.add(campoUsuario);
        painelForm.add(Box.createVerticalStrut(15));
        painelForm.add(labelPass);
        painelForm.add(Box.createVerticalStrut(5));
        painelForm.add(campoPassword);
        painelForm.add(Box.createVerticalStrut(15));
        painelForm.add(labelConfirmar);
        painelForm.add(Box.createVerticalStrut(5));
        painelForm.add(campoConfirmar);
        painelForm.add(Box.createVerticalStrut(8));
        painelForm.add(labelMensagem);
        painelForm.add(Box.createVerticalStrut(10));
        painelForm.add(botaoCadastrar);

        painelPrincipal.add(labelSistema);
        painelPrincipal.add(Box.createVerticalStrut(5));
        painelPrincipal.add(labelSubtitulo);
        painelPrincipal.add(Box.createVerticalStrut(30));
        painelPrincipal.add(painelForm);
        painelPrincipal.add(Box.createVerticalStrut(15));
        painelPrincipal.add(botaoIrLogin);

        add(painelPrincipal);
    }

    private void realizarCadastro() {
        String usuario = campoUsuario.getText().trim();
        String password = new String(campoPassword.getPassword()).trim();
        String confirmar = new String(campoConfirmar.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            labelMensagem.setText("Preencha todos os campos.");
            return;
        }

        if (!password.equals(confirmar)) {
            labelMensagem.setText("As passwords nao coincidem.");
            return;
        }

        try {
            if (usuarioController.cadastrar(usuario, password)) {
                dispose();
                new TelaLogin(usuarioController).setVisible(true);
            } else {
                labelMensagem.setText("Ja existe um utilizador cadastrado.");
            }
        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    private void irParaLogin() {
        dispose();
        new TelaLogin(usuarioController).setVisible(true);
    }
}
