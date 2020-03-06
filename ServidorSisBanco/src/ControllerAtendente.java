import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ControllerAtendente extends UnicastRemoteObject implements InterfaceAtendente{

	public ControllerAtendente() throws RemoteException {
		super();
	}

	@Override
	public void cadastrarCliente(String nome, String cpf, String senha) throws RemoteException, Excecao  {
		
		try {
			SisBancoModel model = new SisBancoModel();
			model.criarCliente(nome, cpf, senha);
		} catch (Exception e) {
			System.out.println("SisBancoController - Erro cadastrar o cliente : "+ e.getMessage());
		}
		
	}
	
	@Override
	public ArrayList<Cliente> listarClientes() throws RemoteException, Excecao {
		try {
			SisBancoModel model = new SisBancoModel();
			return model.listarClientes();
		} catch (Exception e){
			System.out.println("SisBancoController - Erro  listar cliente: "+ e.getMessage());
		}
		return null;
	}

	@Override
	public synchronized void cadastroAgencia(double valorCofre) throws RemoteException, Excecao {
		try {
			SisBancoModel model = new SisBancoModel();
			model.cadastrarAgencia(valorCofre);
		} catch(Exception e) {
			System.out.println("SisBancoController - Erro controller agencia: "+ e.getMessage());
		}
		
		
	}

	@Override
	public synchronized ArrayList<Agencia> listarAgencias() throws RemoteException, Excecao {
		try {
			SisBancoModel model = new SisBancoModel();
			return model.listarAgencias();
		} catch (Exception e){
			System.out.println("SisBancoController - Erro listar agencias: "+ e.getMessage());
		}
		return null;
	}
	
	@Override
	public synchronized Integer cadastroConta(int tipo, double saldo, double limiteCredito, int idCliente, int idAgencia)
			throws RemoteException, Excecao {
		
		try {
			SisBancoModel model = new SisBancoModel();
			return model.criarConta(tipo, saldo, limiteCredito, idCliente, idAgencia);
		} catch (Exception e){
			System.out.println("SisBancoController - Erro cadastro conta: "+ e.getMessage());
		}
		return 0;
	}

	@Override
	public synchronized ArrayList<Conta> listarContas() throws RemoteException, Excecao {
		try {
			SisBancoModel model = new SisBancoModel();
			return model.listarContas();
		} catch (Exception e){
			System.out.println("SisBancoController - Erro listagem conta: "+ e.getMessage());
		}
		return null;
	}


	
}
