import java.util.ArrayList;
import java.util.Scanner;

public class ViewAtendente {
	
	public static void main(String[] args) {
		//Variáveis e objetos
		Scanner leitor = new Scanner(System.in);
		ControllerAtendente controller = new ControllerAtendente();
		int opcao;
		System.out.println("Bem-vindo ao terminal do atendente do Sisbanco. O banco do Programador !!!");
			do{
				System.out.println("1- Cadastrar o cliente");
				System.out.println("2- Listar os clientes");
				System.out.println("3- Cadastrar a agência");
				System.out.println("4- Listar as agências");
				System.out.println("5- Cadastrar a conta do cliente");
				System.out.println("6- Listar as contas cadastradas");
				System.out.println("0- Sair do sistema");
				System.out.print("Digite o numero da opção desejada: ");
				opcao = leitor.nextInt();
				System.out.println(" ");
				
				switch(opcao) {
				case 1: //1- Cadastrar o cliente
					System.out.println("--------Cadastro do cliente----------");
					System.out.println("Digite o nome do cliente: ");
					String nome = leitor.next();
					System.out.println("Digite o CPF (somente os número): ");
					String cpf = leitor.next();
					System.out.println("Digite a senha: ");
					String senha = leitor.next();
					controller.cadastroCliente(nome, cpf, senha);		
					break;
				case 2://2- Listar os clientes
					try {
						System.out.println("--------Listagem dos clientes----------");
						ArrayList<Cliente> clientes = new ArrayList<Cliente>();
						clientes = controller.listarClientes();
						ArrayList<Conta> contas;
						for (Cliente cli : clientes) {
							System.out.println("Nome: "+cli.getNome());
							System.out.println("CPF: "+cli.getCpf());
							contas = new ArrayList<Conta>();
							contas = cli.getContas();
							for (Conta cont : contas) {
								System.out.println("----Conta: "+cont.getNumero()+" | Tipo: "+cont.getTipo());
							}
							System.out.println("------------------------");
						}
					} catch (Exception e) {
						System.out.println("ViewAtendente -  listagem dos cliente: "+e.getMessage());
					}		
					break;
				case 3://3- Cadastrar a agência
					System.out.println("--------Cadastro da agencia----------");
					System.out.println("O numero da agência é gerado pelo sistema, você só precisa cadastro o valor "
							+ "que o cofre da agência está sendo iniciado: ");
					double valorCofre = leitor.nextDouble();
					controller.cadastroAgencia(valorCofre);
					break;
				case 4:// 4- Listar as agências
					try {
						System.out.println("--------Listagem das agencias----------");
						ArrayList<Agencia> agencias = controller.listarAgencias();
						for (Agencia age : agencias) {
							System.out.println("ID: "+age.getId());
							System.out.println("Nume: "+age.getNumero_agencia());
							System.out.println("Valor em cofre: "+age.getCofre_agencia());
							System.out.println("------------------------");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}	
					break;
				case 5:// 5- Cadastrar a conta do cliente
					System.out.println("--------Cadastro da conta----------");
					System.out.println("Escolha o tipo da conta: 1-Conta Simples ou 2-Conta Especial");
					int tipo = leitor.nextInt();
					if(tipo == 1) { // Conta simples
						System.out.println("Digite o codigo do cliente: ");
						int idcliente = leitor.nextInt();
						System.out.println("Digite o saldo da conta: ");
						double saldo = leitor.nextDouble();
						System.out.println("Digite o codigo da agência dessa conta: ");
						int idagencia = leitor.nextInt();
						int numero_conta = controller.cadastroContaSimples(tipo,saldo,idcliente, idagencia);
						System.out.println("/n Criando a conta ....");
						System.out.println("Numero da conta: "+ numero_conta);
					}else { // Conta Especial
						System.out.println("Digite o codigo do cliente: ");
						int idcliente = leitor.nextInt();
						System.out.println("Digite o saldo da conta: ");
						double saldo = leitor.nextDouble();
						System.out.println("Digite o codigo da agência dessa conta: ");
						int idagencia = leitor.nextInt();
						System.out.println("Digite o limite de crédito da conta: ");						
						double limite = leitor.nextDouble();
						int numero_conta = controller.cadastroContaEspecial(tipo, saldo, limite, idcliente, idagencia);
						System.out.println("\n Criando a conta ....");
						System.out.println("Numero da conta: "+ numero_conta);
					}
					break;
					
				case 6:// Listar as contas cadastradas
					
					try {
						System.out.println("--------Listagem das contas----------");
						ArrayList<Conta> contas = controller.listarContas();
						for (Conta cont : contas) {
							System.out.println("Tipo: "+cont.getTipo());
							System.out.println("Numero da conta: "+cont.getNumero());
							System.out.println("Saldo: "+cont.getSaldo());
							if(cont.getLimite_credito() == 0) {
								System.out.println("Limite de crédito: O cliente não tem limite de crédito para essa conta.");
							}else {
								System.out.println("Limite de crédito:"+ cont.getLimite_credito());
							}
							System.out.println("Titular da conta: "+cont.getTitular());							
							System.out.println("-------------------------------");
						}
					} catch (Exception e) {
						System.out.println("Erro na View Atendente:"+e.getMessage());
					}	
					break;
				};
				
				System.out.println(" ");
			}while(opcao != 0);
			System.out.println("Saindo do sistema .....");

	}

}
