package Model;

import java.io.Serializable;
import java.time.LocalDate;


public class Saidas implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private LocalDate data;
    private double valorRetirado;
    private String motivo;
    private String contaAssociada;

    public Saidas(String id, double valorRetirado, String motivo, String contaAssociada) {
        this.id = id;
        this.data = LocalDate.now();
        this.valorRetirado = valorRetirado;
        this.motivo = motivo;
        this.contaAssociada = contaAssociada;
    }
    
    public LocalDate getDataHora() { return data; }
    public void setDataHora(LocalDate dataHora) {
        if (dataHora != null) {
            this.data = dataHora;
        }
    }

    public String getContaAssociada() {
        return contaAssociada;
    }

    public void setContaAssociada(String contaAssociada) {
        this.contaAssociada = contaAssociada;
    }

    public double getValorRetirado() {
        return valorRetirado;
    }

    public void setValorRetirado(double valorRetirado) {
        this.valorRetirado = valorRetirado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Saidas{" + "id=" + id + ", valorRetirado=" + valorRetirado + ", motivo=" + motivo + ", contaAssociada=" + contaAssociada + '}';
    }

  
    
    
    
}
