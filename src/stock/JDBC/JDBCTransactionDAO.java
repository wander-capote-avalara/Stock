package stock.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import stock.objects.Transaction;

public class JDBCTransactionDAO {

	private Connection connection;

	public JDBCTransactionDAO(Connection connectionR) {
		this.connection = connectionR;
	}

	public void add(Transaction tst) {
		StringBuilder stbd = new StringBuilder();

		stbd.append("INSERT INTO transactions (");
		stbd.append("productId, manufacturer, quantity, isEntry, expectedDate, deliveryDate, outputDate");
		stbd.append(") VALUES (?,?,?,?,?,?,?)");
		
		PreparedStatement p;

		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setInt(1, tst.getProductId());
			p.setString(2, tst.getManufacturer());
			p.setInt(3, tst.getQuantity());
			p.setInt(4, tst.getIsEntry());
			p.setString(5, tst.getExpectedDate());
			p.setString(6, tst.getDeliveryDate());
			p.setString(7, tst.getOutputDate());
			p.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
