package Dao;

import Model.Contas;
import Model.Entradas;
import Model.Saidas;
import Model.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Repositorio {
    
    private static final String Ficheiro_Contas = "dados/Contas.dat";
    private static final String Ficheiro_Entradas = "dados/Entradas.dat";
    private static final String Ficheiro_Saidas = "dados/Saidas.dat";
    
    public Repositorio(){
        new File("dados").mkdirs();
    }
    
    public void salvarContas(List<Contas> lista){
        salvar(Ficheiro_Contas, lista);   
    }
    
    public List<Contas> carregarContas(){
        return carregar (Ficheiro_Contas);
    }
    
    public void salvarEntradas(List<Entradas> lista){
        salvar(Ficheiro_Entradas, lista);
    }
    
    public List<Entradas> carregarEntradas(){
        return carregar(Ficheiro_Entradas);
    }
    
    public void salvarSaidas(List<Saidas> lista){
        salvar(Ficheiro_Saidas, lista);
    }
    
    public List<Saidas> carregarSaidas(){
        return carregar(Ficheiro_Saidas); 
    }
    
    private static final String FICHEIRO_USUARIO = "dados/usuario.dat";

    public void salvarUsuario(Usuario u) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHEIRO_USUARIO))) {
            oos.writeObject(u);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuario: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        File ficheiro = new File(FICHEIRO_USUARIO);
        if (!ficheiro.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHEIRO_USUARIO))) {
            return (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar usuario: " + e.getMessage());
            return null;
        }
    }
    
    private <T> void salvar (String caminho, List<T> lista){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (caminho))){
            oos.writeObject(lista);
        }catch (IOException e){
            System.err.println("Erro ao salvar ficheiro: " +caminho+ " - " +e.getMessage());
        }
    }
    
    private <T> List<T> carregar (String caminho){
        File ficheiro = new File(caminho);
        if (!ficheiro.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream (caminho))){
            return (List<T>) ois.readObject(); 
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Erro ao carregar ficheiro: " + caminho + " - " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
}
