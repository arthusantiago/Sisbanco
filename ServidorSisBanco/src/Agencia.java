import java.io.Serializable;

public class Agencia implements Serializable{
	int id, numero_agencia;
	double cofre_agencia;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumero_agencia() {
		return numero_agencia;
	}
	public void setNumero_agencia(int numero_agencia) {
		this.numero_agencia = numero_agencia;
	}
	public double getCofre_agencia() {
		return cofre_agencia;
	}
	public void setCofre_agencia(double cofre_agencia) {
		this.cofre_agencia = cofre_agencia;
	}
	 
}
