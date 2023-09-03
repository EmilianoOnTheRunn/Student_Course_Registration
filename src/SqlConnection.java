import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class SqlConnection {
	
	
Connection connection = null;
	
	public static Connection dbConnector() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = DriverManager.getConnection("jdbc:sqlite:Studentdb.db");
			JOptionPane.showMessageDialog(null, "Connection Succesfully Established.");
			return connection;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
			
		}
		
		
	}

}
