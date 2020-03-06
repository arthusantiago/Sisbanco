import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.Naming;

public class ControllerAtendente extends Exception{
	
	InterfaceAtendente sisbanco;
	String urlServer = "rmi://localhost:2126/atendente";
	
	public void cadastroCliente (String nome, String cpf,String senha) {
		try {
			sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);		
			sisbanco.cadastrarCliente(nome,cpf,senha);
		}catch (Exception e) {
			System.out.println("Erro cadastro Cliente: "+ e.getMessage());
		};
	}
	
	
	public ArrayList<Cliente> listarClientes() throws MalformedURLException, RemoteException, NotBoundException, Excecao{
		try {
			sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);	
			return sisbanco.listarClientes();
		}catch (Exception e) {
			System.out.println("ControllerAtendente - Erro ao listar os clientes: "+ e.getMessage());
		}	
			return null;			
	}
	
	public void cadastroAgencia (double valorCofre) {
		try {
			
			sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);	
			sisbanco.cadastroAgencia(valorCofre); 
			
		 } catch (Exception e) {
			System.out.println("Erro cadastro Agencia: "+ e.getMessage());
		 };
	}
	
	public ArrayList<Agencia> listarAgencias() throws MalformedURLException, RemoteException, NotBoundException, Excecao{
		try {
			sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);	
			return sisbanco.listarAgencias();
		}catch (Exception e) {
			System.out.println("Controller listar agencias: "+ e.getMessage());
		}	
			return null;			
	}
	
	public Integer cadastroContaSimples (int tipo ,double saldo,int idCliente, int idAgencia) {
		
		try {
			InterfaceAtendente sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);
			double limiteCredito = 0.0; 
			return sisbanco.cadastroConta(tipo, saldo, limiteCredito, idCliente, idAgencia);
		}catch (Exception e) {
			System.out.println("Erro cadastro Conta simples: "+ e.getMessage());
		};
		return 0;
	}
	
	public Integer cadastroContaEspecial (int tipo ,double saldo,double limiteCredito,int idCliente, int idAgencia) {
		try {
			InterfaceAtendente sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);		
			return sisbanco.cadastroConta(tipo, saldo, limiteCredito, idCliente, idAgencia);
		} catch (Exception e) {
			System.out.println("Erro cadastro Conta Especial: "+ e.getMessage());
		};
		return 0;
	}
	
	public ArrayList<Conta> listarContas (){
		try {
			InterfaceAtendente sisbanco = (InterfaceAtendente)Naming.lookup(urlServer);		
			return sisbanco.listarContas();
		 }catch (Exception e) {
			 System.out.println("Controller atendente - Erro listagem contas: "+ e.getMessage());
		 };
		 return null;	
	}


	
}