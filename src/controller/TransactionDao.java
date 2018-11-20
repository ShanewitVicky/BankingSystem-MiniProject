package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.Account;
import model.Transaction;
import model.User;

public class TransactionDao {
	User userData;
	Transaction transaction;
	Account account;

	Connection con = null;
	String url = "jdbc:mysql://localhost:3306/";

	String db = "banking";
	String driver = "com.mysql.jdbc.Driver";
	/**
	 * 
	 */
	public TransactionDao() {
		
	}
	
	
	public ResultSet fetchUserTransaction(String option,String userId) {
		
		ResultSet result=null;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url + db, "root", "");
				String sql="Select * from transactions WHERE usr_id=(Select usr_id from Users where user_name=?)";
				if(option!="All the accounts") {
				sql+=" AND account_id= ?";
				}
				
				sql+=" ORDER BY trans_id DESC";
				
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, userId);
				if(option!="All the accounts") {
				st.setString(2, option);
				}
				result=st.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return result;
	}
	
	
public ResultSet fetchUserTransactionByDate(String option,String userId,Date startDate,Date endDate) {
		
		ResultSet result=null;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url + db, "root", "");
				String sql="Select * from transactions WHERE usr_id=(Select usr_id from Users where user_name=?) AND date_of_transaction BETWEEN ? AND ?";
				if(option!="All the accounts") {
				sql+=" AND account_id= ?";
				}
				
				sql+=" ORDER BY trans_id DESC";
				
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, userId);
				java.sql.Date strDate=new java.sql.Date(startDate.getTime());
				java.sql.Date edDate=new java.sql.Date(endDate.getTime());
				
                st.setDate(2, strDate);	
                st.setDate(3, edDate);
				if(option!="All the accounts") {
				st.setString(4, option);
				}
				result=st.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return result;
	}
	
public ResultSet fetchUserDepositByDate(Date startDate,Date endDate) {
		
		ResultSet result=null;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url + db, "root", "");
				String sql="Select * from transactions WHERE trans_desc=? and date_of_transaction between ? and ?";
				
				
				sql+=" ORDER BY trans_id DESC";
				
				PreparedStatement st = con.prepareStatement(sql);
				
				st.setString(1, "Money Loaded to the Account");
				java.sql.Date strDate=new java.sql.Date(startDate.getTime());
				java.sql.Date edDate=new java.sql.Date(endDate.getTime());
				
                st.setDate(2, strDate);	
                st.setDate(3, edDate);
                
				result=st.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return result;
	}
	
	
/**
 * @param transaction
 * @return
 */
public boolean insertTransaction(Transaction transaction) {

	boolean flag = false;

	try {
		Class.forName(driver);
		con = DriverManager.getConnection(url + db, "root", "");

		String sql = "INSERT INTO `transactions`(`account_id`,`usr_id`, `date_of_transaction` ,`time`,`trans_desc`, `trans_type`, `amount`,`balance` ) VALUES"
				+ " (?,?,?,?,?,?,?,?)";

		PreparedStatement st = con.prepareStatement(sql);

		st.setLong(1, transaction.getAccountId());
		st.setLong(2, transaction.getUsrId());
		st.setDate(3, new java.sql.Date(transaction.getDateOfTransaction().getTime()));
		st.setString(4, transaction.getTimeOfTrans());
		st.setString(5, transaction.getTransDesc());
		st.setString(6, transaction.getTransType());
		st.setInt(7, transaction.getAmount());
		st.setInt(8, transaction.getBalance());

		int i = st.executeUpdate();

		if (i > 0) {
			flag = true;
		}
		con.close();

	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return flag;
}


}
