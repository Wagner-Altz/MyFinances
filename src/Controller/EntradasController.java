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
        int ano = LocalDate.now().getYear();
        int sequencia = entradas.size() + 1;
        return String.format("ENT-%d%03d", ano, sequencia);
    }

    public boolean registrarEntrada(double valorRecebido, String contaAssociada, String remetente, String motivo) {
        
        List<Contas> resultado = contasController.buscaPorTipo(contaAssociada);
        if (resultado.isEmpty()) {
            return false;
        }

        Contas conta = resultado.get(0);
        conta.setSaldoConta(conta.getSaldoConta() + valorRecebido);
        contasController.editarContas(conta.getTipoConta(), conta);

        String id = gerarId();
        Entradas e = new Entradas(id, valorRecebido, contaAssociada, remetente, motivo);
        entradas.add(e);
        repositorio.salvarEntradas(entradas);
        return true;
    }

    public Entradas buscarPorId(String id) {
        for (Entradas e : entradas) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public boolean editarEntrada(String id, double novoValor, String novaContaAssociada, String novoRemetente, String novoMotivo) {
        Entradas antiga = buscarPorId(id);
        if (antiga == null) {
            return false;
        }

        Contas contaAntiga = contasController.buscaPorTipo(antiga.getContaAssociada()).stream().findFirst().orElse(null);
        if (contaAntiga != null) {
            contaAntiga.setSaldoConta(contaAntiga.getSaldoConta() - antiga.getValorRecebido());
            contasController.editarContas(contaAntiga.getTipoConta(), contaAntiga);
        }

        List<Contas> resultado = contasController.buscaPorTipo(novaContaAssociada);
        if (resultado.isEmpty()) {
            return false;
        }

        Contas contaNova = resultado.get(0);
        contaNova.setSaldoConta(contaNova.getSaldoConta() + novoValor);
        contasController.editarContas(contaNova.getTipoConta(), contaNova);

        antiga.setValorRecebido(novoValor);
        antiga.setContaAssociada(novaContaAssociada);
        antiga.setRemetente(novoRemetente);
        antiga.setMotivo(novoMotivo);

        repositorio.salvarEntradas(entradas);
        return true;
    }

    public boolean removerEntrada(String id) {
        Entradas e = buscarPorId(id);
        if (e == null) {
            return false;
        }

        List<Contas> resultado = contasController.buscaPorTipo(e.getContaAssociada());
        if (resultado.isEmpty()) {
            return false;
        }

        Contas conta = resultado.get(0);
        conta.setSaldoConta(conta.getSaldoConta() - e.getValorRecebido());
        contasController.editarContas(conta.getTipoConta(), conta);

        entradas.remove(e);
        repositorio.salvarEntradas(entradas);
        return true;
    }

    public List<Entradas> listarTodas() {
        return entradas;
    }
    
}
