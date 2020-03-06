import java.util.ArrayList;
import java.util.Scanner;

public class ViewCliente {

	public static void main(String[] args) {
		//Variáveis e objetos
		Scanner leitor = new Scanner(System.in);
		ControllerCliente controller = new ControllerCliente();
		int opcao=1;

		System.out.println("Bem-vindo ao SisBanco. O banco do programador JAVA! \n");

		do {
			//Obtendo informações para fazer o processo de login
			System.out.println("Informe o seu CPF e Senha do ternimal de acesso.");
			System.out.println("CPF: ");
			String cpf = leitor.next();
			System.out.println("Senha: ");
			String senha = leitor.next();
			boolean logar = controller.logarCliente(cpf, senha);

			if(logar){
				do {
					try {
						Cliente cliente = new Cliente();
						cliente.setCpf(cpf);
						System.out.println("--------Suas contas bancarias---------- \n");
						ArrayList<Conta> contas = controller.listarContas(cliente);
						for (Conta cont : contas) {
							System.out.println("Numero da conta: "+cont.getNumero()+" | Tipo: "+cont.getTipo());
							System.out.println("Saldo: "+cont.getSaldo());
							if(cont.getLimite_credito() == 0) {
								System.out.println("Limite de crédito: Sem limite de crédito para essa conta.");
							}else {
								System.out.println("Limite de crédito:"+ cont.getLimite_credito());
							}							
							System.out.println("------------------------------- \n");
						}
					} catch (Exception e) {
						System.out.println("Erro na View Atendente: "+e.getMessage());
					}

					//opção da conta
					System.out.println("------------ Suas opções --------------");
					System.out.println("1- Sacar");
					System.out.println("2- Depositar");
					System.out.println("3- Transferir");
					System.out.println("4- Extrato");
					System.out.println("0- Sair do Caixa Eletrônico");
					opcao = leitor.nextInt();

					switch(opcao) {
					case 1: //------------------- Sacar ------------------------------
						break;

					case 2: //------------------- Depositar ------------------------------

						double valor; boolean tmp=false; Conta conta,contaTemp=new Conta();
						System.out.println("------------ DEPOSITO --------------");

						do {//Validando se o valor inserido é negativo ou igual a 0
							System.out.println("Digite o valor do deposito: ");
							valor = leitor.nextDouble();
							if(valor <= 0){
								System.out.println("Você não pode depositar um valor negativou ou igual a zero \n ");	
							}else {
								tmp = true;
							}
						}while(tmp == false);

						System.out.println("Digite o numero da conta que será creditada: ");
						int numeroContaDestino = leitor.nextInt(); tmp = false;

						do {//Confirmando as informações da transação
							contaTemp.setNumero(numeroContaDestino);
							contaTemp = controller.consultarConta(contaTemp);
							System.out.println("Conta creditada: "+contaTemp.getNumero());
							System.out.println("Titular da conta credita: "+contaTemp.getTitular());
							System.out.println("Valor que será depositado: "+valor);
							System.out.println("Você confirma as informações ?  1-Sim ou 2-Não ");
							int tmpConfirma = leitor.nextInt();

							if(tmpConfirma == 1) {
								Transacao trans = new Transacao();
								trans.setNumerocontaDestino(numeroContaDestino);
								trans.setTipoDaTransacao(2);
								trans.setValor(valor);
								controller.depositar(trans);
								System.out.println("Deposito realizado com sucesso !!");
							}
							tmp = true;

						}while(tmp == false);
						break;

					case 3: //------------------- Transferência ------------------------------
						break;

					case 4: //------------------- Extrato ------------------------------
						break;

					case 5: //------------------- TED/DOC ------------------------------
						break;
					}


				}while(opcao != 0);	
			}else {
				System.out.println("CPF ou senha está errado, tente novamente \n ");
			}
		}while(opcao != 0);
	}

}
