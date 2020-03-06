import java.sql.Connection;
import java.sql.SQLException;

public interface ConexaoJDBC {
	
	public Connection getConnection();
	
	public void close();
	
}