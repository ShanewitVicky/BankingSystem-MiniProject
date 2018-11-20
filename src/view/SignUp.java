package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;
import controller.UserDao;
import model.User;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class SignUp extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4714614347241564485L;
	private JPanel contentPane;
	private JTextField firstNametxt;
	private JTextField lastNametxt;
	private JTextField usrNametxt;
	private JTextField emailIdTxt;
	private JPasswordField passwordField;
	private JPasswordField cnfPasswordField;
	private JTextField phnumber1txt;
	private JTextField phNumber2txt;
	private JDateChooser dateOfBirthtxt;
	private JTextArea addressTxtArea;
	private JComboBox<String> roleList;
    private JButton btnCancel ;
	/**
	 * Create the frame.
	 */
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(lblSignUp);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(12, 5));

		JLabel lblFirstName = new JLabel("First Name");
		panel_1.add(lblFirstName);

		firstNametxt = new JTextField();
		panel_1.add(firstNametxt);
		firstNametxt.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		panel_1.add(lblLastName);

		lastNametxt = new JTextField();
		panel_1.add(lastNametxt);
		lastNametxt.setColumns(10);

		JLabel lblUserName = new JLabel("User Name");
		panel_1.add(lblUserName);

		usrNametxt = new JTextField();
		panel_1.add(usrNametxt);
		usrNametxt.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		panel_1.add(lblEmail);

		emailIdTxt = new JTextField();
		panel_1.add(emailIdTxt);
		emailIdTxt.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		panel_1.add(lblPassword);

		passwordField = new JPasswordField();
		panel_1.add(passwordField);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		panel_1.add(lblConfirmPassword);

		cnfPasswordField = new JPasswordField();
		panel_1.add(cnfPasswordField);

		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		panel_1.add(lblDateOfBirth);

		dateOfBirthtxt = new JDateChooser();
		panel_1.add(dateOfBirthtxt);

		JLabel lblPhoneNumber = new JLabel("Phone Number 1");
		panel_1.add(lblPhoneNumber);

		phnumber1txt = new JTextField();
		panel_1.add(phnumber1txt);
		phnumber1txt.setColumns(10);

		JLabel lblPhoneNumber_1 = new JLabel("Phone Number 2");
		panel_1.add(lblPhoneNumber_1);

		phNumber2txt = new JTextField();
		panel_1.add(phNumber2txt);
		phNumber2txt.setColumns(10);

		JLabel lblAddress = new JLabel("Address");
		panel_1.add(lblAddress);

		addressTxtArea = new JTextArea();
		panel_1.add(addressTxtArea);

		JLabel lblRole = new JLabel("Role");
		panel_1.add(lblRole);

		roleList = new JComboBox<String>();
		roleList.setModel(new DefaultComboBoxModel<String>(
				new String[] { "--Select Role--", "Customer", "Front Office Supervisor", "Manager" }));
		panel_1.add(roleList);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent r) {
				if (r.getSource() == btnRegister) {
					if (validateInput() == true) {
						
							
							User usrData = new User(usrNametxt.getText(),
									String.valueOf(passwordField.getPassword()), firstNametxt.getText(), lastNametxt.getText(), emailIdTxt.getText(), phnumber1txt.getText(),
									phNumber2txt.getText(), addressTxtArea.getText(), dateOfBirthtxt.getDate(),
									roleList.getSelectedItem().toString());
         
							UserDao data = new UserDao(usrData);
							data.registerUser(usrData);
								
							JOptionPane.showMessageDialog(null, "User Registered!!!");
						
					}

				}
			}
		});
		panel_1.add(btnRegister);
        
		btnCancel = new JButton("Back");
		btnCancel.addActionListener(this);
		panel_1.add(btnCancel);
		this.add(panel);
        this.add(panel_1);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnCancel) {
			this.setVisible(false);
			new Login().setVisible(true);
		}
		
	}

	public boolean validateInput() {
		boolean result = false;
        String message="";
		if (firstNametxt.getText() != null && !firstNametxt.getText().isEmpty()) {
			if (lastNametxt.getText() != null && !lastNametxt.getText().isEmpty()) {
				if (usrNametxt.getText() != null && !usrNametxt.getText().isEmpty()) {
					if(!emailIdTxt.getText().isEmpty()) {
					if (emailCheck(emailIdTxt)) {
						if (passwordField.getPassword().length!=0) {
							if (Arrays.equals(passwordField.getPassword(), cnfPasswordField.getPassword()) ) {
								if (dateOfBirthtxt.getDate() != null) {
									if (!phnumber1txt.getText().isEmpty() || !phNumber2txt.getText().isEmpty()) {
										if (!addressTxtArea.getText().isEmpty()) {
											if (roleList.getSelectedItem().toString() != "--Select Role--") {
												result = true;
											} else {
												message="Select a Role";
											}
										} else {
											message="Address is mandatory";
										}
									} else {
										message="Please Fill atleast one Phone number!!";
									}
								} else {
									message="Select a Date Of Birth";
								}
							} else {
								
								message= "The Two passwords do not match Each Other";
							}
						} else {
							message= "The Password Field Cannot be empty!";
						}

					} else {
						message="The Email ID is Invalid. Please Enther in the format example@email.com";
					}
					}
					else {
						message="EmailID cannot be empty";
					}
				} else {
					message="The UserName cannot be Null";
				}
			} else {
				message= "The Last Name cannot be Null";
			}
		}

		else {
			message= "The First Name cannot be Null";
		}
		if(message!="") {
		JOptionPane.showMessageDialog(null, message);
		}
		return result;
	}

	public boolean emailCheck(JTextField emailID) {
		String str = "";
		String emailid;
		boolean result = true;
		emailid = emailID.getText().toString();

		if (emailid.equalsIgnoreCase(str)) {
			result = false;
		} else {
			Pattern p = Pattern.compile("[a-zA-Z0-9_.]*@[a-zA-Z]*.[a-zA-Z]*");
			Matcher m = p.matcher(emailid);
			boolean bm = m.matches();

			if (bm == true) {
				result = true;

			} else {
				result = false;
			}
		}
		return result;
	}

	

}
