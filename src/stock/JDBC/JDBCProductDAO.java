package stock.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import stock.objects.Product;

public class JDBCProductDAO {

	private Connection connection;

	public JDBCProductDAO(Connection connectionR) {
		this.connection = connectionR;
	}
	
	public List<Product> getProducts() {
		StringBuilder stbd = new StringBuilder();
		stbd.append("SELECT id AS prodId, model AS mdl ");
		stbd.append("FROM products ");

		PreparedStatement p;
		ResultSet rs = null;
		List<Product> lp = new ArrayList<Product>();
		
		try {
			p = this.connection.prepareStatement(stbd.toString());		
			rs = p.executeQuery();
			
			while(rs.next()){
				Product prod = new Product();

				prod.setId(rs.getInt("prodId"));
				prod.setModel(rs.getString("mdl"));
				
				lp.add(prod);
			}
			
			return lp;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
