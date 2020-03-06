import java.io.Serializable;

public class Conta implements Serializable{
	private int tipo, numero_conta, id_cliente, id_agencia;
	private double saldo, limite_credito;
	private String titular;
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getNumero() {
		return numero_conta;
	}
	public void setNumero(int numero) {
		this.numero_conta = numero;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_agencia() {
		return id_agencia;
	}
	public void setId_agencia(int id_agencia) {
		this.id_agencia = id_agencia;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getLimite_credito() {
		return limite_credito;
	}
	public void setLimite_credito(double limite_credito) {
		this.limite_credito = limite_credito;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	
}
