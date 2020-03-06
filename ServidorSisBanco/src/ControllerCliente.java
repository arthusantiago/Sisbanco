import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerCliente extends UnicastRemoteObject implements InterfaceCliente{

	public ControllerCliente() throws RemoteException {
		super();
	}

	@Override
	public boolean logarCliente(String cpf, String senha) throws RemoteException, Excecao {
		try {
			SisBancoModel model = new SisBancoModel();
			return model.logarCliente(cpf, senha);
		} catch (Exception e){
			System.out.println("SisBancoController - Erro ao logar o cliente: "+ e.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<Conta> listarContas(Cliente cliente) throws RemoteException, Excecao {
		try {
			SisBancoModel model = new SisBancoModel();
			return model.listarContasLogado(cliente);
		} catch (Exception e){
			System.out.println("SisBancoController - Erro ao buscar as contas do cliente logado: "+ e.getMessage());
		}
		return null;
	}

	@Override
	public void depositar(Transacao transacao) throws SQLException,RemoteException{
		try {
			SisBancoModel model = new SisBancoModel();
			model.depositar(transacao);
		} catch (Exception e){
			System.out.println("SisBancoController - Erro no deposito: "+ e.getMessage());
		}
	}

	@Override
	public Conta consultarConta(Conta conta) throws SQLException, RemoteException{
		try {
			SisBancoModel model = new SisBancoModel();
			Conta tmp = model.consultarConta(conta);
			tmp.setSaldo(0.0);
			tmp.setTipo(0);
			tmp.setLimite_credito(0);
			return tmp;
		} catch (Exception e){
			System.out.println("SisBancoController - Na consulta da conta: "+ e.getMessage());
		}
		return null;
	}

	
}
