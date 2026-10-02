package Controller;

import Dao.Repositorio;
import Model.Contas;
import java.util.List;

public class ContasController {

    private final List<Contas> contas;
    private final Repositorio repositorio;
    private EntradasController entradasController;
    private SaidasController saidasController;

    public ContasController() {
        this.repositorio = new Repositorio();
        this.contas = repositorio.carregarContas();
    }

    public void setEntradasController(EntradasController c) {
        this.entradasController = c;
    }

    public void setSaidasController(SaidasController c) {
        this.saidasController = c;
    }

    public boolean contaTemMovimentos(String tipoConta) {
        if (entradasController != null && entradasController.existeParaConta(tipoConta)) {
            return true;
        }
        return saidasController != null && saidasController.existeParaConta(tipoConta);
    }

    public boolean criarContas(Contas c) {
        if (buscaPorTipo(c.getTipoConta()) != null) {
            return false;
        }
        contas.add(c);
        repositorio.salvarContas(contas);
        return true;
    }

    public boolean editarContas(String tipoConta, Contas nova) {
        boolean mudouTipo = !nova.getTipoConta().equalsIgnoreCase(tipoConta);
        if (mudouTipo && (contaTemMovimentos(tipoConta) || buscaPorTipo(nova.getTipoConta()) != null)) {
            return false;
        }
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getTipoConta().equalsIgnoreCase(tipoConta)) {
                contas.set(i, nova);
                repositorio.salvarContas(contas);
                return true;
            }
        }
        return false;
    }

    public boolean removerConta(String tipoConta) {
        Contas c = buscaPorTipo(tipoConta);
        if (c == null || contaTemMovimentos(tipoConta)) {
            return false;
        }
        contas.remove(c);
        repositorio.salvarContas(contas);
        return true;
    }

    public List<Contas> listarTodas() {
        return contas;
    }

    public Contas buscaPorTipo(String tipoConta) {
        for (Contas c : contas) {
            if (c.getTipoConta().equalsIgnoreCase(tipoConta)) {
                return c;
            }
        }
        return null;
    }
}
