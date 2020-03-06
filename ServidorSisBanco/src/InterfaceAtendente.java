import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceAtendente extends Remote{

	public void cadastrarCliente(String nome, String cpf,String senha) throws RemoteException, Excecao; 
	
	public ArrayList<Cliente> listarClientes() throws RemoteException, Excecao; 
	
	public void cadastroAgencia(double valorCofre) throws RemoteException, Excecao;
	
	public ArrayList<Agencia> listarAgencias() throws RemoteException, Excecao; 
		
	public Integer cadastroConta (int tipo ,double saldo,double limiteCredito,int idCliente, int idAgencia) throws RemoteException, Excecao;
	
	public ArrayList<Conta> listarContas() throws RemoteException, Excecao; 

}
