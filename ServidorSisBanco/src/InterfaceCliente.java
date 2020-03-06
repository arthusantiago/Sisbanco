import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InterfaceCliente extends Remote{
	//lista as contas do cliente logado no Caixa Eletrônico
	public ArrayList<Conta> listarContas(Cliente cliente) throws RemoteException, Excecao; 
	
	public boolean logarCliente(String cpf,String senha) throws RemoteException, Excecao;
	
	public void depositar(Transacao transacao) throws RemoteException, SQLException;
	
	//Eu recebo um objeto Conta com a informação do numero da conta e retornou outro objeto Conta
	//com todos os dados referente a esse conta.
	public Conta consultarConta(Conta conta)throws RemoteException, SQLException;

	
}
