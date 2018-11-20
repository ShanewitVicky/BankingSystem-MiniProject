package model;

import java.util.Date;

public class Transaction {
	
	private long transactionId;
	private long accountId;
	private long usrId;
	private Date dateOfTransaction;
	private String timeOfTrans;
	private String transDesc;
	private String TransType;
	private int amount;
	private int balance;
	
	
	
	
	
	
/**
	 * @param accountId
	 * @param usrId
	 * @param dateOfTransaction
	 * @param timeOfTrans
	 * @param transDesc
	 * @param transType
	 * @param amount
	 * @param balance
	 */
	public Transaction(long accountId, long usrId, Date dateOfTransaction, String timeOfTrans, String transDesc,
			String transType, int amount, int balance) {
		
		this.accountId = accountId;
		this.usrId = usrId;
		this.dateOfTransaction = dateOfTransaction;
		this.timeOfTrans = timeOfTrans;
		this.transDesc = transDesc;
		TransType = transType;
		this.amount = amount;
		this.balance = balance;
	}
	
	
	
	/**
 * @param transactionId
 * @param accountId
 * @param usrId
 * @param dateOfTransaction
 * @param timeOfTrans
 * @param transDesc
 * @param transType
 * @param amount
 * @param balance
 */
public Transaction(long transactionId, long accountId, long usrId, Date dateOfTransaction, String timeOfTrans,
		String transDesc, String transType, int amount, int balance) {
	super();
	this.transactionId = transactionId;
	this.accountId = accountId;
	this.usrId = usrId;
	this.dateOfTransaction = dateOfTransaction;
	this.timeOfTrans = timeOfTrans;
	this.transDesc = transDesc;
	TransType = transType;
	this.amount = amount;
	this.balance = balance;
}



	/**
 * @return the transactionId
 */
public long getTransactionId() {
	return transactionId;
}



/**
 * @param transactionId the transactionId to set
 */
public void setTransactionId(long transactionId) {
	this.transactionId = transactionId;
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
	 * @return the dateOfTransaction
	 */
	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}
	/**
	 * @param dateOfTransaction the dateOfTransaction to set
	 */
	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	/**
	 * @return the timeOfTrans
	 */
	public String getTimeOfTrans() {
		return timeOfTrans;
	}
	/**
	 * @param timeOfTrans the timeOfTrans to set
	 */
	public void setTimeOfTrans(String timeOfTrans) {
		this.timeOfTrans = timeOfTrans;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return the transDesc
	 */
	public String getTransDesc() {
		return transDesc;
	}
	/**
	 * @param transDesc the transDesc to set
	 */
	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}
	/**
	 * @return the transType
	 */
	public String getTransType() {
		return TransType;
	}
	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		TransType = transType;
	}
	
	


	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the usrId
	 */
	public long getUsrId() {
		return usrId;
	}
	/**
	 * @param usrId the usrId to set
	 */
	public void setUsrId(long usrId) {
		this.usrId = usrId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transaction [DateOfTransaction:" + this.getDateOfTransaction() + "TimeOfTrans:"
				+ this.getTimeOfTrans() + "getAmount:" + this.getAmount() + "getTransDesc:" + this.getTransDesc()
				+ "TransType:" + this.getTransType() + "]";
	}
	
	
	
	

}
