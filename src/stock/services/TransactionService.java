package stock.services;

import java.sql.Connection;
import java.util.List;

import stock.JDBC.JDBCTransactionDAO;
import stock.bd.connection.ConnectionStock;
import stock.objects.Transaction;

public class TransactionService {

	public void addTransaction(Transaction tst){
		ConnectionStock conec = new ConnectionStock();
		Connection connection = conec.openConnection();
		JDBCTransactionDAO JDBCTDAO = new JDBCTransactionDAO(connection);
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
}
