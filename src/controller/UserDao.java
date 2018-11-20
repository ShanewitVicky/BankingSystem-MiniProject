package controller;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Decoder.BASE64Encoder;
import model.Account;
import model.Transaction;
import model.User;


public class UserDao {

	User userData;
	Transaction transaction;
	Account account;
	
	
	Connection con = null;
	String url = "jdbc:mysql://localhost:3306/";

	String db = "banking";
	String driver = "com.mysql.jdbc.Driver";

	/**
	 * @param userData
	 */
	public UserDao(User userData) {
		this.userData = userData;
	}
	
	public UserDao() {
		
	}

	public boolean loginUser(User usrData) {

		boolean flag=false;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "Select * from users where User_Name=?";

			PreparedStatement prep = con.prepareStatement(sql);

			prep.setString(1, usrData.getUsrName());

			ResultSet result = prep.executeQuery();
			while (result.next()) {

				String hash = result.getString(3).toString();
				String password = usrData.getPasswordField();
				

				
				
				String encryptedPassword=generateHash(password).toString();
				
				
				
				if(encryptedPassword.equals(hash)) {
					flag=true;
				}
				else {
					flag=false;
				}
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

	@SuppressWarnings("unused")

	public void registerUser(User usrData) {

		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "INSERT INTO `users`(`User_Name`,`password`, `First_Name`, `Last_Name`, `Email_id`, `Phone_Number1`, `Phone_Number2`,"
					+ " `Address`, `Date_Of_Birth`, `Role`) VALUES " + "(?,?,?,?,?,?,?,?,?,?)";

			//System.out.println(usrData.toString());
			String encdPwd=generateHash(usrData.getPasswordField());
			//System.out.println("Enter Hashed Password:"+encdPwd);
			
			
			PreparedStatement st = con.prepareStatement(sql);
            //System.out.println(encdPwd.toString());
			st.setString(1, usrData.getUsrName());
			st.setString(2, encdPwd.toString());
			st.setString(3, usrData.getFirstName());
			st.setString(4, usrData.getLastName());
			st.setString(5, usrData.getEmailId());
			st.setString(6, usrData.getPhNumber1());
			st.setString(7, usrData.getPhNumber2());
			st.setString(8, usrData.getAddress());

			java.sql.Date date = new java.sql.Date(usrData.getDob().getTime());

			st.setDate(9, date);
			st.setString(10, usrData.getRole());

			st.executeUpdate();

			//System.out.println("Row Inserted");
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static String SALT="akjbehg3247dfdshga";
	MessageDigest sha;
	//EncryptInput input=new EncryptInput();
	
	
	public String hashPwd(String password) {
		String saltedpassword=SALT+password;
		String hashedpassword=generateHash(saltedpassword);
		
		return hashedpassword;
		
		
		
	} 
	
	public User loadUserData(User usrData) {
		User usrDbData=new User();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "Select * from users where User_Name=?";

			PreparedStatement prep = con.prepareStatement(sql);

			prep.setString(1, usrData.getUsrName());

			ResultSet result = prep.executeQuery();
			while (result.next()) {
				System.out.println(result.getInt(1)+result.getString(2)+result.getString(3)+result.getString(4)+
						result.getString(5)+ result.getString(6)+result.getString(7)+result.getString(8)+result.getString(9)+result.getDate(10)+ result.getString(11));
				usrDbData=new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getString(6), result.getString(7), result.getString(8),result.getString(9), result.getDate(10), result.getString(11));

				}
				
				

			
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usrDbData;
	}
	
	public User loadUserDataByUserName(String userName) {
		User usrDbData=new User();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "Select * from users where User_Name=?";

			PreparedStatement prep = con.prepareStatement(sql);

			prep.setString(1, userName);

			ResultSet result = prep.executeQuery();
			while (result.next()) {
				
				usrDbData=new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getString(6), result.getString(7), result.getString(8),result.getString(9), result.getDate(10), result.getString(11));

				}
				
				

			
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usrDbData;
	}
	
	
	
	public ArrayList<String> fetchUserData() {
		ResultSet result=null;
		ArrayList<String> usrNameList=new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "Select * from users";

			PreparedStatement prep = con.prepareStatement(sql);

			

			result = prep.executeQuery();
			while(result.next()) {
				usrNameList.add(result.getString(2));
			}
				
				

			
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usrNameList;
	}
	
	public synchronized String generateHash(String input) {
		String hash =null;

		try {
			sha = MessageDigest.getInstance("SHA-1");
			sha.update(input.getBytes("UTF-8"));
			byte rawbyte[]=sha.digest();
			hash=(new BASE64Encoder()).encode(rawbyte);
			
			}catch(UnsupportedEncodingException e) {
				
			}
		 catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash;
	}
	
	
public boolean updateUser(User usrData) {

		boolean flag=false;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, "root", "");

			String sql = "UPDATE `users` SET `User_Name`=?,`First_Name`=?,`Last_Name`=?,`Email_id`=?,`Phone_Number1`=?,"
					      + "`Phone_Number2`=?,`Address`=?,`Date_Of_Birth`=? WHERE `Usr_id`=?";

			/*System.out.println(usrData.toString());*/
			/*String encdPwd=generateHash(usrData.getPasswordField());*/
			/*System.out.println("Enter Hashed Password:"+encdPwd);*/
			
			
			PreparedStatement st = con.prepareStatement(sql);
            /*System.out.println(encdPwd.toString());*/
			st.setString(1, usrData.getUsrName());
			st.setString(2, usrData.getFirstName());
			st.setString(3, usrData.getLastName());
			st.setString(4, usrData.getEmailId());
			st.setString(5, usrData.getPhNumber1());
			st.setString(6, usrData.getPhNumber2());
			st.setString(7, usrData.getAddress());

			java.sql.Date date = new java.sql.Date(usrData.getDob().getTime());

			st.setDate(8, date);
			st.setInt(9, usrData.getUsrId());

			int updateCount=st.executeUpdate();
            if(updateCount>0) {
            	flag=true;
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
