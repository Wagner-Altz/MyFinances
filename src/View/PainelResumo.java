package View;

import Controller.ContasController;
import Controller.EntradasController;
import Controller.SaidasController;
import Model.Contas;
import Model.Entradas;
import Model.Saidas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import java.awt.*;
//import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PainelResumo extends JPanel {

    private ContasController contasController;
    private EntradasController entradasController;
    private SaidasController saidasController;

    public PainelResumo(ContasController contasController, EntradasController entradasController, SaidasController saidasController) {
        this.contasController = contasController;
        this.entradasController = entradasController;
        this.saidasController = saidasController;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Cores.FUNDO_PRINCIPAL);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(construirTitulo());
        add(Box.createVerticalStrut(15));
        add(construirCards());
        add(Box.createVerticalStrut(15));
        add(construirTabela());
    }

    private JPanel construirTitulo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBackground(Cores.FUNDO_PRINCIPAL);

        JLabel label = new JLabel("Resumo Financeiro");
        label.setFont(Cores.FONTE_TITULO);
        label.setForeground(Cores.AZUL_ESCURO);
        painel.add(label);

        return painel;
    }

    private JPanel construirCards() {
        JPanel painel = new JPanel(new GridLayout(1, 4, 15, 0));
        painel.setBackground(Cores.FUNDO_PRINCIPAL);
        painel.setPreferredSize(new Dimension(0, 120));

        double totalSaldo = calcularSaldoTotal();
        double totalEntradas = calcularTotalEntradas();
        double totalSaidas = calcularTotalSaidas();
        double liquido = totalEntradas - totalSaidas;

        painel.add(criarCard("Saldo Total", totalSaldo, Cores.AZUL_MEDIO));
        painel.add(criarCard("Total Entradas", totalEntradas, Cores.ALERTA_VERDE));
        painel.add(criarCard("Total Saidas", totalSaidas, Cores.ALERTA_VERMELHO));
        painel.add(criarCard("Liquido", liquido, liquido >= 0 ? Cores.ALERTA_VERDE : Cores.ALERTA_VERMELHO));

        return painel;
    }

    private JPanel criarCard(String titulo, double valor, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Cores.FUNDO_CARD);
        card.setPreferredSize(new Dimension(200, 100));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(Cores.FONTE_NORMAL);
        labelTitulo.setForeground(Cores.TEXTO_SECUNDARIO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelValor = new JLabel(String.format("%.2f MT", valor));
        labelValor.setFont(Cores.FONTE_TITULO);
        labelValor.setForeground(cor);
        labelValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(labelTitulo);
        card.add(Box.createVerticalStrut(10));
        card.add(labelValor);

        return card;
    }

    private JPanel construirTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.FUNDO_CARD);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel labelTitulo = new JLabel("Contas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.AZUL_ESCURO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] colunas = {"Tipo de Conta", "Saldo Actual (MT)"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Contas c : contasController.listarTodas()) {
            modelo.addRow(new Object[]{
                c.getTipoConta(),
                String.format("%.2f", c.getSaldoConta())
            });
        }

        JTable tabela = new JTable(modelo);
        tabela.setFont(Cores.FONTE_NORMAL);
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(Cores.FONTE_NORMAL);
        tabela.getTableHeader().setBackground(Cores.AZUL_ESCURO);
        tabela.getTableHeader().setForeground(Cores.TEXTO_CLARO);
        tabela.setSelectionBackground(Cores.AZUL_SUAVE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        painel.add(labelTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private double calcularSaldoTotal() {
        double total = 0;
        for (Contas c : contasController.listarTodas()) {
            total += c.getSaldoConta();
        }
        return total;
    }

    private double calcularTotalEntradas() {
        double total = 0;
        for (Entradas e : entradasController.listarTodas()) {
            total += e.getValorRecebido();
        }
        return total;
    }

    private double calcularTotalSaidas() {
        double total = 0;
        for (Saidas s : saidasController.listarTodas()) {
            total += s.getValorRetirado();
        }
        return total;
    }
}
