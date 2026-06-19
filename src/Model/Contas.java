package Model;

import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.List;


public class Contas implements Serializable {
    
   private static final long serialVersionUID = 1L;

  private String TipoConta;
  private double saldoConta;

    public Contas(String TipoConta, double SaldoConta) {
        this.TipoConta = TipoConta;
        this.saldoConta = SaldoConta;
    }

    public String getTipoConta() {
        return TipoConta;
    }

    public void setTipoConta(String TipoConta) {
        this.TipoConta = TipoConta;
    }

    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) {
        if (saldoConta < 0){
            throw new IllegalArgumentException ("O Saldo não pode ser negativo"); 
        } else {
        this.saldoConta = saldoConta;
        }
    }
    
    public double getSaldoTotal(){
        double total = 0;
        List<Contas> contas = new ArrayList<>();
        for (Contas c : contas) total += c.getSaldoConta();
        return total;
    }

    
    @Override
    public String toString() {
        return "Contas{" + "TipoConta=" + TipoConta + ", SaldoConta=" + saldoConta + '}';
    }
  
    
  
  
}
