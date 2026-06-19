package View;

import Controller.EntradasController;
import Controller.SaidasController;
import Model.Entradas;
import Model.Saidas;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PainelHistorico extends JPanel {

    private EntradasController entradasController;
    private SaidasController saidasController;

    public PainelHistorico(EntradasController entradasController, SaidasController saidasController) {
        this.entradasController = entradasController;
        this.saidasController = saidasController;
        setLayout(new GridLayout(2, 1, 0, 15));
        setBackground(Cores.FUNDO_PRINCIPAL);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(construirTabelaEntradas());
        add(construirTabelaSaidas());
    }

    private JPanel construirTabelaEntradas() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.FUNDO_CARD);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Cores.BORDA, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel labelTitulo = new JLabel("Historico de Entradas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.ALERTA_VERDE);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] colunas = {"ID", "Conta", "Valor (MT)", "Remetente", "Motivo"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        double total = 0;
        for (Entradas e : entradasController.listarTodas()) {
            modelo.addRow(new Object[]{
                e.getId(),
                e.getContaAssociada(),
                String.format("%.2f", e.getValorRecebido()),
                e.getRemetente(),
                e.getMotivo()
            });
            total += e.getValorRecebido();
        }

        JTable tabela = new JTable(modelo);
        tabela.setFont(Cores.FONTE_NORMAL);
        tabela.setRowHeight(28);
        tabela.getTableHeader().setFont(Cores.FONTE_NORMAL);
        tabela.getTableHeader().setBackground(Cores.AZUL_ESCURO);
        tabela.getTableHeader().setForeground(Cores.TEXTO_CLARO);
        tabela.setSelectionBackground(Cores.AZUL_SUAVE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JLabel labelTotal = new JLabel("Total de Entradas: " + String.format("%.2f", total) + " MT");
        labelTotal.setFont(Cores.FONTE_NORMAL);
        labelTotal.setForeground(Cores.ALERTA_VERDE);
        labelTotal.setBorder(new EmptyBorder(10, 0, 0, 0));

        painel.add(labelTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(labelTotal, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel construirTabelaSaidas() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.FUNDO_CARD);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Cores.BORDA, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel labelTitulo = new JLabel("Historico de Saidas");
        labelTitulo.setFont(Cores.FONTE_SUBTITULO);
        labelTitulo.setForeground(Cores.ALERTA_VERMELHO);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] colunas = {"ID", "Conta", "Valor (MT)", "Motivo"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        double total = 0;
        for (Saidas s : saidasController.listarTodas()) {
            modelo.addRow(new Object[]{
                s.getId(),
                s.getContaAssociada(),
                String.format("%.2f", s.getValorRetirado()),
                s.getMotivo()
            });
            total += s.getValorRetirado();
        }

        JTable tabela = new JTable(modelo);
        tabela.setFont(Cores.FONTE_NORMAL);
        tabela.setRowHeight(28);
        tabela.getTableHeader().setFont(Cores.FONTE_NORMAL);
        tabela.getTableHeader().setBackground(Cores.AZUL_ESCURO);
        tabela.getTableHeader().setForeground(Cores.TEXTO_CLARO);
        tabela.setSelectionBackground(Cores.AZUL_SUAVE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JLabel labelTotal = new JLabel("Total de Saidas: " + String.format("%.2f", total) + " MT");
        labelTotal.setFont(Cores.FONTE_NORMAL);
        labelTotal.setForeground(Cores.ALERTA_VERMELHO);
        labelTotal.setBorder(new EmptyBorder(10, 0, 0, 0));

        painel.add(labelTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(labelTotal, BorderLayout.SOUTH);

        return painel;
    }
}