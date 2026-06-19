package Model;

import java.io.Serializable;
import java.time.LocalDate;


public class Entradas implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
     private LocalDate data;
    private double valorRecebido;
    private String contaAssociada;
    private String remetente;
    private String motivo;



    public Entradas(String id, double valorRecebido, String contaAssociada, String remetente, String motivo) {
        this.id = id;
        this.data = LocalDate.now();
        this.valorRecebido = valorRecebido;
        this.contaAssociada = contaAssociada;
        this.remetente = remetente;
        this.motivo = motivo;
    }

    public LocalDate getData() { return data; }
    public void setData(LocalDate dataHora) {
        if (dataHora != null) {
            this.data = dataHora;
        }
    }
    
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(double valorRecebido) {
        if (valorRecebido < 0){
            throw new IllegalArgumentException ("O valor não pode ser negativo"); 
        } else {
        this.valorRecebido = valorRecebido;
        }
        
    }

    public String getContaAssociada() {
        return contaAssociada;
    }

    public void setContaAssociada(String contaAssociada) {
        this.contaAssociada = contaAssociada;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }
    
       public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entradas{" + "id=" + id + ", valorRecebido=" + valorRecebido + ", contaAssociada=" + contaAssociada + ", remetente=" + remetente + ", motivo=" + motivo + '}';
    }
    

    
   
    
    
    
}
