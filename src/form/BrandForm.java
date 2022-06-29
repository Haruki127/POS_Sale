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

import model.Brand;
import service.BrandService;

public class BrandForm {

	public JFrame frmBrandEntry;
	private JTextField txtBrand;
	private JTextField txtSearch;
	private BrandService brandService;
	private Brand brand;
	private JTable tblBrand;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Brand> brandList = new ArrayList<>();
	private List<Brand> filteredbrandList = new ArrayList<>();

	private final config.DBconfig dbConfig = new config.DBconfig();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrandForm window = new BrandForm();
					window.frmBrandEntry.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BrandForm() {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllBrands(Optional.empty());
	}

	private void initializeDependency() {
		this.brandService = new BrandService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Brand Name");
		this.tblBrand.setModel(dtm);
		this.tblBrand.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	private void loadAllBrands(Optional<List<Brand>> optionalBrands) {
		this.dtm = (DefaultTableModel) this.tblBrand.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();
		
		this.brandList = this.brandService.findAllBrands();
		
		this.filteredbrandList = optionalBrands.orElseGet(
				() -> this.brandList).stream().collect(Collectors.toList());

		filteredbrandList.forEach(e -> {
			Object[] row = new Object[7];
			row[0] = e.getBrand_id();
			row[1] = e.getBrand();
			dtm.addRow(row);
		});
		
		this.tblBrand.setModel(dtm);
	}

	private void resetFormData() {
		txtBrand.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBrandEntry = new JFrame();
		frmBrandEntry.setTitle("Brand Entry");
		frmBrandEntry.setBounds(50, 100, 600, 500);
		frmBrandEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBrandEntry.getContentPane().setLayout(null);

		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setHorizontalAlignment(SwingConstants.LEFT);
		lblBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBrand.setBounds(47, 39, 85, 29);
		frmBrandEntry.getContentPane().add(lblBrand);

		txtBrand = new JTextField();
		txtBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtBrand.setColumns(10);
		txtBrand.setBounds(47, 78, 193, 29);
		frmBrandEntry.getContentPane().add(txtBrand);

		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic('S');
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Brand brand = new Brand();
				try {
					brand.setBrand(txtBrand.getText());

				if (!brand.getBrand().isBlank()) {

					brandService.createBrand(brand);
					resetFormData();
					loadAllBrands(Optional.empty());
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
		frmBrandEntry.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(280, 78, 165, 29);
		frmBrandEntry.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = txtSearch.getText();
				System.out.println(brandList);
				loadAllBrands(
						Optional.of(brandList.stream()
								.filter(emp->emp.getBrand().toLowerCase().startsWith(keyword.toLowerCase()))
								.collect(Collectors.toList()))
						
						);
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(450, 78, 165, 29);
		btnSearch.setSize(100,30);
		frmBrandEntry.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(276, 132, 300, 300);
		frmBrandEntry.getContentPane().add(scrollPane_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);

		tblBrand = new JTable();
		tblBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblBrand);
		this.tblBrand.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblBrand.getSelectionModel().isSelectionEmpty()) {

				String id = tblBrand.getValueAt(tblBrand.getSelectedRow(), 0).toString();

				brand = brandService.findBrandById(id);

				txtBrand.setText(brand.getBrand());

			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					brand.setBrand(txtBrand.getText());
					if (!brand.getBrand().isBlank()) {

						brandService.updateBrand(String.valueOf(brand.getBrand_id()), brand);
						resetFormData();
						loadAllBrands(Optional.empty());
						brand = null;

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
		frmBrandEntry.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setMnemonic('D');
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(47, 406, 193, 29);
		frmBrandEntry.getContentPane().add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					brand.setBrand(txtBrand.getText());
					if (!brand.getBrand().isBlank()) {
						
						brandService.deleteBrand(String.valueOf(brand.getBrand_id()), brand);
						resetFormData();
						loadAllBrands(Optional.empty());
						brand = null;

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
