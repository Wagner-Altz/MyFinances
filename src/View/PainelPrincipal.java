package View;

import Controller.ContasController;
import Controller.EntradasController;
import Controller.SaidasController;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PainelPrincipal extends JFrame {

    private ContasController contasController;
    private EntradasController entradasController;
    private SaidasController saidasController;
    private JPanel painelConteudo;

    public PainelPrincipal(ContasController contasController, EntradasController entradasController, SaidasController saidasController) {
        this.contasController = contasController;
        this.entradasController = entradasController;
        this.saidasController = saidasController;
        configurarJanela();
        construirTela();
    }

    private void configurarJanela() {
        setTitle("FinanceApp");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void construirTela() {
        add(construirMenu(), BorderLayout.NORTH);

        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(Cores.FUNDO_PRINCIPAL);
        add(painelConteudo, BorderLayout.CENTER);

        mostrarPainel(new PainelResumo(contasController, entradasController, saidasController));
    }

    private JPanel construirMenu() {
        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(Cores.AZUL_ESCURO);
        menu.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel labelNome = new JLabel("FinanceApp");
        labelNome.setFont(Cores.FONTE_SUBTITULO);
        labelNome.setForeground(Cores.TEXTO_CLARO);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        painelBotoes.setBackground(Cores.AZUL_ESCURO);

        painelBotoes.add(criarBotaoMenu("Resumo", () -> mostrarPainel(new PainelResumo(contasController, entradasController, saidasController))));
        painelBotoes.add(criarBotaoMenu("Contas", () -> mostrarPainel(new PainelContas(contasController))));
        painelBotoes.add(criarBotaoMenu("Entradas", () -> mostrarPainel(new PainelEntradas(entradasController, contasController))));
        painelBotoes.add(criarBotaoMenu("Saidas", () -> mostrarPainel(new PainelSaidas(saidasController, contasController))));
        painelBotoes.add(criarBotaoMenu("Historico", () -> mostrarPainel(new PainelHistorico(entradasController, saidasController))));

        menu.add(labelNome, BorderLayout.WEST);
        menu.add(painelBotoes, BorderLayout.EAST);

        return menu;
    }

    private JButton criarBotaoMenu(String texto, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setFont(Cores.FONTE_NORMAL);
        botao.setForeground(Cores.TEXTO_CLARO);
        botao.setBackground(Cores.AZUL_ESCURO);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(8, 15, 8, 15));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(Cores.AZUL_CLARO);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(Cores.AZUL_ESCURO);
            }
        });

        botao.addActionListener(e -> acao.run());
        return botao;
    }

    private void mostrarPainel(JPanel painel) {
        painelConteudo.removeAll();
        painelConteudo.add(painel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
}