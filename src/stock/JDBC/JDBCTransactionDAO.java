package stock.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import stock.objects.Product;
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

			insertIntoStock(tst.getQuantity(), tst.getProductId(), tst.getIsEntry());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertIntoStock(int quantity, int productId, int isEntry) {
		StringBuilder stbd = new StringBuilder();
		Integer qtt = existInStock(productId);
		stbd.append( qtt != null ? "UPDATE stock SET products_id = ?, quantity = ? WHERE products_id = ?" : "INSERT INTO stock (products_id, quantity) VALUES (?,?)");
			
		PreparedStatement p;

		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setInt(1, productId);		
			if(qtt != null)
				p.setInt(3, productId);	
			else
				qtt = 0;
			p.setInt(2, isEntry == 0 ? qtt+quantity : qtt-quantity);	
			p.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer existInStock(int productId) {
		StringBuilder stbd = new StringBuilder();

		stbd.append("SELECT quantity AS qtt FROM stock WHERE products_id = ?");

		PreparedStatement p;
		ResultSet rs = null;

		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setInt(1, productId);
			rs = p.executeQuery();
			while(rs.next()){
				return rs.getInt("qtt");
			}
			return null;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Transaction> getTransactions(int isEntry){
		StringBuilder stbd = new StringBuilder();
		stbd.append("SELECT id AS pid, productId AS prodId, manufacturer AS manufac, quantity AS qtt, ");
		stbd.append(isEntry == 0 ? "expectedDate AS eDate, deliveryDate as dDate" : "outputDate AS oDate");
		stbd.append(" FROM transactions");
		stbd.append(" WHERE isEntry = ?");

		PreparedStatement p;
		ResultSet rs = null;
		List<Transaction> lt = new ArrayList<Transaction>();
		
		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setInt(1, isEntry);
			rs = p.executeQuery();
			
			while(rs.next()){
				Transaction tt = new Transaction();
				
				tt.setId(rs.getInt("pid"));
				tt.setProductId(rs.getInt("prodId"));
				tt.setManufacturer(rs.getString("manufac"));
				tt.setQuantity(rs.getInt("qtt"));
				if (isEntry == 0) {
					tt.setExpectedDate(rs.getString("eDate"));
					tt.setDeliveryDate(rs.getString("dDate"));
				}else{
					tt.setOutputDate(rs.getString("oDate"));
				}
				
				lt.add(tt);
			}
			
			for (Transaction tsct : lt) {
				tsct.setProductName(getProductNameById(tsct.getProductId()));
			}
			
			return lt;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	private String getProductNameById(int productId) {
		StringBuilder stbd = new StringBuilder();

		stbd.append("SELECT model AS mdl FROM products WHERE id = ?");

		PreparedStatement p;
		ResultSet rs = null;

		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setInt(1, productId);
			rs = p.executeQuery();
			while(rs.next()){
				return rs.getString("mdl");
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void transactionArrived(int id){
		StringBuilder stbd = new StringBuilder();
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");		
		String text = date.format(Calendar.getInstance().getTime()).replace("-", "/");
		stbd.append("UPDATE transactions SET deliveryDate=? WHERE id = ?");

		PreparedStatement p;

		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setString(1, text);
			p.setInt(2, id);			
			p.execute();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> getStock(int id){
		StringBuilder stbd = new StringBuilder();
		stbd.append("SELECT p.model AS mdl, s.quantity AS qtt ");
		stbd.append("FROM stock s ");
		stbd.append("INNER JOIN products p ON p.id = s.products_id ");
		if(id != 0)
			stbd.append("WHERE p.id = ? ");
		stbd.append("ORDER BY s.quantity ASC");

		PreparedStatement p;
		ResultSet rs = null;
		List<Product> lp = new ArrayList<Product>();
		
		try {
			p = this.connection.prepareStatement(stbd.toString());
			if(id != 0)
				p.setInt(1, id);
			
			rs = p.executeQuery();
			
			while(rs.next()){
				Product prod = new Product();
				
				prod.setModel(rs.getString("mdl"));
				prod.setQuantity(rs.getInt("qtt"));
				
				lp.add(prod);
			}
			
			return lp;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public boolean checkTransaction(Transaction tst) {
		StringBuilder stbd = new StringBuilder();
		stbd.append("SELECT id AS tId ");
		stbd.append("FROM transactions ");
		stbd.append("WHERE productId = ? AND manufacturer = ? ");
		stbd.append("AND isEntry = ? AND deliveryDate = ''");


		PreparedStatement p;
		ResultSet rs = null;
		List<Product> lp = new ArrayList<Product>();
		
		try {
			p = this.connection.prepareStatement(stbd.toString());
			p.setInt(1, tst.getProductId());	
			p.setString(2, tst.getManufacturer());
			p.setInt(3, tst.getIsEntry());
			rs = p.executeQuery();
			
			while(rs.next()){
				return true;
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
