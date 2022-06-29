package form;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import form.*;

public class MainForm {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Desktop\\CIO_Exported\\clover.png"));
		frame.setBounds(100, 100, 450, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnEmployee = new JButton("Employee");
		btnEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeForm newEmployeeWindow = new EmployeeForm();
				newEmployeeWindow.frmEmployeeEntry.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnEmployee = new GridBagConstraints();
		gbc_btnEmployee.insets = new Insets(0, 0, 5, 0);
		gbc_btnEmployee.gridx = 0;
		gbc_btnEmployee.gridy = 0;
		frame.getContentPane().add(btnEmployee, gbc_btnEmployee);
		
		JButton btnSupplier = new JButton("Supplier");
		btnSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupplierForm newSupplierWindow = new SupplierForm();
				newSupplierWindow.frmSupplierEntry.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnSupplier = new GridBagConstraints();
		gbc_btnSupplier.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupplier.gridx = 0;
		gbc_btnSupplier.gridy = 1;
		frame.getContentPane().add(btnSupplier, gbc_btnSupplier);
		
		JButton btnBrand = new JButton("Brand");
		btnBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrandForm newBrandWindow = new BrandForm();
				newBrandWindow.frmBrandEntry.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnBrand = new GridBagConstraints();
		gbc_btnBrand.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrand.gridx = 0;
		gbc_btnBrand.gridy = 2;
		frame.getContentPane().add(btnBrand, gbc_btnBrand);
		
		JButton btnCategory = new JButton("Category");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryForm newCategoryWindow = new CategoryForm();
				newCategoryWindow.frmCategoryEntry.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnCategory = new GridBagConstraints();
		gbc_btnCategory.insets = new Insets(0, 0, 5, 0);
		gbc_btnCategory.gridx = 0;
		gbc_btnCategory.gridy = 3;
		frame.getContentPane().add(btnCategory, gbc_btnCategory);
		
		JButton btnNewButton_4 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_4.gridx = 0;
		gbc_btnNewButton_4.gridy = 4;
		frame.getContentPane().add(btnNewButton_4, gbc_btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_5.gridx = 0;
		gbc_btnNewButton_5.gridy = 5;
		frame.getContentPane().add(btnNewButton_5, gbc_btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_6 = new GridBagConstraints();
		gbc_btnNewButton_6.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_6.gridx = 0;
		gbc_btnNewButton_6.gridy = 6;
		frame.getContentPane().add(btnNewButton_6, gbc_btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
		gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_7.gridx = 0;
		gbc_btnNewButton_7.gridy = 7;
		frame.getContentPane().add(btnNewButton_7, gbc_btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_8 = new GridBagConstraints();
		gbc_btnNewButton_8.gridx = 0;
		gbc_btnNewButton_8.gridy = 8;
		frame.getContentPane().add(btnNewButton_8, gbc_btnNewButton_8);
	}
}
