import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.Naming;

public class ControllerCliente extends Exception{
	InterfaceCliente sisbanco;
	String urlServer = "rmi://localhost:2127/caixaeletronico";
	
	public boolean logarCliente (String cpf,String senha) {
		try {
			sisbanco = (InterfaceCliente)Naming.lookup(urlServer);		
			return sisbanco.logarCliente(cpf,senha);
		}catch (Exception e) {
			System.out.println("Erro Controller cliente - Locar Cliente: "+ e.getMessage());
			return false;
		}
	}
	
	public ArrayList<Conta> listarContas(Cliente cliente){
		try {
			sisbanco = (InterfaceCliente)Naming.lookup(urlServer);
			return sisbanco.listarContas(cliente);
		}catch (Exception e) {
			System.out.println("ControllerCliente - Erro ao Listar as contas do cliente: "+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void depositar(Transacao transacao){
		try {
			sisbanco = (InterfaceCliente)Naming.lookup(urlServer);
			sisbanco.depositar(transacao);
		}catch (Exception e) {
			System.out.println("ControllerCliente - Erro ao depositar: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Conta consultarConta(Conta conta){
		try {
			sisbanco = (InterfaceCliente)Naming.lookup(urlServer);
			return sisbanco.consultarConta(conta);
		}catch (Exception e) {
			System.out.println("ControllerCliente - Erro ao depositar: "+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
}
