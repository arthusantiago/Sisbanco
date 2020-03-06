import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Index {

	public static void main(String[] args) {
		
		try {
			System.out.println("Iniciando o servidor ....");
			ControllerAtendente atendente = new ControllerAtendente();
			ControllerCliente caixa = new ControllerCliente();
			Registry r1 = LocateRegistry.createRegistry(2126);
			Naming.rebind("rmi://localhost:2126/atendente", atendente);
			Registry r2 = LocateRegistry.createRegistry(2127);
			Naming.rebind("rmi://localhost:2127/caixaeletronico", caixa);
			System.out.println("Servidor no ar!");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
}
