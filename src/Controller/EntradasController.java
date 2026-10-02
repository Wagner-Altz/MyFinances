package Controller;

import Dao.Repositorio;
import Model.Contas;
import Model.Entradas;
import java.time.LocalDate;
import java.util.List;

public class EntradasController {

    private final List<Entradas> entradas;
    private final Repositorio repositorio;
    private final ContasController contasController;

    public EntradasController(ContasController contasController) {
        this.contasController = contasController;
        this.repositorio = new Repositorio();
        this.entradas = repositorio.carregarEntradas();
    }

    private String gerarId() {
        String prefixo = "ENT-" + LocalDate.now().getYear();
        int max = 0;
        for (Entradas e : entradas) {
            String id = e.getId();
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
    public String registrarEntrada(double valorRecebido, String contaAssociada, String remetente, String motivo) {
        if (!(valorRecebido > 0) || Double.isInfinite(valorRecebido)) {
            return "Valor invalido.";
        }

        Contas conta = contasController.buscaPorTipo(contaAssociada);
        if (conta == null) {
            return "Conta nao encontrada.";
        }

        conta.setSaldoConta(conta.getSaldoConta() + valorRecebido);
        contasController.editarContas(conta.getTipoConta(), conta);

        Entradas e = new Entradas(gerarId(), valorRecebido, contaAssociada, remetente, motivo);
        entradas.add(e);
        repositorio.salvarEntradas(entradas);
        return null;
    }

    public Entradas buscarPorId(String id) {
        for (Entradas e : entradas) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public String removerEntrada(String id) {
        Entradas e = buscarPorId(id);
        if (e == null) {
            return "Entrada nao encontrada.";
        }

        Contas conta = contasController.buscaPorTipo(e.getContaAssociada());
        if (conta != null) {
            if (conta.getSaldoConta() - e.getValorRecebido() < 0) {
                return "Saldo insuficiente: a conta ficaria negativa.";
            }
            conta.setSaldoConta(conta.getSaldoConta() - e.getValorRecebido());
            contasController.editarContas(conta.getTipoConta(), conta);
        }

        entradas.remove(e);
        repositorio.salvarEntradas(entradas);
        return null;
    }

    public String editarEntrada(String id, double novoValor, String novaContaAssociada, String novoRemetente, String novoMotivo) {
        if (!(novoValor > 0) || Double.isInfinite(novoValor)) {
            return "Valor invalido.";
        }

        Entradas antiga = buscarPorId(id);
        if (antiga == null) {
            return "Entrada nao encontrada.";
        }

        Contas contaAntiga = contasController.buscaPorTipo(antiga.getContaAssociada());
        Contas contaNova = contasController.buscaPorTipo(novaContaAssociada);
        if (contaNova == null) {
            return "Conta nao encontrada.";
        }

        if (contaAntiga == contaNova) {
            if (contaNova.getSaldoConta() - antiga.getValorRecebido() + novoValor < 0) {
                return "Saldo insuficiente: a conta ficaria negativa.";
            }
        } else if (contaAntiga != null) {
            if (contaAntiga.getSaldoConta() - antiga.getValorRecebido() < 0) {
                return "Saldo insuficiente: a conta ficaria negativa.";
            }
        }

        if (contaAntiga != null) {
            contaAntiga.setSaldoConta(contaAntiga.getSaldoConta() - antiga.getValorRecebido());
            contasController.editarContas(contaAntiga.getTipoConta(), contaAntiga);
        }

        contaNova.setSaldoConta(contaNova.getSaldoConta() + novoValor);
        contasController.editarContas(contaNova.getTipoConta(), contaNova);

        antiga.setValorRecebido(novoValor);
        antiga.setContaAssociada(novaContaAssociada);
        antiga.setRemetente(novoRemetente);
        antiga.setMotivo(novoMotivo);

        repositorio.salvarEntradas(entradas);
        return null;
    }

    public List<Entradas> listarTodas() {
        return entradas;
    }

    public boolean existeParaConta(String tipoConta) {
        for (Entradas e : entradas) {
            if (e.getContaAssociada().equalsIgnoreCase(tipoConta)) {
                return true;
            }
        }
        return false;
    }
}
