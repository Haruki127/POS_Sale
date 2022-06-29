package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconfig {
	
	private final String CONNECTION = "jdbc:mysql://localhost:3306/employee";
	private final String PASSWORD = "root";
	private static Connection con = null;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException{
		if(con == null) {
			con = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/employee","root","root"); 
		}
		return con;
	}

}
