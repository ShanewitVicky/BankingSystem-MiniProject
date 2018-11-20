/**
 * 
 */
package model;

import java.util.Date;

/**
 * @author Shane
 *
 */
public class User {
	
	private int usrId;
	private String usrName;
	private String firstName;
	private String lastName;
	private String password;
	private String emailId;
	private String phNumber1;
	private String phNumber2;
	private String address;
	private Date dob;
	private String role;
	private byte[] salt;
	
	/**
	 * @param usrName
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param emailId
	 * @param phNumber1
	 * @param phNumber2
	 * @param address
	 * @param dob
	 * @param role
	 */
	public User(String usrName, String password, String firstName, String lastName, String emailId, String phNumber1,
			String phNumber2, String address, Date dob, String role) {
		
		this.usrName = usrName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.emailId = emailId;
		this.phNumber1 = phNumber1;
		this.phNumber2 = phNumber2;
		this.address = address;
		this.dob = dob;
		this.role = role;
	}
	
	
	
	

	/**
	 * @param usrName
	 * @param password
	 */
	public User(String usrName, String password) {
		super();
		this.usrName = usrName;
		this.password = password;
	}
	
	




	/**
	 * @param usrId
	 * @param usrName
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param emailId
	 * @param phNumber1
	 * @param phNumber2
	 * @param address
	 * @param dob
	 * @param role
	 * @param salt
	 */
	public User(int usrId, String usrName,String password, String firstName, String lastName,  String emailId,
			String phNumber1, String phNumber2, String address, Date dob, String role) {
		
		this.usrId = usrId;
		this.usrName = usrName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.emailId = emailId;
		this.phNumber1 = phNumber1;
		this.phNumber2 = phNumber2;
		this.address = address;
		this.dob = dob;
		this.role = role;
	}
	
	
	public User() {
		
	}




  
	/**
	 * @return the usrId
	 */
	public int getUsrId() {
		return usrId;
	}





	/**
	 * @param usrId the usrId to set
	 */
	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}





	/**
	 * @return the usrName
	 */
	public String getUsrName() {
		return usrName;
	}
	/**
	 * @param usrName the usrName to set
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the password
	 */
	public String getPasswordField() {
		return String.valueOf(password);
	}
	/**
	 * @param password the password to set
	 */
	public void setPasswordField(String password) {
		this.password = password;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the phNumber1
	 */
	public String getPhNumber1() {
		return phNumber1;
	}
	/**
	 * @param phNumber1 the phNumber1 to set
	 */
	public void setPhNumber1(String phNumber1) {
		this.phNumber1 = phNumber1;
	}
	/**
	 * @return the phNumber2
	 */
	public String getPhNumber2() {
		return phNumber2;
	}
	/**
	 * @param phNumber2 the phNumber2 to set
	 */
	public void setPhNumber2(String phNumber2) {
		this.phNumber2 = phNumber2;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
		/**
	 * @return the salt
	 */
	public byte[] getSalt() {
		return salt;
	}





	/**
	 * @param salt the salt to set
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserName" + this.getUsrName() + "FirstName=" + this.getFirstName() + "LastName="
				+ this.getLastName() + ",Password=" + this.getPasswordField() + "EmailId=" + this.getEmailId()
				+ "PhNumber1=" + this.getPhNumber1() + "PhNumber2=" + this.getPhNumber2() + "Address"
				+ this.getAddress() + "Date of Birth=" + this.getDob() + "Role" + this.getRole() + "]";
	}	
	
	
	
	

}
