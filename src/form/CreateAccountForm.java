package form;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import model.CreateAccount;
import model.Employee;
import service.CreateAccountService;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class CreateAccountForm {

	public JFrame frmCreateAccount;
	private JTextField txtName;
	Employee emp = new Employee();
	private JTextField txtRole;
	Employee passemp = new Employee();
	private JPasswordField pwfPassword;
	private JPasswordField pwfRePassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccountForm window = new CreateAccountForm();
					window.frmCreateAccount.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateAccountForm() {
		initialize();
	}
	
	public Employee passInfo(Employee emp) {
		return this.passemp = emp;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmCreateAccount = new JFrame();
		frmCreateAccount.setBounds(100, 100, 272, 322);
		frmCreateAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmCreateAccount.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Create Account");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(74, 24, 107, 17);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(8, 71, 63, 14);
		panel.add(lblNewLabel_1);
		
		txtName = new JTextField();
		txtName.setBounds(73, 63, 172, 32);
		panel.add(txtName);
		txtName.setColumns(10);
		txtName.setText(emp.getName());
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(8, 108, 58, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("<html>ReEnter<br/>Password</html>");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setBounds(8, 140, 55, 30);
		panel.add(lblNewLabel_1_1_1);
		
		JButton btnSignup = new JButton("Sign Up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(String.valueOf(pwfPassword.getPassword())+":"+String.valueOf(pwfRePassword.getPassword()));
				if(!String.valueOf(pwfPassword.getPassword()).equals(String.valueOf(pwfRePassword.getPassword()))) {
					JOptionPane.showMessageDialog(null, "ReEnter the password...");
					pwfPassword.setFocusable(true);
				}
				CreateAccount ca = new CreateAccount();
				ca.setName(txtName.getText().toString());
				ca.setPassword(String.valueOf(pwfPassword.getPassword()));
				ca.setRole(txtRole.getText().toString());
				CreateAccountService cas = new CreateAccountService();
				cas.createCreateAccount(passemp,ca);
				JOptionPane.showMessageDialog(null, "SingUp succeeded!");
				CreateAccountForm caf = new CreateAccountForm();
				caf.frmCreateAccount.setVisible(false);
				EmployeeForm empf = new EmployeeForm();
				empf.frmEmployeeEntry.setVisible(true);
			}
		});
		btnSignup.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSignup.setBounds(157, 220, 89, 37);
		panel.add(btnSignup);
		
		txtRole = new JTextField();
		txtRole.setColumns(10);
		txtRole.setBounds(73, 175, 172, 32);
		panel.add(txtRole);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRole.setBounds(8, 182, 58, 14);
		panel.add(lblRole);
		
		pwfPassword = new JPasswordField();
		pwfPassword.setBounds(73, 101, 171, 32);
		panel.add(pwfPassword);
		
		pwfRePassword = new JPasswordField();
		pwfRePassword.setBounds(73, 138, 171, 32);
		panel.add(pwfRePassword);
	}
}
