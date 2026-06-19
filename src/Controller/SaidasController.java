package Controller;

import Dao.Repositorio;
import Model.Contas;
import Model.Saidas;
import java.time.LocalDate;
import java.util.List;

public class SaidasController {

    private List<Saidas> saidas;
    private Repositorio repositorio;
    private ContasController contasController;

    public SaidasController(ContasController contasController) {
        this.contasController = contasController;
        this.repositorio = new Repositorio();
        this.saidas = repositorio.carregarSaidas();
    }

    public String gerarId() {
        int ano = LocalDate.now().getYear();
        int sequencia = saidas.size() + 1;
        return String.format("SAI-%d%03d", ano, sequencia);
    }

    public boolean registarSaidas(double valorRetirado, String motivo, String contaAssociada) {
        List<Contas> resultado = contasController.buscaPorTipo(contaAssociada);
        if (resultado.isEmpty()) {
            return false;
        }

        Contas conta = resultado.get(0);
        if (conta.getSaldoConta() < valorRetirado) {
            return false;
        }

        conta.setSaldoConta(conta.getSaldoConta() - valorRetirado);
        contasController.editarContas(conta.getTipoConta(), conta);

        String id = gerarId();
        Saidas s = new Saidas(id, valorRetirado, motivo, contaAssociada);
        saidas.add(s);
        repositorio.salvarSaidas(saidas);
        return true;
    }

    public Saidas buscaPorId(String id) {
        for (Saidas s : saidas) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public List<Saidas> listarTodas() {
        return saidas;
    }

    public boolean editarSaidas(String id, double novoValorRetirado, String novoMotivo, String novaContaAssociada) {
        Saidas antiga = buscaPorId(id);
        if (antiga == null) {
            return false;
        }

        Contas contaAntiga = contasController.buscaPorTipo(antiga.getContaAssociada()).stream().findFirst().orElse(null);
        if (contaAntiga != null) {
            contaAntiga.setSaldoConta(contaAntiga.getSaldoConta() + antiga.getValorRetirado());
            contasController.editarContas(contaAntiga.getTipoConta(), contaAntiga);
        }

        List<Contas> resultado = contasController.buscaPorTipo(novaContaAssociada);
        if (resultado.isEmpty()) {
            return false;
        }

        Contas contaNova = resultado.get(0);
        if (contaNova.getSaldoConta() < novoValorRetirado) {
            return false;
        }

        contaNova.setSaldoConta(contaNova.getSaldoConta() - novoValorRetirado);
        contasController.editarContas(contaNova.getTipoConta(), contaNova);

        antiga.setValorRetirado(novoValorRetirado);
        antiga.setMotivo(novoMotivo);
        antiga.setContaAssociada(novaContaAssociada);

        repositorio.salvarSaidas(saidas);
        return true;
    }

    public boolean removerSaidas(String id) {
        Saidas s = buscaPorId(id);
        if (s == null) {
            return false;
        }

        List<Contas> resultado = contasController.buscaPorTipo(s.getContaAssociada());
        if (resultado.isEmpty()) {
            return false;
        }

        Contas conta = resultado.get(0);
        conta.setSaldoConta(conta.getSaldoConta() + s.getValorRetirado());
        contasController.editarContas(conta.getTipoConta(), conta);

        saidas.remove(s);
        repositorio.salvarSaidas(saidas);
        return true;
    }
}
