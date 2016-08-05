package stock.services;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import stock.JDBC.JDBCTransactionDAO;
import stock.bd.connection.ConnectionStock;
import stock.objects.Product;
import stock.objects.Transaction;

public class TransactionService {

	public void addTransaction(Transaction tst) throws Exception{
		ConnectionStock conec = new ConnectionStock();
		Connection connection = conec.openConnection();
		JDBCTransactionDAO JDBCTDAO = new JDBCTransactionDAO(connection);
		if(JDBCTDAO.checkTransaction(tst) && tst.getIsEntry() == 0)
			throw new Exception("Same request is already done!");
		int inStock = JDBCTDAO.existInStock(tst.getProductId()) == null ? 0 : JDBCTDAO.existInStock(tst.getProductId());
		if (tst.getIsEntry() == 0 && inStock >= 10)
			throw new Exception("There's more than 10 units in stock! In stock: "+inStock);
		else if(tst.getIsEntry() == 1 && (inStock - tst.getQuantity()) < 0)
			throw new Exception("Not enough units! Units available: "+inStock);
		else
			JDBCTDAO.add(tst);
				
		conec.closeConnection();
	}

	public List<Transaction> getTransactions(int isEntry) {
		ConnectionStock conec = new ConnectionStock();
		Connection connection = conec.openConnection();
		JDBCTransactionDAO JDBCTDAO = new JDBCTransactionDAO(connection);
		List<Transaction> lt = JDBCTDAO.getTransactions(isEntry);
		conec.closeConnection();
		return lt;
	}
	
	public List<Product> getStock(int id) {
		ConnectionStock conec = new ConnectionStock();
		Connection connection = conec.openConnection();
		JDBCTransactionDAO JDBCTDAO = new JDBCTransactionDAO(connection);
		List<Product> lp = JDBCTDAO.getStock(id);
		conec.closeConnection();
		return lp;
	}

	public void transactionArrived(int id) {
		ConnectionStock conec = new ConnectionStock();
		Connection connection = conec.openConnection();
		JDBCTransactionDAO JDBCTDAO = new JDBCTransactionDAO(connection);
		JDBCTDAO.transactionArrived(id);
		conec.closeConnection();
	}
}
