/**
 * 
 */
package model;

/**
 * @author Shane
 *
 */
public class Account {
	
	private long accountNo;
	private String iban;
	private String bic;
	private String city;
	private String country;
	private int balance;
	private String accountType;
	private int usrId;
	
	
	
	
	
	
	
	/**
	 * @param accountNo
	 * @param iban
	 * @param bic
	 * @param city
	 * @param country
	 * @param balance
	 * @param accountType
	 */
	public Account(long accountNo, String iban, String bic, String city, String country, int balance,
			String accountType,int usrId) {
		this.accountNo = accountNo;
		this.iban = iban;
		this.bic = bic;
		this.city = city;
		this.country = country;
		this.balance = balance;
		this.accountType = accountType;
		this.usrId=usrId;
	}
	
	
	/**
	 * @return the accountNo
	 */
	public long getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}
	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}
	/**
	 * @return the bic
	 */
	public String getBic() {
		return bic;
	}
	/**
	 * @param bic the bic to set
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}
	/**
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [IBan:" + this.getIban() + "Bic:" + this.getBic() + "Balance:" + this.getBalance()
				+ "AccountType:" + this.getAccountType() + "]";
	}
	
	
	

}
