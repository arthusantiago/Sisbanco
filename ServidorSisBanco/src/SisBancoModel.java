import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SisBancoModel{
	
	private final ConexaoJDBC conexao;
	
	public SisBancoModel() throws ClassNotFoundException, SQLException {
		this.conexao = new ConexaoPostgresJDBC();
	}

	
	public synchronized void criarCliente(String nome, String cpf, String senha)throws Excecao {
		String sql = "INSERT INTO cliente (nome,cpf,senha) VALUES (?,?,?);";
		try {
			PreparedStatement st = this.conexao.getConnection().prepareStatement(sql);
			st.setString(1, nome);
			st.setString(2, cpf);
			st.setString(3, senha);
			st.executeQuery();
			this.conexao.close();
			
		} catch (Exception e) {
			System.out.println("Erro ao inserir o cliente: "+e);
		}
	}
	
	public synchronized ArrayList<Cliente> listarClientes() throws SQLException {
		
		//Trazendo do BD todos os clientes
		String sqlClientes = "SELECT * FROM public.cliente;";
		PreparedStatement st = this.conexao.getConnection().prepareStatement(sqlClientes);
		ResultSet resultadoSqlClientes = st.executeQuery();
		
		//Preparação das variaveis utilizadas utilizadas dentro do WHILE
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		ArrayList<Conta> contas; 
		Cliente cliente;
		Conta cont;
		int id;
		ResultSet rs_contas_cliente;
		String sql_contas_cliente;
		
		while(resultadoSqlClientes.next()){
			//Setando no objetos as informações referente ao cadastro de pessoa deste cliente
			contas = new ArrayList<Conta>();
			cliente = new Cliente();
			cliente.setNome(resultadoSqlClientes.getString("nome"));
			cliente.setCpf(resultadoSqlClientes.getString("cpf"));
			
			//Trazendo do BD as contas deste cliente;
			id = resultadoSqlClientes.getInt("id");
			sql_contas_cliente = "SELECT cont.tipo, cont.numero_conta FROM public.conta AS cont WHERE cont.id_cliente = ?;";
			st = this.conexao.getConnection().prepareStatement(sql_contas_cliente);
			st.setInt(1,id);
			rs_contas_cliente =st.executeQuery();
			
			//Criando um array de contas para salvar no objeto cliente.
			while(rs_contas_cliente.next()) {
				cont = new Conta();
				cont.setTipo(rs_contas_cliente.getInt("tipo"));
				cont.setNumero(rs_contas_cliente.getInt("numero_conta"));
				contas.add(cont);
			}
			cliente.setContas(contas);
			
			//Array contendo os clientes e suas informações
			clientes.add(cliente);  
        }
		this.conexao.close();
		return clientes;
	}
	
	public synchronized void cadastrarAgencia(double valorCofre)throws SQLException {
		try {
			String sql = "INSERT INTO public.agencia(cofre_agencia)	VALUES (?)";
			PreparedStatement st = this.conexao.getConnection().prepareStatement(sql);
			st.setDouble(1, valorCofre);
			st.executeQuery();
			this.conexao.close();
			
		} catch (Exception e) {
			System.out.println("Erro model agência: "+e.getMessage());
		}
	}
	
	public synchronized ArrayList<Agencia> listarAgencias() throws SQLException {

		Agencia agencia = null;
		String sql = "SELECT * FROM public.Agencia";
		PreparedStatement st = this.conexao.getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		ArrayList<Agencia> agencias = new ArrayList<Agencia>();

		while (rs.next()){
			agencia = new Agencia();
			agencia.setId(rs.getInt("id"));
			agencia.setNumero_agencia(rs.getInt("numero_agencia"));
			agencia.setCofre_agencia(rs.getDouble("cofre_agencia"));
			agencias.add(agencia);
			agencia = null;
		}
		this.conexao.close();
		return agencias;
	}
	
	public synchronized Integer criarConta(int tipo, double saldo, double limiteCredito, int idCliente, int idAgencia)throws Excecao {

		try {
			String sql = "INSERT INTO conta (tipo,saldo,limite_credito,id_cliente,id_agencia)"
					+ "VALUES (?,?,?,?,?) RETURNING id";
			PreparedStatement st = this.conexao.getConnection().prepareStatement(sql);
			st.setInt(1, tipo);
			st.setDouble(2, saldo);
			st.setDouble(3, limiteCredito);
			st.setInt(4, idCliente);
			st.setInt(5, idAgencia);

			ResultSet rs = st.executeQuery();

			//Gerando o numero da conta
			rs.next();
			int id = rs.getInt("id");
			Integer num_conta = Integer.valueOf(id+""+tipo+""+idCliente+""+idAgencia);
			String sqlNumeroConta = "UPDATE public.conta SET numero_conta=? WHERE id=? RETURNING numero_conta;";
			st = null;
			st = this.conexao.getConnection().prepareStatement(sqlNumeroConta);
			st.setInt(1,num_conta);
			st.setInt(2, id);
			rs = st.executeQuery();				

			this.conexao.close();
			return num_conta;

		} catch (Exception e) {
			System.out.println("Model-server: Erro ao inserir a conta: "+e.getMessage());
		}
		return 0;
	}
	
	public synchronized ArrayList<Conta> listarContas() throws SQLException {

		Conta conta;
		String sql = "SELECT cont.tipo, cont.numero_conta, cont.saldo, cont.limite_credito, cli.nome\n" + 
					 "FROM public.conta AS cont, public.cliente AS cli;";
		PreparedStatement st = this.conexao.getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		ArrayList<Conta> contas = new ArrayList<Conta>();

		while (rs.next()){
			conta = new Conta();
			conta.setTipo(rs.getInt("tipo"));
			conta.setNumero(rs.getInt("numero_conta"));
			conta.setSaldo(rs.getDouble("saldo"));
			conta.setLimite_credito(rs.getDouble("limite_credito"));
			conta.setTitular(rs.getString("nome"));
			contas.add(conta);
			conta = null;
		}
		this.conexao.close();
		return contas;
	}

	public synchronized boolean logarCliente(String cpf, String senha) throws SQLException{
		
		try {
			String sql =  "SELECT cli.id FROM public.cliente AS cli WHERE cli.cpf = ? AND cli.senha = ?";
			PreparedStatement st = this.conexao.getConnection().prepareStatement(sql);
			st.setString(1, cpf);
			st.setString(2, senha);
			ResultSet rs = st.executeQuery();
			this.conexao.close();
			if (rs.next()) {
				return true;	
			}
			
		} catch (Exception e) {
			System.out.println("Sisbanco Model - Erro ao validar senha e cpf: "+e.getMessage());
		}		
		return false;
	}
	
	public synchronized ArrayList<Conta> listarContasLogado(Cliente cliente)throws SQLException {
		
		try {
			String sql =  "SELECT cont.tipo, cont.numero_conta, cont.saldo, cont.limite_credito " + 
						  "FROM public.conta AS cont " + 
						  "WHERE cont.id_cliente = (SELECT cli.id FROM public.cliente AS cli WHERE cli.cpf = ?);";
			PreparedStatement ps = this.conexao.getConnection().prepareStatement(sql);
			ps.setString(1, cliente.getCpf());
			ResultSet rs = ps.executeQuery();
			ArrayList<Conta> contas = new ArrayList<Conta>();
			
			while(rs.next()) {
				Conta ct = new Conta();
				ct.setNumero(rs.getInt("numero_conta"));
				ct.setTipo(rs.getInt("tipo"));
				ct.setSaldo(rs.getDouble("saldo"));
				ct.setLimite_credito(rs.getDouble("limite_credito"));
				contas.add(ct);
			}
			this.conexao.close();
			return contas;
			
		} catch (Exception e) {
			System.out.println("SisBancoModel - Erro ao buscar as contas do cliente logado: "+e.getMessage());
		}
		return null;
	}
	
	
	/*
	 * Paramestros de entrada: Objeto Conta {Numeroconta} 
	 * */
	public synchronized Conta consultarConta (Conta conta) throws SQLException{
		//Eu recebo um objeto Conta com a informação do numero da conta e retornou outro objeto Conta
		//com todos os dados referente a esse conta.
		try {
			String sqlConta = "SELECT ct.tipo, ct.numero_conta ,ct.saldo, ct.limite_credito, ct.id_cliente, ct.id_agencia, cli.nome " + 
							  "FROM public.conta AS ct JOIN public.cliente AS cli" + 
							  "	ON cli.id = ct.id_cliente " + 
							  "WHERE ct.numero_conta = ? ;";
			PreparedStatement ps = this.conexao.getConnection().prepareStatement(sqlConta);
			ps.setInt(1,conta.getNumero());
			ResultSet rs = ps.executeQuery();
			this.conexao.close();
			Conta ct = new Conta();
			while(rs.next()) {
				ct.setTipo(rs.getInt("tipo"));
				ct.setNumero(rs.getInt("numero_conta"));
				ct.setSaldo(rs.getDouble("saldo"));
				ct.setLimite_credito(rs.getDouble("limite_credito"));
				ct.setId_cliente(rs.getInt("id_cliente"));
				ct.setId_agencia(rs.getInt("id_agencia"));
				ct.setTitular(rs.getString("nome"));
			}
			
			return ct;
		} catch (Exception e) {
			System.out.println("SisBancoModel - Erro na consulta da conta: ");
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * Paramestros de entrada: Objeto Transacao {NumerocontaDestino,Valor,TipoDaTransacao} 
	 * */
	public synchronized void depositar(Transacao transacao) throws SQLException{
		try {
			//Trazer o saldo da conta de destino
			String sqlSaldoAtual = "SELECT conta.saldo FROM public.conta WHERE conta.numero_conta = ?;";
			PreparedStatement ps1 = this.conexao.getConnection().prepareStatement(sqlSaldoAtual);
			ps1.setInt(1, transacao.getNumerocontaDestino());
			ResultSet rs = ps1.executeQuery();
			
			//Atualizar o saldo da conta de destino
			rs.next();
			double novoSaldoDaConta = transacao.getValor() + rs.getDouble("saldo");
			String sqlNovoSaldo = "UPDATE public.conta SET saldo=? WHERE conta.numero_conta=?;";
			PreparedStatement ps2 = this.conexao.getConnection().prepareStatement(sqlNovoSaldo);
			ps2.setDouble(1, novoSaldoDaConta);
			ps2.setInt(2,transacao.getNumerocontaDestino());
			ps2.executeUpdate();
		
			//Salvar historico da transação
			String sqlHistorico = "INSERT INTO public.transacao(tipo, valor, numero_conta) VALUES (?, ?, ?);";
			PreparedStatement ps3 = this.conexao.getConnection().prepareStatement(sqlHistorico);
			ps3.setInt(1, transacao.getTipoDaTransacao());
			ps3.setDouble(2, transacao.getValor());
			ps3.setInt(3,transacao.getNumerocontaDestino());
			ps3.execute();
			
			this.conexao.close();
			
		} catch (Exception e) {
			System.out.println("SisBancoModel - Erro no deposito: ");
			e.printStackTrace();
			this.conexao.close();
		}
		
	}

	
	/*
	 * Paramestros de entrada: Objeto transacao {ContaOrigem,ContaDestino,Valor}
	 * O valor dentro do obj é o dinheiro que vai para a conta de destino
	 * */
	public synchronized String transferencia(Transacao transacao)throws SQLException{
		// Dois tipos de transferencia: 1-Contas do mesmo titulo | 2-Contas de titulares diferentes
		
		double taxa=2;//taxa combrada na transferencia entre titulares diferentes
		
		//Pegando todas as informações da conta de origem
		Conta tmp = new Conta();
		tmp.setNumero(transacao.getNumerocontaOrigem());
		Conta contaOrigem = consultarConta(tmp); tmp =null;
		
		//pegando todas as informações da conta de Destino
		tmp.setNumero(transacao.getNumerocontaDestino());
		Conta contaDestino = consultarConta(tmp); tmp=null;
		
		//Se entrou aqui significa que as contas pertencem ao mesmo cliente
		if(contaOrigem.getId_cliente() == contaDestino.getId_cliente()) {
			
			//Verifica se na conta de origem tem saldo suficiente
			if((contaOrigem.getSaldo() - transacao.getValor()) >= 0) {
				
				//subtrair o valor da conta de origem
				Transacao tmpTrans = new Transacao();
				tmpTrans.setNumerocontaOrigem(contaOrigem.getNumero());
				double valor = contaOrigem.getSaldo() - transacao.getValor();
				tmpTrans.setValor(valor);
				tmpTrans.setTipoDaTransacao(3); //Tipo 3-Transferencia
				debitar(tmpTrans); tmpTrans = null;
				
				//depositar o valor na conta de destino;
				tmpTrans.setValor(transacao.getValor());
				tmpTrans.setNumerocontaDestino(contaDestino.getNumero());
				tmpTrans.setTipoDaTransacao(2); //Tipo 2-Deposito
				depositar(tmpTrans);
				
			}else {//Se entrou aqui, significa que as contas pertencem a clientes diferentes;
				return "Sem saldo suficiente na conta de origem !";
			}
			return "Transferência realizada com sucesso!";
			
		}else {//Se entrou aqui significa que as contas pertencem a titulares diferentes
			
			//Calculando o valor que será cobrado e o valor final da transação
			double taxaSobreEstaTrans = (transacao.getValor() * taxa) / 100;
			double valorFinal = transacao.getValor() + taxaSobreEstaTrans;
			
			//Verifico se na conta de origem tem saldo suficiente
			if((contaOrigem.getSaldo() - valorFinal) >= 0) {
				
				//subtrair o valor da conta de origem
				Transacao tmpTrans = new Transacao();
				tmpTrans.setNumerocontaOrigem(contaOrigem.getNumero());
				double valor = contaOrigem.getSaldo() - transacao.getValor();
				tmpTrans.setValor(valor);
				tmpTrans.setTipoDaTransacao(3); //Tipo 3-Transferencia
				debitar(tmpTrans); tmpTrans = null;
				
				//depositar o valor na conta de destino;
				tmpTrans.setValor(transacao.getValor());
				tmpTrans.setNumerocontaDestino(contaDestino.getNumero());
				tmpTrans.setTipoDaTransacao(2); //Tipo 2-Deposito
				depositar(tmpTrans);tmpTrans = null;
				
				//Salvando no cofre da agencia DEBITADA a taxa  e salvando no banco o histórico da transferencia
				tmpTrans.setTipoDaTransacao(4);
				tmpTrans.setValor(taxaSobreEstaTrans);
				tmpTrans.setNumerocontaOrigem(contaOrigem.getNumero());
				taxacao(tmpTrans, contaOrigem, contaDestino);
				
				
			}else {
				return "Sem saldo suficiente na conta de origem !";
			}
			
			return "Transferência realizada com sucesso!";
		}
		
	}
	
	
	/* Esse metodo pode ser usado tanto para SAQUE quanto para TRANSFERÊNCIA
	 * 
	 * PARA TRANSFERENCIA: Paramestros de entrada: Objeto Transacao {NumerocontaOrigem,Valor,TipoDaTransacao}
	 * 
	 * PARA O SAQUES: Eu não faço nenhuma validação do valor que será debitado. 
	 * O valor envolvido na transação tem que chegar pronto para a alteração ser inserido no banco de dados.
	 * */
	public synchronized void debitar(Transacao transacao)throws SQLException{
		double valorTransferido=0;
		try {
			//Se entrar aqui significa que é uma transação do tipo TRANSFERÊNCIA
			if (transacao.getTipoDaTransacao() == 3) {
				//Salvando o saldo antigo
				String sqlSelect = "SELECT c.saldo FROM conta AS c WHERE c.numero_conta =?;";
				PreparedStatement ps1 = this.conexao.getConnection().prepareStatement(sqlSelect);
				ps1.setDouble(1, transacao.getNumerocontaOrigem());
				ResultSet rs = ps1.executeQuery();  rs.next();
				//Aqui eu faço o calculo para saber quanto está sendo transferido
				valorTransferido = rs.getDouble("saldo") - transacao.getValor();
			}
				
			//Debitando da conta
			String sqlUpdate = "UPDATE public.conta SET saldo=? WHERE conta.numero_conta =?;";
			PreparedStatement ps2 = this.conexao.getConnection().prepareStatement(sqlUpdate);
			ps2.setDouble(1, transacao.getValor());
			ps2.setInt(2,transacao.getNumerocontaOrigem());
			ps2.executeUpdate();
			
			//Salvar historico da transação
			String sqlHistorico = "INSERT INTO public.transacao(tipo, valor, numero_conta) VALUES (?, ?, ?);";
			PreparedStatement ps3 = this.conexao.getConnection().prepareStatement(sqlHistorico);
			ps3.setInt(1, transacao.getTipoDaTransacao());
			//se for transferencia, aqui eu estou setando no obj o valor que foi transferido para a outra conta
			if(transacao.getTipoDaTransacao() == 3){
				transacao.setValor(valorTransferido);	
			}
			ps3.setDouble(2, transacao.getValor());
			ps3.setInt(3,transacao.getNumerocontaOrigem());
			ps3.execute();
			
			
			this.conexao.close();
		} catch (Exception e) {
			System.out.println("SisBancoModel - Erro no processo de debitar: ");
			e.printStackTrace();
		}
		
	}
	
	
	public synchronized void taxacao(Transacao transacao, Conta contaOrigem, Conta contaDestino)throws SQLException{
		//1- Criar um novo resgistro vinculado ao conta de origem referente a taxa
		String sqlHistorico = "INSERT INTO transacao (tipo,valor,numero_conta) VALUES (?,?,?) RETURNING id;";
		PreparedStatement ps1 = this.conexao.getConnection().prepareStatement(sqlHistorico);
		ps1.setInt(1,transacao.getTipoDaTransacao());
		ps1.setDouble(2,transacao.getValor());
		ps1.setInt(3,transacao.getNumerocontaOrigem());
		ResultSet rs1 = ps1.executeQuery(); 	rs1.next();//Nesse cara está o ID do historico da transação
		
		//2- Salvar no cofre da agencia o valor da taxa
		//2.1 Pegando o valor que já tem no cofre
		String sqlSaldoCofre = "SELECT ag.cofre_agencia FROM agencia AS ag WHERE ag.numero_agencia=?";
		PreparedStatement ps2 = this.conexao.getConnection().prepareStatement(sqlSaldoCofre);
		ps2.setInt(1, contaOrigem.getNumero());
		ResultSet rs2 = ps2.executeQuery(); 	rs2.next();
		//estou somando o valor que já tem no cofre com o valor da taxa cobrada
		double novoSaldoCofre = rs2.getDouble("cofre_agencia") + transacao.getValor(); 
				
		//2.2 Salvando no cofre
		String sqlCofre = "UPDATE agencia SET cofre_agencia=? WHERE agencia.id=?;";
		PreparedStatement ps3 = this.conexao.getConnection().prepareStatement(sqlCofre);
		ps3.setDouble(1,novoSaldoCofre);
		ps3.setInt(2,contaOrigem.getId_agencia());
		ps3.executeUpdate();
		
		//3- Criar o registro da taxa
		String sqlHistoricoTaxa = "INSERT INTO historico_taxa_sobre_transacao "
								+ "(valor_taxa, id_historico_transacao, id_agencia_creditada) VALUES (?,?,?);";
		PreparedStatement ps4 = this.conexao.getConnection().prepareStatement(sqlHistoricoTaxa);
		ps4.setDouble(1,transacao.getValor());
		ps4.setInt(2,rs1.getInt("id"));
		ps4.setInt(3,contaDestino.getId_agencia());
		ps4.execute();
		
		this.conexao.close();
		
	}
	
}
