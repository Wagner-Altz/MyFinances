package View;

import Controller.ContasController;
import Model.Contas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PainelContas extends JPanel {

    private ContasController contasController;

    private JTextField campoTipo;
    private JTextField campoSaldo;
    private JLabel labelMensagem;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private String tipoSelecionado;

    public PainelContas(ContasController contasController) {
        this.contasController = contasController;
        setLayout(new BorderLayout(0, 15));
        setBackground(Cores.FUNDO_PRINCIPAL);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(construirFormulario(), BorderLayout.NORTH);
        add(construirTabela(), BorderLayout.CENTER);
        carregarTabela();
    }

    private JPanel construirFormulario() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.FUNDO_CARD);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Cores.BORDA, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel labelTitulo = new JLabel("Gerir Contas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.AZUL_ESCURO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel painelCampos = new JPanel(new GridLayout(0, 4, 10, 10));
        painelCampos.setBackground(Cores.FUNDO_CARD);

        campoTipo = new JTextField();
        campoSaldo = new JTextField();

        painelCampos.add(criarLabel("Tipo de Conta")); painelCampos.add(campoTipo);
        painelCampos.add(criarLabel("Saldo Inicial (MT)")); painelCampos.add(campoSaldo);

        labelMensagem = new JLabel(" ");
        labelMensagem.setFont(Cores.FONTE_PEQUENA);
        labelMensagem.setForeground(Cores.ALERTA_VERMELHO);

        JButton botaoAdicionar = criarBotao("Adicionar", Cores.AZUL_MEDIO);
        botaoAdicionar.addActionListener(e -> adicionarConta());

        JButton botaoEditar = criarBotao("Editar", Cores.ALERTA_AMARELO);
        botaoEditar.addActionListener(e -> editarConta());

        JButton botaoRemover = criarBotao("Remover", Cores.ALERTA_VERMELHO);
        botaoRemover.addActionListener(e -> removerConta());

        JButton botaoLimpar = criarBotao("Limpar", Cores.TEXTO_SECUNDARIO);
        botaoLimpar.addActionListener(e -> limparCampos());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Cores.FUNDO_CARD);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoAdicionar);

        JPanel painelSul = new JPanel(new BorderLayout());
        painelSul.setBackground(Cores.FUNDO_CARD);
        painelSul.add(labelMensagem, BorderLayout.WEST);
        painelSul.add(painelBotoes, BorderLayout.EAST);

        painel.add(labelTitulo, BorderLayout.NORTH);
        painel.add(painelCampos, BorderLayout.CENTER);
        painel.add(painelSul, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel construirTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.FUNDO_CARD);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Cores.BORDA, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel labelTitulo = new JLabel("Lista de Contas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.AZUL_ESCURO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] colunas = {"Tipo de Conta", "Saldo Actual (MT)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(Cores.FONTE_NORMAL);
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(Cores.FONTE_NORMAL);
        tabela.getTableHeader().setBackground(Cores.AZUL_ESCURO);
        tabela.getTableHeader().setForeground(Cores.TEXTO_CLARO);
        tabela.setSelectionBackground(Cores.AZUL_SUAVE);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherFormulario());

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        painel.add(labelTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Contas c : contasController.listarTodas()) {
            modeloTabela.addRow(new Object[]{
                c.getTipoConta(),
                String.format("%.2f", c.getSaldoConta())
            });
        }
    }

    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        List<Contas> lista = contasController.listarTodas();
        Contas c = lista.get(linha);
        tipoSelecionado = c.getTipoConta();
        campoTipo.setText(c.getTipoConta());
        campoSaldo.setText(String.format("%.2f", c.getSaldoConta()));
        campoSaldo.setEditable(false);
    }

    private void adicionarConta() {
        if (!validarCampos()) {
            return;
        }
        try {
            String tipo = campoTipo.getText().trim();
            double saldo = Double.parseDouble(campoSaldo.getText().trim().replace(',', '.'));
            if (Double.isNaN(saldo) || Double.isInfinite(saldo) || saldo < 0) {
                mostrarErro("Saldo invalido.");
                return;
            }
            if (!contasController.criarContas(new Contas(tipo, saldo))) {
                mostrarErro("Ja existe uma conta com esse tipo.");
                return;
            }
            carregarTabela();
            limparCampos();
            mostrarSucesso("Conta adicionada com sucesso.");
        } catch (NumberFormatException e) {
            mostrarErro("Saldo invalido.");
        }
    }

    private void editarConta() {
        if (tipoSelecionado == null) {
            mostrarErro("Seleccione uma conta na tabela.");
            return;
        }
        String novoTipo = campoTipo.getText().trim();
        if (novoTipo.isEmpty()) {
            mostrarErro("Preencha o tipo de conta.");
            return;
        }
        Contas atual = contasController.buscaPorTipo(tipoSelecionado);
        if (atual == null) {
            mostrarErro("Conta nao encontrada.");
            return;
        }
        boolean mudouTipo = !novoTipo.equalsIgnoreCase(tipoSelecionado);
        if (mudouTipo && contasController.contaTemMovimentos(tipoSelecionado)) {
            mostrarErro("Conta com movimentos: nao pode mudar o tipo.");
            return;
        }
        if (mudouTipo && contasController.buscaPorTipo(novoTipo) != null) {
            mostrarErro("Ja existe uma conta com esse tipo.");
            return;
        }
        if (!contasController.editarContas(tipoSelecionado, new Contas(novoTipo, atual.getSaldoConta()))) {
            mostrarErro("Erro ao editar conta.");
            return;
        }
        carregarTabela();
        limparCampos();
        mostrarSucesso("Conta editada com sucesso.");
    }

    private void removerConta() {
        if (tipoSelecionado == null) {
            mostrarErro("Seleccione uma conta na tabela.");
            return;
        }
        if (contasController.contaTemMovimentos(tipoSelecionado)) {
            mostrarErro("Conta com movimentos associados.");
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover esta conta?", "Remover", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }
        if (!contasController.removerConta(tipoSelecionado)) {
            mostrarErro("Erro ao remover conta.");
            return;
        }
        carregarTabela();
        limparCampos();
        mostrarSucesso("Conta removida com sucesso.");
    }

    private boolean validarCampos() {
        if (campoTipo.getText().trim().isEmpty()) { mostrarErro("Preencha o tipo de conta."); return false; }
        if (campoSaldo.getText().trim().isEmpty()) { mostrarErro("Preencha o saldo."); return false; }
        return true;
    }

    private void limparCampos() {
        tipoSelecionado = null;
        campoTipo.setText("");
        campoSaldo.setText("");
        campoSaldo.setEditable(true);
        tabela.clearSelection();
        labelMensagem.setText(" ");
    }

    private void mostrarErro(String mensagem) {
        labelMensagem.setForeground(Cores.ALERTA_VERMELHO);
        labelMensagem.setText(mensagem);
    }

    private void mostrarSucesso(String mensagem) {
        labelMensagem.setForeground(Cores.ALERTA_VERDE);
        labelMensagem.setText(mensagem);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(Cores.FONTE_NORMAL);
        botao.setBackground(cor);
        botao.setForeground(Cores.TEXTO_CLARO);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(Cores.FONTE_NORMAL);
        label.setForeground(Cores.TEXTO_ESCURO);
        return label;
    }
}