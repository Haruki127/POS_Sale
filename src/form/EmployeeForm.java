package form;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import config.DBconfig;
import model.Employee;
import service.EmployeeService;

public class EmployeeForm {

	private static final ListSelectionListener ListSelectionEvent = null;
	public JFrame frmEmployeeEntry;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtSearch;
	private EmployeeService employeeService;
	private Employee employee;
	private JTable tblEmployee;
	private DefaultTableModel dtm = new DefaultTableModel();

	private List<Employee> employeeList = new ArrayList<>();
	private List<Employee> filterdemployeeList = new ArrayList<>();

	private final config.DBconfig dbConfig = new config.DBconfig();
	private JTextField txtPhone;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeForm window = new EmployeeForm();
					window.frmEmployeeEntry.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeForm() {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllEmployees(Optional.empty());
	}

	private void initializeDependency() {
		this.employeeService = new EmployeeService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Phone");
		dtm.addColumn("Email");
		dtm.addColumn("Address");
		this.tblEmployee.setModel(dtm);
	}

	private void loadAllEmployees(Optional<List<Employee>> optionalEmployees) {
		this.dtm = (DefaultTableModel) this.tblEmployee.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.employeeList = this.employeeService.findAllEmployees();

		this.filterdemployeeList = optionalEmployees.orElseGet(() -> this.employeeList).stream()
				.collect(Collectors.toList());

		filterdemployeeList.forEach(e -> {
			Object[] row = new Object[10];
			row[0] = e.getEmp_id();
			row[1] = e.getName();
			row[2] = e.getPhone();
			row[3] = e.getEmail();
			row[4] = e.getAddress();
			dtm.addRow(row);

		});
		this.tblEmployee.setModel(dtm);
	}

	private void resetFormData() {
		txtName.setText("");
		txtPhone.setText("");
		txtEmail.setText("");
		txtAddress.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmployeeEntry = new JFrame();
		frmEmployeeEntry.setTitle("Employee Entry");
		frmEmployeeEntry.setBounds(10, 100, 1000, 500);
		frmEmployeeEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmployeeEntry.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(5, 32, 85, 29);
		frmEmployeeEntry.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(73, 32, 193, 29);
		frmEmployeeEntry.getContentPane().add(txtName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(5, 170, 85, 29);
		frmEmployeeEntry.getContentPane().add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAddress.setColumns(10);
		txtAddress.setBounds(73, 170, 193, 29);
		frmEmployeeEntry.getContentPane().add(txtAddress);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtName.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Name.");
					txtName.requestFocus(true);
				} else if (!(txtName.getText().matches("[a-z A-Z]+"))) {
					JOptionPane.showMessageDialog(null, "Please enter String value for Name.");
					txtName.requestFocus(true);
				} else if (txtPhone.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Phone.");
					txtPhone.requestFocus(true);
				} else if (!(txtPhone.getText().matches("[0-9]+"))) {
					JOptionPane.showMessageDialog(null, "Please enter Integer value for Phone Number.");
					txtPhone.requestFocus(true);
				} else if (txtEmail.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Email.");
					txtEmail.requestFocus(true);
				} else if (!(txtEmail.getText().matches("^[A-Za-z0-9_]+@[A-Za-z0-9.]+$")))// [^(.+)@(.+)$]
				{
					JOptionPane.showMessageDialog(null, "Please enter again Email Number.");
					txtEmail.requestFocus(true);
				} else if (txtAddress.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Address.");
					txtAddress.requestFocus(true);
				} else if (!(txtAddress.getText().matches("[^!@#$%^&*=+~?<>;:/]+"))) {
					JOptionPane.showMessageDialog(null, "Please enter Address again.");
					txtAddress.requestFocus(true);
				} else {

					employee = new Employee();
					employee.setName(txtName.getText());
					employee.setPhone(txtPhone.getText());
					employee.setEmail(txtEmail.getText());
					employee.setAddress(txtAddress.getText());
					employeeService.createEmployee(employee);
					resetFormData();
					loadAllEmployees(Optional.empty());

				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(5, 224, 114, 29);
		frmEmployeeEntry.getContentPane().add(btnSave);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Name.");
					txtName.requestFocus(true);
				} else if (!(txtName.getText().matches("[a-z A-Z]+"))) {
					JOptionPane.showMessageDialog(null, "Please enter String value.");
					txtName.requestFocus(true);
				} else if (txtPhone.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Phone.");
					txtPhone.requestFocus(true);
				} else if (!(txtPhone.getText().matches("[0-9]+"))) {
					JOptionPane.showMessageDialog(null, "Please enter Integer value for Phone Number.");
					txtPhone.requestFocus(true);
				} else if (txtEmail.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Email.");
					txtEmail.requestFocus(true);
				} else if (!(txtEmail.getText().matches("^[A-Za-z0-9_]+@[A-Za-z0-9.]+$")))// [^(.+)@(.+)$]
				{
					JOptionPane.showMessageDialog(null, "Please enter again Email Number.");
					txtEmail.requestFocus(true);
				} else if (txtAddress.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Employee Address.");
					txtAddress.requestFocus(true);
				}
				// else if(!(txtAddress.getText().matches("[a-z A-Z 0-9()-.,]+")))
				else if (!(txtAddress.getText().matches("[^!@#$%^&*=+~?<>;:/]+"))) {
					JOptionPane.showMessageDialog(null, "Please enter Address again.");
					txtAddress.requestFocus(true);
				}

				employee.setName(txtName.getText());
				employee.setPhone(txtPhone.getText());
				employee.setEmail(txtEmail.getText());
				employee.setAddress(txtAddress.getText());

				employeeService.updateEmployee(String.valueOf(employee.getEmp_id()), employee);
				resetFormData();
				loadAllEmployees(Optional.empty());
				employee = null;

			}
		});

		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(5, 275, 114, 29);
		frmEmployeeEntry.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText().trim().equals("") || txtPhone.getText().trim().equals("")
						|| txtEmail.getText().trim().equals("") || txtAddress.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "No data! \n Please select data from table.");
					txtName.requestFocus(true);
				}
				// Employee employee = new Employee();

				employee.setName(txtName.getText());
				employee.setPhone(txtPhone.getText());
				employee.setEmail(txtEmail.getText());
				employee.setAddress(txtAddress.getText());

				employeeService.deleteEmployee(String.valueOf(employee.getEmp_id()), employee);
				resetFormData();
				loadAllEmployees(Optional.empty());

			}
		});

		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(152, 275, 114, 29);
		frmEmployeeEntry.getContentPane().add(btnDelete);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(669, 78, 165, 29);
		frmEmployeeEntry.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String keyword = txtSearch.getText();

				loadAllEmployees(Optional.of(employeeList.stream()
						.filter(emp -> emp.getName().toLowerCase().startsWith(keyword.toLowerCase()))
						.collect(Collectors.toList())));

			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(854, 78, 85, 29);
		frmEmployeeEntry.getContentPane().add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(276, 132, 662, 292);
		frmEmployeeEntry.getContentPane().add(scrollPane);

		tblEmployee = new JTable();
		tblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblEmployee);

		this.tblEmployee.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblEmployee.getSelectionModel().isSelectionEmpty()) {

				String id = tblEmployee.getValueAt(tblEmployee.getSelectedRow(), 0).toString();

				employee = employeeService.findEmployeeById(id);

				txtName.setText(employee.getName());
				txtPhone.setText(employee.getPhone());
				txtEmail.setText(employee.getEmail());
				txtAddress.setText(employee.getAddress());
			}
		});

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhone.setBounds(5, 78, 85, 29);
		frmEmployeeEntry.getContentPane().add(lblPhone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(5, 126, 64, 29);
		frmEmployeeEntry.getContentPane().add(lblEmail);

		txtPhone = new JTextField();
		txtPhone.setBounds(74, 80, 193, 29);
		frmEmployeeEntry.getContentPane().add(txtPhone);
		txtPhone.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(73, 128, 193, 29);
		frmEmployeeEntry.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee emp = new Employee();
				if(!txtName.getText().equals("") && !txtPhone.getText().equals("") && !txtEmail.getText().equals("") && !txtAddress.getText().equals("")) {
					emp.setName(txtName.getText().toString());
					emp.setPhone(txtPhone.getText().toString());
					emp.setEmail(txtEmail.getText().toString());
					emp.setAddress(txtAddress.getText().toString());
					frmEmployeeEntry.setVisible(false);
					CreateAccountForm newCAF = new CreateAccountForm();
					newCAF.passInfo(emp);
					newCAF.frmCreateAccount.setVisible(true);
				}
			}
		});
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCreateAccount.setBounds(152, 224, 114, 29);
		frmEmployeeEntry.getContentPane().add(btnCreateAccount);

		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
				txtPhone.setText("");
				txtEmail.setText("");
				txtAddress.setText("");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(5, 324, 114, 29);
		frmEmployeeEntry.getContentPane().add(btnNewButton);

	}
}
