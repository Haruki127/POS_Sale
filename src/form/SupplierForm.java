package form;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;

import model.Supplier;
import service.SupplierService;

public class SupplierForm {

	public JFrame frmSupplierEntry;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTextField txtSearch;
	private SupplierService supplierService;
	private Supplier supplier;
	private JTable tblSupplier;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Supplier> supplierList = new ArrayList<>();
	private List<Supplier> filteredsupplierList = new ArrayList<>();

	private final config.DBconfig dbConfig = new config.DBconfig();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierForm window = new SupplierForm();
					window.frmSupplierEntry.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SupplierForm() {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllSuppliers(Optional.empty());
	}
	
	private void initializeDependency() {
		this.supplierService = new SupplierService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Phone");
		dtm.addColumn("Email");
		dtm.addColumn("Address");
		this.tblSupplier.setModel(dtm);
		for(int i=1; i<=4; i++) {
			this.tblSupplier.getColumnModel().getColumn(i).setMinWidth(150);
		}
		this.tblSupplier.getColumnModel().getColumn(0).setMaxWidth(50);
		this.tblSupplier.getColumnModel().getColumn(0).setMinWidth(50);
	}
	
	private void loadAllSuppliers(Optional<List<Supplier>> optionalSuppliers) {
		this.dtm = (DefaultTableModel) this.tblSupplier.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();
		
		this.supplierList = this.supplierService.findAllSuppliers();
		
		this.filteredsupplierList = optionalSuppliers.orElseGet(
				() -> this.supplierList).stream().collect(Collectors.toList());

		filteredsupplierList.forEach(e -> {
			Object[] row = new Object[5];
			row[0] = e.getSup_id();
			row[1] = e.getName();
			row[2] = e.getPhone();
			row[3] = e.getEmail();
			row[4] = e.getAddress();
			dtm.addRow(row);
		});
		
		this.tblSupplier.setModel(dtm);
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
		frmSupplierEntry = new JFrame();
		frmSupplierEntry.setTitle("Supplier Entry");
		frmSupplierEntry.setBounds(50, 100, 800, 500);
		frmSupplierEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSupplierEntry.getContentPane().setLayout(null);
		frmSupplierEntry.setSize(800, 500);

		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(47, 34, 85, 29);
		frmSupplierEntry.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(47, 63, 193, 29);
		frmSupplierEntry.getContentPane().add(txtName);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone.setBounds(47, 100, 85, 29);
		frmSupplierEntry.getContentPane().add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPhone.setColumns(10);
		txtPhone.setBounds(47, 126, 193, 29);
		frmSupplierEntry.getContentPane().add(txtPhone);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(47, 166, 85, 29);
		frmSupplierEntry.getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(47, 192, 193, 29);
		frmSupplierEntry.getContentPane().add(txtEmail);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(47, 232, 85, 29);
		frmSupplierEntry.getContentPane().add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAddress.setColumns(10);
		txtAddress.setBounds(47, 258, 193, 29);
		frmSupplierEntry.getContentPane().add(txtAddress);

		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic('S');
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Supplier supplier = new Supplier();
				try {
					if(txtName.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please enter Supplier Name.");
						txtName.requestFocus(true) ;
					}
					else if(!(txtName.getText().matches("[a-z A-Z]+")))
					{
						JOptionPane.showMessageDialog(null, "Please enter String value for Name.");
						txtName.requestFocus(true);
					}
					
					else if(txtPhone.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please enter Supplier Phone.");
						txtPhone.requestFocus(true);
					}
					else if(!(txtPhone.getText().matches("[0-9]+")))
					{
						JOptionPane.showMessageDialog(null, "Please enter Integer value for Phone Number.");
						txtPhone.requestFocus(true);
					}
					
					else if(txtEmail.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please enter Supplier Email.");
						txtEmail.requestFocus(true);
					}
					else if(!(txtEmail.getText().matches("^[A-Za-z0-9_]+@[A-Za-z0-9.]+$")))//[^(.+)@(.+)$]
					{
						JOptionPane.showMessageDialog(null, "Please enter valid Email.");
						txtEmail.requestFocus(true);
					}
					
					else if(txtAddress.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please enter Supplier Address.");
						txtAddress.requestFocus(true);
					}
					else if(!(txtAddress.getText().matches("[^!@#$%^&*=+~?<>;:/]+")))
					{
						JOptionPane.showMessageDialog(null, "Please enter Address again.");
						txtAddress.requestFocus(true);
					}
					else {
						supplier.setName(txtName.getText());
						supplier.setPhone(txtPhone.getText());
						supplier.setEmail(txtEmail.getText());
						supplier.setAddress(txtAddress.getText());
						supplierService.createSupplier(supplier);
						resetFormData();
						loadAllSuppliers(Optional.empty());
					}
				}
				catch(NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "Enter Required Field!");
				}
				catch(NullPointerException ne) {
					JOptionPane.showMessageDialog(null, "Enter Required Field!!");
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(47, 309, 193, 29);
		frmSupplierEntry.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(478, 78, 165, 29);
		frmSupplierEntry.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = txtSearch.getText();
				loadAllSuppliers(
						Optional.of(supplierList.stream()
								.filter(emp->emp.getName().toLowerCase().startsWith(keyword.toLowerCase()))
								.collect(Collectors.toList()))
						
						);
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(653, 78, 85, 29);
		frmSupplierEntry.getContentPane().add(btnSearch);
		
//		JScrollPane scrollPane_1 = new JScrollPane();
//		scrollPane_1.setBounds(276, 132, 500, 300);
//		frmSupplierEntry.getContentPane().add(scrollPane_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(276, 132, 500, 200);
//		scrollPane.setViewportView(scrollPane);
		frmSupplierEntry.getContentPane().add(scrollPane);

		tblSupplier = new JTable();
		tblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tblSupplier.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tblSupplier);
		this.tblSupplier.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblSupplier.getSelectionModel().isSelectionEmpty()) {

				String id = tblSupplier.getValueAt(tblSupplier.getSelectedRow(), 0).toString();

				supplier = supplierService.findSupplierById(id);

				txtName.setText(supplier.getName());
				txtPhone.setText(supplier.getPhone());
				txtEmail.setText(supplier.getEmail());
				txtAddress.setText(supplier.getAddress());
			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					supplier.setName(txtName.getText());
					supplier.setPhone(txtPhone.getText());
					supplier.setEmail(txtEmail.getText());
					supplier.setAddress(txtAddress.getText());
					if (!supplier.getName().isBlank() && !supplier.getPhone().isBlank() && !supplier.getEmail().isBlank() && !supplier.getAddress().isBlank()) {

						supplierService.updateSupplier(String.valueOf(supplier.getSup_id()), supplier);
						resetFormData();
						loadAllSuppliers(Optional.empty());
						supplier = null;

					}
				}
				catch(NullPointerException ne) {
					JOptionPane.showMessageDialog(null, "Select the row to update!");
				}
				catch(NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "Select the row to update!!");
				}
			}
		});
		btnUpdate.setMnemonic('U');
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(47, 358, 193, 29);
		frmSupplierEntry.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setMnemonic('D');
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(47, 406, 193, 29);
		frmSupplierEntry.getContentPane().add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
				txtPhone.setText("");
				txtEmail.setText("");
				txtAddress.setText("");
			}
		});
		btnClear.setBounds(151, 11, 89, 23);
		frmSupplierEntry.getContentPane().add(btnClear);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					supplier.setName(txtName.getText());
					supplier.setPhone(txtPhone.getText());
					supplier.setEmail(txtEmail.getText());
					supplier.setAddress(txtAddress.getText());
					if (!supplier.getName().isBlank() && !supplier.getPhone().isBlank() && !supplier.getEmail().isBlank() && !supplier.getAddress().isBlank()) {
						
						supplierService.deleteSupplier(String.valueOf(supplier.getSup_id()), supplier);
						resetFormData();
						loadAllSuppliers(Optional.empty());
						supplier = null;

					}
				}
				catch(NullPointerException ne) {
					JOptionPane.showMessageDialog(null, "Select the row to delete!");
				}
				catch(NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "Select the row to delete!!");
				}
			}
		});

	}
}
