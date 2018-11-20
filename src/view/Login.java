package view;

import java.awt.EventQueue;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.UserDao;
import model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usrName;
	private JPasswordField passwordField;
	private static Login frame;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 53);
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 148, 118, 0 };
		gbl_panel.rowHeights = new int[] { 22, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label_2 = new JLabel("Argents Bank");
		label_2.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 0;
		panel.add(label_2, gbc_label_2);

		JLabel label_3 = new JLabel("Login");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 1;
		panel.add(label_3, gbc_label_3);
		label_3.setFont(new Font("Arial", Font.BOLD, 15));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 89, 414, 161);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 2));

		JLabel label = new JLabel("UserName/Email");
		panel_1.add(label);
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));

		usrName = new JTextField();
		panel_1.add(usrName);
		usrName.setColumns(10);

		JLabel label_1 = new JLabel("Password");
		panel_1.add(label_1);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		passwordField = new JPasswordField();
		panel_1.add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnLogin) {
					if (validateInput() == true) {
						User usrData = new User(usrName.getText(), String.valueOf(passwordField.getPassword()));

						UserDao data = new UserDao(usrData);

						boolean flag = data.loginUser(usrData);
						
						if (flag == true) {

							User loadedusr = data.loadUserData(usrData);
							if (loadedusr.getRole().equals("Customer")) {
								
								HomeScreen screen = new HomeScreen(loadedusr);

								frame.setVisible(false);
								screen.setVisible(true);
							} else if (loadedusr.getRole().equals("Front Office Supervisor")) {
								//JOptionPane.showMessageDialog(null, "Front Office Login");
								HomeScreenFrontEnd screenFEO = new HomeScreenFrontEnd(loadedusr);

								frame.setVisible(false);
								screenFEO.setVisible(true);
							} else if (loadedusr.getRole().equals("Manager")) {
								
								HomeScreenManager screenMgr = new HomeScreenManager(loadedusr);

								frame.setVisible(false);
								screenMgr.setVisible(true);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Username/Password is incorrect!!");

						}

					} else {
						JOptionPane.showMessageDialog(null, "Username/Password cannot be empty");
					}
				}

			}
		});
		panel_1.add(btnLogin);

		JButton btnNotHavingAnd = new JButton("Not Having an Account ?Sign Up");
		btnNotHavingAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnNotHavingAnd) {
					frame.setVisible(false);
					new SignUp().setVisible(true);
				}
			}
		});
		panel_1.add(btnNotHavingAnd);

	}

	public boolean validateInput() {
		boolean flag = false;

		if (usrName.getText() != null && !usrName.getText().isEmpty()) {
			if (passwordField.getPassword().length != 0) {
				flag = true;
			}
		}

		return flag;
	}
}
