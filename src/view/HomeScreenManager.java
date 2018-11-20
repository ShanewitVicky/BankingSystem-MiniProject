package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.AccountDao;
import controller.TransactionDao;
import controller.UserDao;
import model.Account;
import model.Transaction;
import model.User;
import net.proteanit.sql.DbUtils;
import utility.CSVFileWriter;
import utility.CreateReport;
import java.awt.Label;
import java.awt.Button;

public class HomeScreenManager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel frame;
	private JTextField firstNametxt;
	private JTextField lastNameTxt;
	private JTextField userNametxt;
	private JTextField emailTxt;
	private JTextField phoneNumber1txt;
	private JTextField phoneNumber2;
	private JTextField addressTxt;
	private String[] cols = { "Col 1", "Col2" };
	private String[] transHeader = { "Transaction Id", "Account Id", "User Id", "Date Of transaction", "Time",
			"Transaction Description", "Transaction Type", "Amount", "Balance" };
	private String[] depositHeader = { "Start Date", "End Date", "Deposit Amount" };
	private String[] userHeader = { "Item", "Value" };
	private String[][] userValuedata = { { "User Id", "Select a User Id" }, { "First Name", "-" }, { "Last Name", "-" },
			{ "User Name", "-" }, { "Phone Number 1", "-" }, { "Phone Number 2", "-" }, { "Address", "-" },
			{ "Email Id", "-" } };
	private String[][] data = { { "Account Number", "Select an Account Number from the List" },
			{ "Account Holder", "-" }, { "IBAN", "-" }, { "BIC", "-" }, { "Balance", "-" }, { "Account Type", "-" },
			{ "City", "-" }, { "Country", "-" } };
	private String[][] dataExport = { {} };
	private JScrollPane jscrpane;
	private JScrollPane jscrClient;
	private String customer;

	DefaultTableModel model = new DefaultTableModel(data, cols);
	DefaultTableModel exportmodel = new DefaultTableModel(dataExport, transHeader);
	DefaultTableModel usermodel = new DefaultTableModel(userValuedata, userHeader);
	DefaultTableModel depositModel = new DefaultTableModel(dataExport, depositHeader);
	private ArrayList<Transaction> transactions;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		HomeScreenManager frame = new HomeScreenManager(new User());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	private static long accountNo = 7680028L;
	private JTable table;
	private JTextField amountTxt;
	private JTextField transAmount;
	private JTable clienttransTable;
	private JTable transTable;
	private ResultSet result;
	private JTable table_1;
	private JTable depositTable;

	public static synchronized String createID() {
		return String.valueOf(accountNo++);
	}

	public HomeScreenManager(User usrData) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 502);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(20, 13, 750, 53);
		frame.add(panel);
		panel.setLayout(null);

		JLabel bankTitle = new JLabel("Argents Bank");
		bankTitle.setBounds(301, 0, 118, 22);
		bankTitle.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(bankTitle);

		JLabel pageTitle = new JLabel("Homepage");
		pageTitle.setBounds(327, 27, 62, 15);
		panel.add(pageTitle);
		pageTitle.setFont(new Font("Arial", Font.ITALIC, 13));

		Label label = new Label(usrData.getUsrName());
		label.setFont(new Font("Calibri Light", Font.BOLD, 15));
		label.setBounds(611, 20, 62, 22);
		panel.add(label);

		Button button = new Button("Logout");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login logout = new Login();
				setVisible(false);
				logout.setVisible(true);
			}
		});
		button.setBounds(670, 20, 70, 22);
		panel.add(button);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 77, 456, 375);
		frame.add(tabbedPane);

		JPanel Home = new JPanel();
		tabbedPane.addTab("Home", null, Home, null);
		Home.setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome!");
		lblWelcome.setBounds(135, 26, 183, 50);
		lblWelcome.setFont(new Font("Calibri Light", Font.BOLD, 40));
		Home.add(lblWelcome);

		JLabel lblUsrName = new JLabel(usrData.getUsrName());
		lblUsrName.setFont(new Font("Calibri Light", Font.BOLD, 13));
		lblUsrName.setBounds(183, 87, 60, 30);
		Home.add(lblUsrName);

		JPanel export = new JPanel();
		export.setSize(100, 100);
		tabbedPane.addTab("Export Recent Transactons", null, export, null);
		export.setLayout(new BorderLayout(0, 0));

		JPanel exportHeader = new JPanel();
		export.add(exportHeader, BorderLayout.NORTH);

		JLabel lblExportTransaction = new JLabel("Export Transaction");
		lblExportTransaction.setFont(new Font("Calibri Light", Font.BOLD, 30));
		exportHeader.add(lblExportTransaction);

		JLabel lblSelectAccount_1 = new JLabel("Select Account");
		exportHeader.add(lblSelectAccount_1);
		lblSelectAccount_1.setFont(new Font("Calibri Light", Font.BOLD, 20));

		JPanel exportAccountOption = new JPanel();
		exportHeader.add(exportAccountOption);
		exportAccountOption.setLayout(new GridLayout(0, 2));

		JComboBox<String> exportSelectAccount = new JComboBox<String>();
		exportSelectAccount.addItem("--Select Account--");
		exportSelectAccount.addItem("All the accounts");
		exportAccountOption.add(exportSelectAccount);

		JPanel exportBody = new JPanel();
		exportHeader.add(exportBody);
		exportBody.setLayout(new GridLayout(1, 0));

		JPanel exporttable = new JPanel();
		export.add(exporttable, BorderLayout.SOUTH);

		JButton btnExport = new JButton("Export");

		exporttable.add(btnExport);

		JButton btnCancel_4 = new JButton("Cancel");
		btnCancel_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		exporttable.add(btnCancel_4);

		JPanel tablepanel = new JPanel();
		export.add(tablepanel, BorderLayout.CENTER);
		tablepanel.setLayout(new BorderLayout(0, 0));

		transTable = new JTable(exportmodel);
		jscrpane = new JScrollPane(transTable);
		tablepanel.add(transTable.getTableHeader(), BorderLayout.NORTH);
		tablepanel.add(jscrpane, BorderLayout.CENTER);

		AccountDao acctexpotyListDBld = new AccountDao();
		ArrayList<String> listExportld = acctexpotyListDBld.fetchUserAccountList(usrData);
		for (String strItem : listExportld) {
			exportSelectAccount.addItem(strItem);

		}

		exportSelectAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (exportSelectAccount.getSelectedItem().toString() != "--Select Account--") {
					TransactionDao trans = new TransactionDao();

					result = trans.fetchUserTransaction(exportSelectAccount.getSelectedItem().toString(),
							usrData.getUsrName());

					transTable.setModel(DbUtils.resultSetToTableModel(result));

				} else {
					transTable.setModel(exportmodel);
				}

			}
		});

		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnExport) {
					if (exportSelectAccount.getSelectedItem().toString().equals("--Select Account--")) {

						JOptionPane.showMessageDialog(null, "Please Select the Account to Export");
					} else {
						CSVFileWriter writeCsv = new CSVFileWriter();
						try {
							TransactionDao trans = new TransactionDao();
							result = trans.fetchUserTransaction(exportSelectAccount.getSelectedItem().toString(),
									usrData.getUsrName());
							while (result.next()) {
								writeCsv.convertToCsv(result);
							}
							JOptionPane.showMessageDialog(null, "File Successfully written!!!");
						} catch (FileNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});

		JPanel myInfo = new JPanel();
		tabbedPane.addTab("My Info", null, myInfo, null);
		myInfo.setLayout(new GridLayout(12, 2));

		JLabel lblFirstname = new JLabel("First Name");
		myInfo.add(lblFirstname);

		firstNametxt = new JTextField();
		firstNametxt.setText(usrData.getFirstName());
		myInfo.add(firstNametxt);
		firstNametxt.setColumns(2);

		JLabel lblLastName = new JLabel("Last Name");
		myInfo.add(lblLastName);

		lastNameTxt = new JTextField();
		lastNameTxt.setText(usrData.getLastName());
		myInfo.add(lastNameTxt);
		lastNameTxt.setColumns(10);

		JLabel lblUserName = new JLabel("User Name");
		myInfo.add(lblUserName);

		userNametxt = new JTextField();
		userNametxt.setText(usrData.getUsrName());
		myInfo.add(userNametxt);
		userNametxt.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		myInfo.add(lblEmail);

		emailTxt = new JTextField();
		emailTxt.setText(usrData.getEmailId());
		myInfo.add(emailTxt);
		emailTxt.setColumns(10);

		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		myInfo.add(lblDateOfBirth);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDate(usrData.getDob());
		myInfo.add(dateChooser);

		JLabel lblPhoneNumber1 = new JLabel("Phone Number 1");
		myInfo.add(lblPhoneNumber1);

		phoneNumber1txt = new JTextField();
		phoneNumber1txt.setText(usrData.getPhNumber1());
		myInfo.add(phoneNumber1txt);
		phoneNumber1txt.setColumns(10);

		JLabel lblPhoneNumber2 = new JLabel("Phone Number 2");
		myInfo.add(lblPhoneNumber2);

		phoneNumber2 = new JTextField();
		phoneNumber2.setText(usrData.getPhNumber2());

		myInfo.add(phoneNumber2);
		phoneNumber2.setColumns(10);

		JLabel lblAddress = new JLabel("Address");
		myInfo.add(lblAddress);

		this.setSize(800, 500);
		tabbedPane.setSize(750, 300);

		addressTxt = new JTextField();
		addressTxt.setText(usrData.getAddress());
		myInfo.add(addressTxt);
		addressTxt.setColumns(10);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btnUpdate) {

					User usrUpdateData = new User(usrData.getUsrId(), userNametxt.getText(), usrData.getPasswordField(),
							firstNametxt.getText(), lastNameTxt.getText(), emailTxt.getText(),
							phoneNumber1txt.getText(), phoneNumber2.getText(), addressTxt.getText(),
							dateChooser.getDate(), usrData.getRole());
					UserDao update = new UserDao(usrUpdateData);
					boolean result = update.updateUser(usrUpdateData);
					if (result == true) {
						JOptionPane.showMessageDialog(null, "The Data has been updated!!!");
					} else {
						JOptionPane.showMessageDialog(null, "Oops Something went wrong the data didn't get updated.");
					}
				}
			}
		});
		myInfo.add(btnUpdate);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		myInfo.add(btnCancel);

		JPanel loadCash = new JPanel();
		tabbedPane.addTab("Load Cash", null, loadCash, null);
		loadCash.setLayout(new BorderLayout(0, 0));

		JPanel loadcashHeaderPanel = new JPanel();
		loadCash.add(loadcashHeaderPanel, BorderLayout.NORTH);

		JLabel lblLoadCash = new JLabel("Load Cash ");
		lblLoadCash.setFont(new Font("Calibri Light", Font.BOLD, 30));
		loadcashHeaderPanel.add(lblLoadCash);

		JPanel panel_5 = new JPanel();
		loadCash.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new GridLayout(3, 2));

		JLabel lblSelectAccountld = new JLabel("Select Account");
		lblSelectAccountld.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panel_5.add(lblSelectAccountld);

		JComboBox<String> accountListld = new JComboBox<String>();
		AccountDao acctListDBld = new AccountDao();
		ArrayList<String> listld = acctListDBld.fetchUserAccountList(usrData);
		accountListld.addItem("--Select Account--");
		for (String strItem : listld) {
			accountListld.addItem(strItem);

		}
		panel_5.add(accountListld);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panel_5.add(lblAmount);

		amountTxt = new JTextField();
		panel_5.add(amountTxt);
		amountTxt.setColumns(10);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnLoad) {
					if (accountListld.getSelectedItem().toString() != "--Select Account--") {
						if (!amountTxt.getText().isEmpty()) {
							AccountDao acctld = new AccountDao();
							Account selecAccount = acctld.fetchUserAccountDetail(usrData,
									accountListld.getSelectedItem().toString());

							int amount = Integer.parseInt(amountTxt.getText());

							int balance = selecAccount.getBalance();

							balance += amount;

							selecAccount.setBalance(balance);

							SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
							String time = localDateFormat.format(new Date());

							String description = "Money Loaded to the Account ";
							String transType = "Credit";

							Transaction trans = new Transaction(selecAccount.getAccountNo(), usrData.getUsrId(),
									new Date(), time, description, transType, amount, balance);
                            TransactionDao transdb=new TransactionDao();
							if (acctld.updateBalance(selecAccount) && transdb.insertTransaction(trans)) {

								JOptionPane.showMessageDialog(null,
										"Account Balance Updated!! The Balance is" + balance);
								HomeScreenManager home = new HomeScreenManager(usrData);
								setVisible(false);
								home.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(null,
										"Oops Something went wrong the data didn't get updated.Please try again later");
							}
						} else {
							JOptionPane.showMessageDialog(null, "The Amount cannot be empty!!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Select an Account Number to add!!");
					}

				}

			}
		});
		panel_5.add(btnLoad);

		JButton btnCancel_2 = new JButton("Cancel");
		btnCancel_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		panel_5.add(btnCancel_2);

		JPanel myAccounts = new JPanel();
		tabbedPane.addTab("My Accounts", null, myAccounts, null);
		myAccounts.setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		myAccounts.add(headerPanel, BorderLayout.NORTH);

		JLabel lblMyAccounts = new JLabel("My Accounts");
		lblMyAccounts.setFont(new Font("Calibri Light", Font.BOLD, 30));
		headerPanel.add(lblMyAccounts);

		JPanel detailpanel = new JPanel();
		myAccounts.add(detailpanel, BorderLayout.CENTER);
		detailpanel.setLayout(new BorderLayout(0, 0));

		JPanel accountSelection = new JPanel();
		detailpanel.add(accountSelection, BorderLayout.NORTH);
		accountSelection.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblSelectAccount = new JLabel("Select Account");
		lblSelectAccount.setFont(new Font("Calibri Light", Font.BOLD, 18));
		accountSelection.add(lblSelectAccount);

		JComboBox<String> accountList = new JComboBox<String>();
		AccountDao acctListDB = new AccountDao();
		ArrayList<String> list = acctListDB.fetchUserAccountList(usrData);
		accountList.addItem("--Select Account--");
		for (String strItem : list) {
			accountList.addItem(strItem);
		}
		table = new JTable(model);
		detailpanel.add(table, BorderLayout.CENTER);

		accountList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == accountList) {
					if (accountList.getSelectedItem().toString() != "--Select Account--") {
						String selectedAccount = accountList.getSelectedItem().toString();
						AccountDao accctObj = new AccountDao();
						Account acctDtl = accctObj.fetchUserAccountDetail(usrData, selectedAccount);

						model.setValueAt(acctDtl.getAccountNo(), 0, 1);
						model.setValueAt(usrData.getLastName(), 1, 1);
						model.setValueAt(acctDtl.getIban(), 2, 1);
						model.setValueAt(acctDtl.getBic(), 3, 1);
						model.setValueAt(acctDtl.getBalance(), 4, 1);
						model.setValueAt(acctDtl.getAccountType(), 5, 1);
						model.setValueAt(acctDtl.getCity(), 6, 1);
						model.setValueAt(acctDtl.getCountry(), 7, 1);

					} else {
						model.setValueAt("Select an Account Number from the List", 0, 1);
						model.setValueAt("-", 1, 1);
						model.setValueAt("-", 2, 1);
						model.setValueAt("-", 3, 1);
						model.setValueAt("-", 4, 1);
						model.setValueAt("-", 5, 1);
						model.setValueAt("-", 6, 1);
						model.setValueAt("-", 7, 1);

					}
				}

			}
		});

		accountSelection.add(accountList);

		JPanel panel_2 = new JPanel();
		detailpanel.add(panel_2, BorderLayout.SOUTH);

		JButton btnHome = new JButton("Cancel");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		panel_2.add(btnHome);

		JPanel createAccount = new JPanel();
		tabbedPane.addTab("Create a new Account", null, createAccount, null);
		createAccount.setLayout(null);

		JLabel lblCreateAccount = new JLabel("Create Account");
		lblCreateAccount.setBounds(111, 0, 221, 37);
		lblCreateAccount.setVerticalAlignment(SwingConstants.TOP);
		lblCreateAccount.setFont(new Font("Calibri Light", Font.BOLD, 30));
		createAccount.add(lblCreateAccount);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 37, 725, 224);
		createAccount.add(panel_1);
		panel_1.setLayout(new GridLayout(5, 2));

		JLabel lblAccountNumber = new JLabel("Account Number");
		panel_1.add(lblAccountNumber);
		AccountDao generateaccNo = new AccountDao();
		long accNo = generateaccNo.generateAccountNumber();
		JLabel lblAccountnumbergenerated = new JLabel(Long.toString(accNo));
		panel_1.add(lblAccountnumbergenerated);

		JLabel lblCountry = new JLabel("Country");
		panel_1.add(lblCountry);

		JComboBox<String> countryList = new JComboBox<String>();
		countryList.setModel(new DefaultComboBoxModel<String>(new String[] { "--Select Country--", "France" }));
		panel_1.add(countryList);

		JLabel City = new JLabel("City");
		panel_1.add(City);

		JComboBox<String> city = new JComboBox<String>();
		city.setModel(new DefaultComboBoxModel<String>(
				new String[] { "--Select City--", "Lyon", "Paris", "Rouen", "Toulouse" }));
		panel_1.add(city);

		JLabel lblAccountType = new JLabel("Account Type");
		panel_1.add(lblAccountType);

		JComboBox<String> accountTypeList = new JComboBox<String>();
		accountTypeList.setModel(
				new DefaultComboBoxModel<String>(new String[] { "--Select Type--", "Savings", "Current", "Salary" }));
		panel_1.add(accountTypeList);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnCreate) {
					Account accountdtl = new Account(accNo,
							generateIban(accNo, countryList.getSelectedItem().toString()),
							generateBic(accNo, city.getSelectedItem().toString()), city.getSelectedItem().toString(),
							countryList.getSelectedItem().toString(), 0, accountTypeList.getSelectedItem().toString(),
							usrData.getUsrId());

					AccountDao dataAccount = new AccountDao(accountdtl);
					if (dataAccount.createAccount(accountdtl)) {

						JOptionPane.showMessageDialog(null, "Congrats!! Your account has been created.");
						HomeScreenManager home = new HomeScreenManager(usrData);
						setVisible(false);
						home.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Oops Something went wrong the data didn't get updated.");
					}

				}
			}
		});
		panel_1.add(btnCreate);

		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		panel_1.add(btnCancel_1);

		JPanel transfer = new JPanel();
		tabbedPane.addTab("Make a Transfer", null, transfer, null);
		transfer.setLayout(new BorderLayout(0, 0));

		JPanel transferHead = new JPanel();
		transfer.add(transferHead, BorderLayout.NORTH);

		JLabel lblTransferMoney = new JLabel("Transfer Money");
		lblTransferMoney.setFont(new Font("Calibri Light", Font.BOLD, 30));
		transferHead.add(lblTransferMoney);

		JPanel tranAmountTxt = new JPanel();
		transfer.add(tranAmountTxt, BorderLayout.CENTER);
		tranAmountTxt.setLayout(new GridLayout(4, 2));

		JLabel lblSelectSourceAccount = new JLabel("Select Source Account");
		lblSelectSourceAccount.setFont(new Font("Calibri Light", Font.BOLD, 18));
		tranAmountTxt.add(lblSelectSourceAccount);

		JComboBox<String> sourceAccountList = new JComboBox<String>();
		AccountDao acctListDBsrc = new AccountDao();
		ArrayList<String> listsrcacct = acctListDBsrc.fetchUserAccountList(usrData);
		sourceAccountList.addItem("--Select Account--");
		for (String strItem : listsrcacct) {
			sourceAccountList.addItem(strItem);
		}
		tranAmountTxt.add(sourceAccountList);

		JLabel lblNewLabel = new JLabel("Select Target Account");
		tranAmountTxt.add(lblNewLabel);

		JComboBox<String> targetAccountList = new JComboBox<String>();
		AccountDao acctListDBsdes = new AccountDao();
		ArrayList<String> listsrcacctdes = acctListDBsdes.fetchAllAccountsList();
		targetAccountList.addItem("--Select Account--");
		for (String strItem : listsrcacctdes) {
			targetAccountList.addItem(strItem);
		}
		tranAmountTxt.add(targetAccountList);

		JLabel lblAmount_1 = new JLabel("Amount");
		tranAmountTxt.add(lblAmount_1);

		transAmount = new JTextField();
		tranAmountTxt.add(transAmount);
		transAmount.setColumns(10);

		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnTransfer) {

					if (sourceAccountList.getSelectedItem().toString()
							.equals(targetAccountList.getSelectedItem().toString())) {

						JOptionPane.showMessageDialog(null, "The Source and Target Accounts cannot be the same");
					} else {
						if (!transAmount.getText().toString().isEmpty()) {
							AccountDao acctDao = new AccountDao();
							Account sourceAcct = acctDao.fetchUserAccountDetail(usrData,
									sourceAccountList.getSelectedItem().toString());
							Account targetAcct = acctDao
									.fetchAllUserAccountDetail(targetAccountList.getSelectedItem().toString());
							int amountToTransfer = Integer.parseInt(transAmount.getText());
							int sourceBalance = sourceAcct.getBalance();
							int targetBalance = targetAcct.getBalance();

							if (sourceBalance <= 0 || amountToTransfer > sourceBalance) {
								JOptionPane.showMessageDialog(null,
										"You do not Have Sufficient fund in your Account \n"
												+ " The Account Balance for the Account " + sourceAcct.getAccountNo()
												+ "\n" + " is " + sourceAcct.getBalance());
							} else {

								// calculating the source Balance After Transfer
								sourceBalance -= amountToTransfer;
								sourceAcct.setBalance(sourceBalance);

								// Calculating the target Balance After Transfer
								targetBalance += amountToTransfer;
								targetAcct.setBalance(targetBalance);
								SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
								String time = localDateFormat.format(new Date());

								String sourceTransDesc = "Amount Transfered to account:" + targetAcct.getAccountNo();
								Transaction srcTrans = new Transaction(sourceAcct.getAccountNo(), sourceAcct.getUsrId(),
										new Date(), time, sourceTransDesc, "Debit", amountToTransfer, sourceBalance);

								String tarTranceDesc = "Amount Transfered From account:" + sourceAcct.getAccountNo();
								Transaction tarTrans = new Transaction(targetAcct.getAccountNo(), targetAcct.getUsrId(),
										new Date(), time, tarTranceDesc, "Credit", amountToTransfer, targetBalance);
								TransactionDao transdb = new TransactionDao();
								acctDao.updateBalance(sourceAcct);
								acctDao.updateBalance(targetAcct);
								transdb.insertTransaction(srcTrans);
								transdb.insertTransaction(tarTrans);

								JOptionPane.showMessageDialog(null,
										"Transfer is Successul!! \n" + "                       Accnount Balances\n"
												+ " Source Account Number:" + sourceAcct.getAccountNo() + " Balance:"
												+ sourceAcct.getBalance() + "\n" + " Target Account Number:"
												+ targetAcct.getAccountNo() + " Balance:" + targetAcct.getBalance());

							}
						} else {
							JOptionPane.showMessageDialog(null, "Please Enter an Amount to Transfer!!");
						}

					}
				}
			}
		});
		tranAmountTxt.add(btnTransfer);

		JButton btnCancel_3 = new JButton("Cancel");
		btnCancel_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		tranAmountTxt.add(btnCancel_3);

		JPanel userInfoTab = new JPanel();
		tabbedPane.addTab("User Info", null, userInfoTab, null);
		userInfoTab.setLayout(new BorderLayout(0, 0));

		JPanel userInforHead = new JPanel();
		userInfoTab.add(userInforHead, BorderLayout.NORTH);

		JLabel lblUserInfo = new JLabel("User Info");
		lblUserInfo.setFont(new Font("Calibri Light", Font.BOLD, 30));
		userInforHead.add(lblUserInfo);

		JPanel usrInfoBody = new JPanel();
		userInfoTab.add(usrInfoBody, BorderLayout.CENTER);
		usrInfoBody.setLayout(new BorderLayout(0, 0));

		JPanel selectUserPanel = new JPanel();
		usrInfoBody.add(selectUserPanel, BorderLayout.NORTH);
		selectUserPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblSelectUser = new JLabel("Select User:");
		lblSelectUser.setFont(new Font("Calibri Light", Font.BOLD, 20));
		selectUserPanel.add(lblSelectUser);

		ArrayList<String> usrNameList = new ArrayList<>();
		JComboBox<String> userList = new JComboBox<String>();
		selectUserPanel.add(userList);

		table_1 = new JTable(usermodel);
		usrInfoBody.add(table_1, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Client Transaction", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel clTransactionPanel = new JPanel();
		panel_3.add(clTransactionPanel, BorderLayout.NORTH);

		JLabel lblClientTransaction = new JLabel("Client Transaction");
		lblClientTransaction.setFont(new Font("Calibri Light", Font.BOLD, 30));
		clTransactionPanel.add(lblClientTransaction);

		JPanel clTransactionPanelFooter = new JPanel();
		panel_3.add(clTransactionPanelFooter, BorderLayout.SOUTH);
		clTransactionPanelFooter.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel clTransactionPanelBody = new JPanel();
		panel_3.add(clTransactionPanelBody, BorderLayout.CENTER);
		clTransactionPanelBody.setLayout(new BorderLayout(0, 0));

		JPanel internalPanelclientTran = new JPanel();
		clTransactionPanelBody.add(internalPanelclientTran, BorderLayout.NORTH);
		internalPanelclientTran.setLayout(new GridLayout(0, 4, 0, 0));
		UserDao userData = new UserDao();
		usrNameList = userData.fetchUserData();
		userList.addItem("--Select User--");

		JLabel lblSelectCustomer = new JLabel("Select Customer");
		lblSelectCustomer.setFont(new Font("Calibri Light", Font.BOLD, 20));
		internalPanelclientTran.add(lblSelectCustomer);

		JComboBox<String> selectCustomer = new JComboBox<String>();
		internalPanelclientTran.add(selectCustomer);
		selectCustomer.addItem("--Select User--");
		for (String user : usrNameList) {
			selectCustomer.addItem(user);
		}
		JLabel lblSelectAccount_2 = new JLabel("Select Account");
		lblSelectAccount_2.setFont(new Font("Calibri Light", Font.BOLD, 20));
		internalPanelclientTran.add(lblSelectAccount_2);

		JComboBox<String> selectAccountList = new JComboBox<String>();
		internalPanelclientTran.add(selectAccountList);

		JLabel lblStartDate_1 = new JLabel("Start Date");
		lblStartDate_1.setFont(new Font("Calibri Light", Font.BOLD, 20));
		internalPanelclientTran.add(lblStartDate_1);

		JDateChooser startDateChoice = new JDateChooser();
		internalPanelclientTran.add(startDateChoice);

		JLabel lblEndDate_1 = new JLabel("End Date");
		lblEndDate_1.setFont(new Font("Calibri Light", Font.BOLD, 20));
		internalPanelclientTran.add(lblEndDate_1);

		JDateChooser endDateChoice = new JDateChooser();
		internalPanelclientTran.add(endDateChoice);
		JButton btnGenerate = new JButton("Generate ");
		internalPanelclientTran.add(btnGenerate);

		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer = selectCustomer.getSelectedItem().toString();
				if (e.getSource() == btnGenerate) {
					if (customer.equals("--Select User--")
							|| selectAccountList.getSelectedItem().toString().equals("--Select Account--")) {
						JOptionPane.showMessageDialog(null, "Please Select the Customer and Account!!");
					} else {
						if ((!selectAccountList.getSelectedItem().toString().isEmpty())
								&& (!selectAccountList.getSelectedItem().toString().equals("--Select Account--"))) {
							if (startDateChoice.getDate() != null && endDateChoice.getDate() != null) {
								TransactionDao trans = new TransactionDao();
								result = trans.fetchUserTransactionByDate(
										selectAccountList.getSelectedItem().toString(), customer,
										startDateChoice.getDate(), endDateChoice.getDate());

								clienttransTable.setModel(DbUtils.resultSetToTableModel(result));
							} else {
								JOptionPane.showMessageDialog(null, "Start and End Date should be Selected");
							}

						} else {
							clienttransTable.setModel(exportmodel);
						}

					}
				}
			}
		});

		JButton btnExportClient = new JButton("Export");
		btnExportClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer = selectCustomer.getSelectedItem().toString();
				if (e.getSource() == btnExportClient) {
					if (customer.equals("--Select User--")
							|| selectAccountList.getSelectedItem().toString().equals("--Select Account--")) {
						JOptionPane.showMessageDialog(null, "Please Select the Customer and Account!!");
					} else {
						if (selectAccountList.getSelectedItem().toString().equals("--Select Account--")) {

							JOptionPane.showMessageDialog(null, "Please Select the Account to Export");
						} else {

							try {
								if (startDateChoice.getDate() != null && endDateChoice.getDate() != null) {
									CSVFileWriter writeCsv = new CSVFileWriter();
									TransactionDao trans = new TransactionDao();
									result = trans.fetchUserTransactionByDate(
											selectAccountList.getSelectedItem().toString(), customer,
											startDateChoice.getDate(), endDateChoice.getDate());
									while (result.next()) {
										writeCsv.convertToCsv(result);
									}
									JOptionPane.showMessageDialog(null, "File Successfully written!!!");
								} else {
									JOptionPane.showMessageDialog(null, "Start and End Date should be Selected");
								}
							} catch (FileNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

			}
		});
		internalPanelclientTran.add(btnExportClient);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAccountList.setSelectedIndex(0);
				selectCustomer.setSelectedIndex(0);
				startDateChoice.setDate(null);
				endDateChoice.setDate(null);

				clienttransTable.setModel(new DefaultTableModel(dataExport, transHeader));

			}
		});
		internalPanelclientTran.add(btnReset);

		JButton exportReport = new JButton("Export Report");
		internalPanelclientTran.add(exportReport);

		exportReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customer = selectCustomer.getSelectedItem().toString();
				if (e.getSource() == exportReport) {
					if (customer.equals("--Select User--")
							|| selectAccountList.getSelectedItem().toString().equals("--Select Account--")) {
						JOptionPane.showMessageDialog(null, "Please Select the Customer and Account!!");
					} else {
						if (selectAccountList.getSelectedItem().toString().equals("--Select Account--")) {

							JOptionPane.showMessageDialog(null, "Please Select the Account to Export");
						} else {

							try {
								if (startDateChoice.getDate() != null && endDateChoice.getDate() != null) {
									transactions = new ArrayList<>();
									TransactionDao trans = new TransactionDao();
									result = trans.fetchUserTransactionByDate(
											selectAccountList.getSelectedItem().toString(), customer,
											startDateChoice.getDate(), endDateChoice.getDate());
									while (result.next()) {
										transactions.add(
												new Transaction(result.getLong(1), result.getLong(2), result.getLong(3),
														result.getDate(4), result.getString(5), result.getString(6),
														result.getString(7), result.getInt(8), result.getInt(9)));
									}
									new CreateReport(transactions, usrData);
									JOptionPane.showMessageDialog(null, "Report Generated Successfully!!!");
								} else {
									JOptionPane.showMessageDialog(null, "Start and End Date should be Selected");
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

			}
		});

		clienttransTable = new JTable(exportmodel);
		jscrClient = new JScrollPane(clienttransTable);
		clTransactionPanelBody.add(jscrClient, BorderLayout.CENTER);

		JPanel customerDepositPanel = new JPanel();
		tabbedPane.addTab("Customer Deposits", null, customerDepositPanel, null);
		customerDepositPanel.setLayout(new BorderLayout(0, 0));

		JPanel cdPanelHead = new JPanel();
		customerDepositPanel.add(cdPanelHead, BorderLayout.NORTH);

		JLabel lblCustomerDeposits = new JLabel("Customer Deposits");
		lblCustomerDeposits.setFont(new Font("Calibri Light", Font.BOLD, 30));
		cdPanelHead.add(lblCustomerDeposits);

		JPanel cdPanelBody = new JPanel();
		customerDepositPanel.add(cdPanelBody, BorderLayout.CENTER);
		cdPanelBody.setLayout(new BorderLayout(0, 0));

		JPanel cdOptionsPanel = new JPanel();
		cdPanelBody.add(cdOptionsPanel, BorderLayout.NORTH);
		cdOptionsPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setFont(new Font("Calibri Light", Font.BOLD, 20));
		cdOptionsPanel.add(lblStartDate);

		JDateChooser startDateChooser = new JDateChooser();
		cdOptionsPanel.add(startDateChooser);

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setFont(new Font("Calibri Light", Font.BOLD, 20));
		cdOptionsPanel.add(lblEndDate);

		JDateChooser endDateChooser = new JDateChooser();
		cdOptionsPanel.add(endDateChooser);

		depositTable = new JTable(depositModel);
		JScrollPane cdscrPane = new JScrollPane(depositTable);
		cdPanelBody.add(cdscrPane, BorderLayout.CENTER);

		JButton btncdGenerate = new JButton("Generate");
		cdOptionsPanel.add(btncdGenerate);
		btncdGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btncdGenerate) {
					TransactionDao trans = new TransactionDao();
					int deposit = 0;
					ResultSet result = trans.fetchUserDepositByDate(startDateChooser.getDate(),
							endDateChooser.getDate());
					try {
						while (result.next()) {
							System.out.println(result.getInt(8));
							deposit += result.getInt(8);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Deposit:" + deposit);
					System.out
							.println(startDateChooser.getDate().toString() + "-" + endDateChooser.getDate().toString());
					depositModel.setValueAt(startDateChooser.getDate(), 0, 0);
					depositModel.setValueAt(endDateChooser.getDate(), 0, 1);
					depositModel.setValueAt(deposit, 0, 2);

				}
			}
		});

		JButton btnCdCancel = new JButton("Cancel");
		btnCdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		cdOptionsPanel.add(btnCdCancel);

		selectCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (e.getSource() == selectCustomer) {

					ArrayList<String> listClientAccountLoad = new ArrayList<>();
					listClientAccountLoad.clear();
					selectAccountList.removeAllItems();
					selectAccountList.addItem("--Select Account--");
					selectAccountList.addItem("All the accounts");
					AccountDao acctexpotyListDBld = new AccountDao();
					listClientAccountLoad = acctexpotyListDBld
							.fetchUserAccountList(selectCustomer.getSelectedItem().toString());
					for (String account : listClientAccountLoad) {
						selectAccountList.addItem(account);
					}
					selectAccountList.setSelectedItem("--Select Account--");
				}

			}
		});

		for (String user : usrNameList) {
			userList.addItem(user);
		}

		userList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				if (e.getSource() == userList) {
					if (!accountList.getSelectedItem().toString().equals("--Select User--")) {
						String selectedUser = userList.getSelectedItem().toString();
						UserDao userData = new UserDao();
						User user = userData.loadUserDataByUserName(selectedUser);

						usermodel.setValueAt(user.getUsrId(), 0, 1);
						usermodel.setValueAt(user.getFirstName(), 1, 1);
						usermodel.setValueAt(user.getLastName(), 2, 1);
						usermodel.setValueAt(user.getUsrName(), 3, 1);
						usermodel.setValueAt(user.getPhNumber1(), 4, 1);
						usermodel.setValueAt(user.getPhNumber2(), 5, 1);
						usermodel.setValueAt(user.getAddress(), 6, 1);
						usermodel.setValueAt(user.getEmailId(), 7, 1);

					} else {
						usermodel.setValueAt("Select an User from the List", 0, 1);
						usermodel.setValueAt("-", 1, 1);
						usermodel.setValueAt("-", 2, 1);
						usermodel.setValueAt("-", 3, 1);
						usermodel.setValueAt("-", 4, 1);
						usermodel.setValueAt("-", 5, 1);
						usermodel.setValueAt("-", 6, 1);
						usermodel.setValueAt("-", 7, 1);

					}
				}

			}
		});

		JPanel userInfoFooter = new JPanel();
		userInfoFooter.setBounds(20, 388, 750, 30);
		frame.add(userInfoFooter);
		userInfoFooter.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnHome_1 = new JButton("Home");
		btnHome_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HomeScreenManager home = new HomeScreenManager(usrData);
				setVisible(false);
				home.setVisible(true);
			}
		});
		userInfoFooter.add(btnHome_1);

	}

	public String generateIban(long acctNumber, String country) {
		String iban = "";
		iban = country.substring(0, 2).toUpperCase() + acctNumber;
		return iban;
	}

	public String generateBic(long acctNumber, String city) {
		String bic = "";
		bic = city.substring(0, 3).toUpperCase() + acctNumber;
		return bic;
	}
}
