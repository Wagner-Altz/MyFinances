package Controller;

import Dao.Repositorio;
import Model.Contas;
import Model.Saidas;
import java.time.LocalDate;
import java.util.List;

public class SaidasController {

    private final List<Saidas> saidas;
    private final Repositorio repositorio;
    private final ContasController contasController;

    public SaidasController(ContasController contasController) {
        this.contasController = contasController;
        this.repositorio = new Repositorio();
        this.saidas = repositorio.carregarSaidas();
    }

    private String gerarId() {
        String prefixo = "SAI-" + LocalDate.now().getYear();
        int max = 0;
        for (Saidas s : saidas) {
            String id = s.getId();
            if (id != null && id.startsWith(prefixo)) {
                try {
                    max = Math.max(max, Integer.parseInt(id.substring(prefixo.length())));
                } catch (NumberFormatException ex) {
                    // ID malformado: ignora
                }
            }
        }
        return String.format("%s%03d", prefixo, max + 1);
    }

    /**
     * Devolve null em caso de sucesso, ou a mensagem de erro.
     */
    public String registarSaidas(double valorRetirado, String motivo, String contaAssociada) {
        if (!(valorRetirado > 0) || Double.isInfinite(valorRetirado)) {
            return "Valor invalido.";
        }

        Contas conta = contasController.buscaPorTipo(contaAssociada);
        if (conta == null) {
            return "Conta nao encontrada.";
        }

        if (conta.getSaldoConta() < valorRetirado) {
            return "Saldo insuficiente.";
        }

        conta.setSaldoConta(conta.getSaldoConta() - valorRetirado);
        contasController.editarContas(conta.getTipoConta(), conta);

        Saidas s = new Saidas(gerarId(), valorRetirado, motivo, contaAssociada);
        saidas.add(s);
        repositorio.salvarSaidas(saidas);
        return null;
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

    public String editarSaidas(String id, double novoValorRetirado, String novoMotivo, String novaContaAssociada) {
        if (!(novoValorRetirado > 0) || Double.isInfinite(novoValorRetirado)) {
            return "Valor invalido.";
        }

        Saidas antiga = buscaPorId(id);
        if (antiga == null) {
            return "Saida nao encontrada.";
        }

        Contas contaAntiga = contasController.buscaPorTipo(antiga.getContaAssociada());
        Contas contaNova = contasController.buscaPorTipo(novaContaAssociada);
        if (contaNova == null) {
            return "Conta nao encontrada.";
        }

        double disponivel = contaNova.getSaldoConta();
        if (contaAntiga == contaNova) {
            disponivel += antiga.getValorRetirado();
        }
        if (disponivel < novoValorRetirado) {
            return "Saldo insuficiente.";
        }

        if (contaAntiga != null) {
            contaAntiga.setSaldoConta(contaAntiga.getSaldoConta() + antiga.getValorRetirado());
            contasController.editarContas(contaAntiga.getTipoConta(), contaAntiga);
        }

        contaNova.setSaldoConta(contaNova.getSaldoConta() - novoValorRetirado);
        contasController.editarContas(contaNova.getTipoConta(), contaNova);

        antiga.setValorRetirado(novoValorRetirado);
        antiga.setMotivo(novoMotivo);
        antiga.setContaAssociada(novaContaAssociada);

        repositorio.salvarSaidas(saidas);
        return null;
    }

    public String removerSaidas(String id) {
        Saidas s = buscaPorId(id);
        if (s == null) {
            return "Saida nao encontrada.";
        }

        Contas conta = contasController.buscaPorTipo(s.getContaAssociada());
        if (conta != null) {
            conta.setSaldoConta(conta.getSaldoConta() + s.getValorRetirado());
            contasController.editarContas(conta.getTipoConta(), conta);
        }

        saidas.remove(s);
        repositorio.salvarSaidas(saidas);
        return null;
    }

    public boolean existeParaConta(String tipoConta) {
        for (Saidas s : saidas) {
            if (s.getContaAssociada().equalsIgnoreCase(tipoConta)) {
                return true;
            }
        }
        return false;
    }
}
