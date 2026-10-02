package View;

import Controller.ContasController;
import Controller.EntradasController;
import Model.Contas;
import Model.Entradas;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PainelEntradas extends JPanel {

    private EntradasController entradasController;
    private ContasController contasController;

    private JComboBox<String> comboConta;
    private JTextField campoValor;
    private JTextField campoRemetente;
    private JTextField campoMotivo;
    private JLabel labelMensagem;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private String idSelecionado;

    public PainelEntradas(EntradasController entradasController, ContasController contasController) {
        this.entradasController = entradasController;
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

        JLabel labelTitulo = new JLabel("Registar Entrada");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.AZUL_ESCURO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel painelCampos = new JPanel(new GridLayout(0, 4, 10, 10));
        painelCampos.setBackground(Cores.FUNDO_CARD);

        comboConta = new JComboBox<>();
        comboConta.addItem("Seleccione uma conta");
        for (Contas c : contasController.listarTodas()) {
            comboConta.addItem(c.getTipoConta());
        }

        campoValor = new JTextField();
        campoRemetente = new JTextField();
        campoMotivo = new JTextField();

        painelCampos.add(criarLabel("Conta"));
        painelCampos.add(comboConta);
        painelCampos.add(criarLabel("Valor (MT)"));
        painelCampos.add(campoValor);
        painelCampos.add(criarLabel("Remetente"));
        painelCampos.add(campoRemetente);
        painelCampos.add(criarLabel("Motivo"));
        painelCampos.add(campoMotivo);

        labelMensagem = new JLabel(" ");
        labelMensagem.setFont(Cores.FONTE_PEQUENA);
        labelMensagem.setForeground(Cores.ALERTA_VERMELHO);

        JButton botaoRegistar = criarBotao("Registar", Cores.ALERTA_VERDE);
        botaoRegistar.addActionListener(e -> registarEntrada());

        JButton botaoEditar = criarBotao("Editar", Cores.ALERTA_AMARELO);
        botaoEditar.addActionListener(e -> editarEntrada());

        JButton botaoRemover = criarBotao("Remover", Cores.ALERTA_VERMELHO);
        botaoRemover.addActionListener(e -> removerEntrada());

        JButton botaoLimpar = criarBotao("Limpar", Cores.TEXTO_SECUNDARIO);
        botaoLimpar.addActionListener(e -> limparCampos());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Cores.FUNDO_CARD);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoRegistar);

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

        JLabel labelTitulo = new JLabel("Historico de Entradas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.AZUL_ESCURO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] colunas = {"ID", "Conta", "Valor (MT)", "Remetente", "Motivo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
        for (Entradas e : entradasController.listarTodas()) {
            modeloTabela.addRow(new Object[]{
                e.getId(),
                e.getContaAssociada(),
                String.format("%.2f", e.getValorRecebido()),
                e.getRemetente(),
                e.getMotivo()
            });
        }
    }

    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        List<Entradas> lista = entradasController.listarTodas();
        Entradas e = lista.get(linha);
        idSelecionado = e.getId();
        selecionarConta(e.getContaAssociada());
        campoValor.setText(String.format(Locale.US, "%.2f", e.getValorRecebido()));
        campoRemetente.setText(e.getRemetente());
        campoMotivo.setText(e.getMotivo());
    }

    private void selecionarConta(String tipo) {
        for (int i = 1; i < comboConta.getItemCount(); i++) {
            if (comboConta.getItemAt(i).equalsIgnoreCase(tipo)) {
                comboConta.setSelectedIndex(i);
                return;
            }
        }
        comboConta.setSelectedIndex(0);
    }

    private Double lerValor() {
        double valor;
        try {
            valor = Double.parseDouble(campoValor.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            mostrarErro("Valor invalido.");
            return null;
        }
        if (!(valor > 0) || Double.isInfinite(valor)) {
            mostrarErro("O valor deve ser positivo.");
            return null;
        }
        return valor;
    }

    private void registarEntrada() {
        if (!validarCampos()) {
            return;
        }
        Double valor = lerValor();
        if (valor == null) {
            return;
        }
        try {
            String conta = comboConta.getSelectedItem().toString();
            String erro = entradasController.registrarEntrada(valor, conta, campoRemetente.getText().trim(), campoMotivo.getText().trim());
            if (erro == null) {
                carregarTabela();
                limparCampos();
                mostrarSucesso("Entrada registada com sucesso.");
            } else {
                mostrarErro(erro);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarErro("Erro inesperado: " + ex.getMessage());
        }
    }

    private void editarEntrada() {
        if (idSelecionado == null) {
            mostrarErro("Seleccione uma entrada na tabela.");
            return;
        }
        if (!validarCampos()) {
            return;
        }
        Double valor = lerValor();
        if (valor == null) {
            return;
        }
        try {
            String conta = comboConta.getSelectedItem().toString();
            String erro = entradasController.editarEntrada(idSelecionado, valor, conta, campoRemetente.getText().trim(), campoMotivo.getText().trim());
            if (erro == null) {
                carregarTabela();
                limparCampos();
                mostrarSucesso("Entrada editada com sucesso.");
            } else {
                mostrarErro(erro);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarErro("Erro inesperado: " + ex.getMessage());
        }
    }

    private void removerEntrada() {
        if (idSelecionado == null) {
            mostrarErro("Seleccione uma entrada na tabela.");
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover esta entrada?", "Remover", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            String erro = entradasController.removerEntrada(idSelecionado);
            if (erro == null) {
                carregarTabela();
                limparCampos();
                mostrarSucesso("Entrada removida com sucesso.");
            } else {
                mostrarErro(erro);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarErro("Erro inesperado: " + ex.getMessage());
        }
    }

    private boolean validarCampos() {
        if (comboConta.getSelectedIndex() <= 0) {
            mostrarErro("Seleccione uma conta.");
            return false;
        }
        if (campoValor.getText().trim().isEmpty()) {
            mostrarErro("Preencha o valor.");
            return false;
        }
        if (campoRemetente.getText().trim().isEmpty()) {
            mostrarErro("Preencha o remetente.");
            return false;
        }
        if (campoMotivo.getText().trim().isEmpty()) {
            mostrarErro("Preencha o motivo.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        idSelecionado = null;
        comboConta.setSelectedIndex(0);
        campoValor.setText("");
        campoRemetente.setText("");
        campoMotivo.setText("");
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
