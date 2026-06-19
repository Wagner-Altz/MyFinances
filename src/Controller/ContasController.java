package Controller;

import Dao.Repositorio;
import Model.Contas;
import java.util.ArrayList;
import java.util.List;


public class ContasController {
 
    private List<Contas> contas;
    private Repositorio repositorio;

    public ContasController() {
        this.repositorio = new Repositorio();
        this.contas = repositorio.carregarContas();
        
    }
    
    public void criarContas (Contas c){
        contas.add(c);
        repositorio.salvarContas(contas);   
    }
    
    public boolean editarContas(String tipoConta, Contas nova){
        for (int i=0; i< contas.size(); i++){
           if (contas.get(i).getTipoConta().equals(tipoConta)){
               contas.set(i, nova);
               repositorio.salvarContas(contas);
               return true;
           } 
        }
        return false;
    }
    
    public boolean removerConta(String tipoConta){
        Contas c = (Contas) buscaPorTipo(tipoConta);
        if (c != null){
            contas.remove(c);
            repositorio.salvarContas(contas);
            return true;
        }
        return false;
    }
    
    public List<Contas> listarTodas(){
        return contas;
    }
    
    public List<Contas> buscaPorTipo(String tipoConta){
      for (Contas c : contas) {
        if (c.getTipoConta().equalsIgnoreCase(tipoConta)) return (List<Contas>) c;
    }
    return null;
    }
    
    
    
    
    
    
}
