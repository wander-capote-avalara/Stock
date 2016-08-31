package stock.bd.connection;

import java.sql.Connection;

public class ConnectionStock {

	private Connection connectionStock;

	public Connection openConnection() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			connectionStock = java.sql.DriverManager
					.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connectionStock;
	}
	
	public void closeConnection() {
		try {
			connectionStock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
