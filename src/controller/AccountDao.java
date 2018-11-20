package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Account;
import model.Transaction;
import model.User;

public class AccountDao {

	User userData;
	Transaction transaction;
	Account account;

	Connection con = null;
	String url = "jdbc:mysql://localhost:3306/";

	String db = "banking";
	String driver = "com.mysql.jdbc.Driver";

	@SuppressWarnings("unused")

	public AccountDao(Account account) {
		this.account = account;
	}

	@SuppressWarnings("unused")

	public AccountDao() {

	}

	/**
	 * This Method creates the User Account and inserts a row in the database
	 * 
	 * @param acctData
	 * @return
	 */
	public boolean createAccount(Account acctData) {

		boolean flag = false;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "INSERT INTO `account`(`Account_Number`, `IBAN`, `BIC`, `City`, `Country`, `Balance`, `Account Type`,`usr_id`) "
					+ "VALUES (?,?,?,?,?,?,?,?)";

			PreparedStatement st = con.prepareStatement(sql);
			st.setLong(1, acctData.getAccountNo());
			st.setString(2, acctData.getIban());
			st.setString(3, acctData.getBic());
			st.setString(4, acctData.getCity());
			st.setString(5, acctData.getCountry());
			st.setInt(6, acctData.getBalance());
			st.setString(7, acctData.getAccountType());
			st.setInt(8, acctData.getUsrId());

			int result = st.executeUpdate();

			if (result > 0) {
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

	/**
	 * This method is used to fetch the latest and unique account number for each
	 * account created
	 * 
	 * @return
	 */
	public long generateAccountNumber() {

		long accountNumber = 0L;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "SELECT max(Account_Number)+1 from `account`";

			PreparedStatement st = con.prepareStatement(sql);

			ResultSet result = st.executeQuery();

			while (result.next()) {
				accountNumber = result.getLong(1);
			}

			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountNumber;
	}

	/**
	 * 
	 * Used to fetch the list of accounts related to the user using usr_id
	 * 
	 * @param usrdata
	 * @return Arraylist of Strings
	 */
	public ArrayList<String> fetchUserAccountList(User usrdata) {
		ArrayList<String> accountList = new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "SELECT * from `account` where usr_id=? ORDER BY Account_Number";

			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, usrdata.getUsrId());

			ResultSet result = st.executeQuery();

			while (result.next()) {
				accountList.add(result.getString(1));
			}

			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accountList;
	}

	/**
	 * Fetch the User's Account list basead on the User Name
	 * 
	 * @param userName
	 * @return ArrayList of String
	 */
	public ArrayList<String> fetchUserAccountList(String userName) {
		ArrayList<String> accountList = new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "SELECT * from `account` where usr_id=(Select usr_id from Users where user_name=?) ORDER BY Account_Number";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, userName);

			ResultSet result = st.executeQuery();

			while (result.next()) {
				accountList.add(result.getString(1));
			}

			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accountList;
	}

	/**
	 * Used to fetch the Details of a particular account selected for the selected
	 * user
	 * 
	 * @param usrdata
	 * @param accountNumber
	 * @return Object Account
	 * 
	 */
	public Account fetchUserAccountDetail(User usrdata, String accountNumber) {
		Account account = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "SELECT * from `account` where usr_id=? and Account_Number=? ";

			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, usrdata.getUsrId());
			st.setString(2, accountNumber);

			ResultSet result = st.executeQuery();
			while (result.next()) {
				account = new Account(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6), result.getString(7), result.getInt(8));

			}

			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;
	}

	/**
	 * Used to fetch the full details of the account
	 * 
	 * @param accountNumber
	 * @return Object Account
	 */
	public Account fetchAllUserAccountDetail(String accountNumber) {
		Account account = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "SELECT * from `account` where `Account_Number`=?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, accountNumber);

			ResultSet result = st.executeQuery();
			while (result.next()) {
				account = new Account(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6), result.getString(7), result.getInt(8));

			}

			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;
	}

	/**
	 * Used to fetch all the accounts in the database
	 * 
	 * @return Arraylist of Account
	 */
	public ArrayList<String> fetchAllAccountsList() {
		ArrayList<String> accountList = new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "SELECT * from `account`  ORDER BY Account_Number";

			PreparedStatement st = con.prepareStatement(sql);

			ResultSet result = st.executeQuery();

			while (result.next()) {
				accountList.add(result.getString(1));
			}

			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accountList;
	}

	/**
	 * 
	 * Used for updating the balance of the table Account whenever and cash is loaded or transfered
	 * @param Account accountselec
	 * @return true/false
	 */
	public boolean updateBalance(Account accountselec) {

		boolean flag = false;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "UPDATE `account` set `balance`=? where  `Account_Number`=? ";

			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, accountselec.getBalance());
			st.setLong(2, accountselec.getAccountNo());

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
