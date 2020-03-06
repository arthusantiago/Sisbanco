import java.io.Serializable;

public class Transacao implements Serializable{
	int NumerocontaOrigem, NumerocontaDestino, tipoDaTransacao;
	double valor;
	String titularContaDestino, titularContaOrigem;
	public int getNumerocontaOrigem() {
		return NumerocontaOrigem;
	}
	public void setNumerocontaOrigem(int numerocontaOrigem) {
		NumerocontaOrigem = numerocontaOrigem;
	}
	public int getNumerocontaDestino() {
		return NumerocontaDestino;
	}
	public void setNumerocontaDestino(int numerocontaDestino) {
		NumerocontaDestino = numerocontaDestino;
	}
	public int getTipoDaTransacao() {
		return tipoDaTransacao;
	}
	public void setTipoDaTransacao(int tipoDaTransacao) {
		this.tipoDaTransacao = tipoDaTransacao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getTitularContaDestino() {
		return titularContaDestino;
	}
	public void setTitularContaDestino(String titularContaDestino) {
		this.titularContaDestino = titularContaDestino;
	}
	
	
}
