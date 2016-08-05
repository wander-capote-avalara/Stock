package stock.services;

import java.sql.Connection;
import java.util.List;

import stock.JDBC.JDBCProductDAO;
import stock.JDBC.JDBCTransactionDAO;
import stock.bd.connection.ConnectionStock;
import stock.objects.Product;

public class ProductService {

	public List<Product> getProducts() {
		ConnectionStock conec = new ConnectionStock();
		Connection connection = conec.openConnection();
		JDBCProductDAO JDBCPDAO = new JDBCProductDAO(connection);
		List<Product> lp = JDBCPDAO.getProducts();
		conec.closeConnection();
		return lp;
	}

}
