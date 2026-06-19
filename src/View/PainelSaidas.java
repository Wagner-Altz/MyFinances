package View;

import Controller.ContasController;
import Controller.SaidasController;
import Model.Contas;
import Model.Saidas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PainelSaidas extends JPanel {

    private SaidasController saidasController;
    private ContasController contasController;

    private JComboBox<String> comboConta;
    private JTextField campoValor;
    private JTextField campoMotivo;
    private JLabel labelMensagem;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private String idSelecionado;

    public PainelSaidas(SaidasController saidasController, ContasController contasController) {
        this.saidasController = saidasController;
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

        JLabel labelTitulo = new JLabel("Registar Saida");
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
        campoMotivo = new JTextField();

        painelCampos.add(criarLabel("Conta"));
        painelCampos.add(comboConta);
        painelCampos.add(criarLabel("Valor (MT)"));
        painelCampos.add(campoValor);
        painelCampos.add(criarLabel("Motivo"));
        painelCampos.add(campoMotivo);
        painelCampos.add(new JLabel());

        labelMensagem = new JLabel(" ");
        labelMensagem.setFont(Cores.FONTE_PEQUENA);
        labelMensagem.setForeground(Cores.ALERTA_VERMELHO);

        JButton botaoRegistar = criarBotao("Registar", Cores.ALERTA_VERMELHO);
        botaoRegistar.addActionListener(e -> registarSaida());

        JButton botaoEditar = criarBotao("Editar", Cores.ALERTA_AMARELO);
        botaoEditar.addActionListener(e -> editarSaida());

        JButton botaoRemover = criarBotao("Remover", Cores.TEXTO_SECUNDARIO);
        botaoRemover.addActionListener(e -> removerSaida());

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

        JLabel labelTitulo = new JLabel("Historico de Saidas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.AZUL_ESCURO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] colunas = {"ID", "Conta", "Valor (MT)", "Motivo"};
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
        for (Saidas s : saidasController.listarTodas()) {
            modeloTabela.addRow(new Object[]{
                s.getId(),
                s.getContaAssociada(),
                String.format("%.2f", s.getValorRetirado()),
                s.getMotivo()
            });
        }
    }

    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        List<Saidas> lista = saidasController.listarTodas();
        Saidas s = lista.get(linha);
        idSelecionado = s.getId();
        comboConta.setSelectedItem(s.getContaAssociada());
        campoValor.setText(String.valueOf(s.getValorRetirado()));
        campoMotivo.setText(s.getMotivo());
    }

    private void registarSaida() {
        if (!validarCampos()) {
            return;
        }
        try {
            String conta = comboConta.getSelectedItem().toString();
            double valor = Double.parseDouble(campoValor.getText().trim());
            boolean sucesso = saidasController.registarSaidas(valor, campoMotivo.getText().trim(), conta);
            if (sucesso) {
                carregarTabela();
                limparCampos();
                mostrarSucesso("Saida registada com sucesso.");
            } else {
                mostrarErro("Saldo insuficiente ou conta nao encontrada.");
            }
        } catch (NumberFormatException e) {
            mostrarErro("Valor invalido.");
        }
    }

    private void editarSaida() {
        if (idSelecionado == null) {
            mostrarErro("Seleccione uma saida na tabela.");
            return;
        }
        if (!validarCampos()) {
            return;
        }
        try {
            String conta = comboConta.getSelectedItem().toString();
            double valor = Double.parseDouble(campoValor.getText().trim());
            boolean sucesso = saidasController.editarSaidas(idSelecionado, valor, campoMotivo.getText().trim(), conta);
            if (sucesso) {
                carregarTabela();
                limparCampos();
                mostrarSucesso("Saida editada com sucesso.");
            } else {
                mostrarErro("Saldo insuficiente ou conta nao encontrada.");
            }
        } catch (NumberFormatException e) {
            mostrarErro("Valor invalido.");
        }
    }

    private void removerSaida() {
        if (idSelecionado == null) {
            mostrarErro("Seleccione uma saida na tabela.");
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover esta saida?", "Remover", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean sucesso = saidasController.removerSaidas(idSelecionado);
            if (sucesso) {
                carregarTabela();
                limparCampos();
                mostrarSucesso("Saida removida com sucesso.");
            } else {
                mostrarErro("Erro ao remover saida.");
            }
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
