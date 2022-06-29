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

import model.Category;
import service.CategoryService;

public class CategoryForm {

	public JFrame frmCategoryEntry;
	private JTextField txtCategory;
	private JTextField txtSearch;
	private CategoryService categoryService;
	private Category category;
	private JTable tblCategory;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Category> categoryList = new ArrayList<>();
	private List<Category> filteredcategoryList = new ArrayList<>();

	private final config.DBconfig dbConfig = new config.DBconfig();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryForm window = new CategoryForm();
					window.frmCategoryEntry.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CategoryForm() {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllCategories(Optional.empty());
	}

	private void initializeDependency() {
		this.categoryService = new CategoryService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Category Name");
		this.tblCategory.setModel(dtm);
	}

	private void loadAllCategories(Optional<List<Category>> optionalCategories) {
		this.dtm = (DefaultTableModel) this.tblCategory.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();
		
		this.categoryList = this.categoryService.findAllCategories();
		
		this.filteredcategoryList = optionalCategories.orElseGet(
				() -> this.categoryList).stream().collect(Collectors.toList());

		filteredcategoryList.forEach(e -> {
			Object[] row = new Object[7];
			row[0] = e.getCategory_id();
			row[1] = e.getCategory();
			dtm.addRow(row);
		});
		
		this.tblCategory.setModel(dtm);
	}

	private void resetFormData() {
		txtCategory.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCategoryEntry = new JFrame();
		frmCategoryEntry.setTitle("Category Entry");
		frmCategoryEntry.setBounds(50, 100, 600, 500);
		frmCategoryEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCategoryEntry.getContentPane().setLayout(null);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setHorizontalAlignment(SwingConstants.LEFT);
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCategory.setBounds(47, 39, 85, 29);
		frmCategoryEntry.getContentPane().add(lblCategory);

		txtCategory = new JTextField();
		txtCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtCategory.setColumns(10);
		txtCategory.setBounds(47, 78, 193, 29);
		frmCategoryEntry.getContentPane().add(txtCategory);

		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic('S');
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Category category = new Category();
				try {
					category.setCategory(txtCategory.getText());

				if (!category.getCategory().isBlank()) {

					categoryService.createCategory(category);
					resetFormData();
					loadAllCategories(Optional.empty());
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
		frmCategoryEntry.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(280, 78, 165, 29);
		frmCategoryEntry.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = txtSearch.getText();
				System.out.println(categoryList);
				loadAllCategories(
						Optional.of(categoryList.stream()
								.filter(ctgr->ctgr.getCategory().toLowerCase().startsWith(keyword.toLowerCase()))
								.collect(Collectors.toList()))
						
						);
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(450, 78, 165, 29);
		btnSearch.setSize(100,30);
		frmCategoryEntry.getContentPane().add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(276, 132, 662, 292);
		frmCategoryEntry.getContentPane().add(scrollPane);
		scrollPane.setSize(300,300);

		tblCategory = new JTable();
		tblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblCategory);
		this.tblCategory.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblCategory.getSelectionModel().isSelectionEmpty()) {

				String id = tblCategory.getValueAt(tblCategory.getSelectedRow(), 0).toString();

				category = categoryService.findCategoryById(id);

				txtCategory.setText(category.getCategory());

			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					category.setCategory(txtCategory.getText());
					if (!category.getCategory().isBlank()) {

						categoryService.updateCategory(String.valueOf(category.getCategory_id()), category);
						resetFormData();
						loadAllCategories(Optional.empty());
						category = null;

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
		frmCategoryEntry.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setMnemonic('D');
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(47, 406, 193, 29);
		frmCategoryEntry.getContentPane().add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					category.setCategory(txtCategory.getText());
					if (!category.getCategory().isBlank()) {
						
						categoryService.deleteCategory(String.valueOf(category.getCategory_id()), category);
						resetFormData();
						loadAllCategories(Optional.empty());
						category = null;

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
