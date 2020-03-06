import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgresJDBC implements ConexaoJDBC{
	
	private Connection connection =null;
	
	public ConexaoPostgresJDBC() throws ClassNotFoundException, SQLException {

			Class.forName("org.postgresql.Driver");
			
			String url = "jdbc:postgresql://localhost:5432/sisbanco";
			String usuario = "postgres"; 
			String senha = "1q2w3e4r5t";
			
			this.connection = DriverManager.getConnection(url, usuario, senha);
	}

	@Override
	public Connection getConnection() {
		return this.connection ;
	}

	@Override
	public void close() {
		if(this.connection != null){
			try {
				this.connection.close();
			} catch (Exception e) {
				System.out.println("Erro ao fechar a conexão: "+e);
			}
		}
	}

}
