package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Supplier;
import shared.mapper.SupplierMapper;

public class SupplierService {
    private final SupplierMapper supplierMapper;
	private config.DBconfig dbConfig = new config.DBconfig();

	public SupplierService() {
	    this.supplierMapper = new SupplierMapper();
		this.dbConfig = new config.DBconfig();
	}

	public void createSupplier(Supplier supplier) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("INSERT INTO supplier (sup_name, sup_phone, sup_email, sup_address) VALUES (?, ?, ?, ?)");

			ps.setString(1, supplier.getName());
			ps.setString(2, supplier.getPhone());
			ps.setString(3, supplier.getEmail());
			ps.setString(4, supplier.getAddress());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Supplier findSupplierById(String id) {
		Supplier supplier = new Supplier();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT * FROM supplier WHERE sup_id = " + id + ";";

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.supplierMapper.mapToSupplier(supplier, rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return supplier;
	}
	
    public void updateSupplier(String id, Supplier supplier) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE supplier SET sup_name=?, sup_phone=?, sup_email=?, sup_address=? WHERE sup_id=?");

            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getPhone());
            ps.setString(3, supplier.getEmail());
            ps.setString(4, supplier.getAddress());
            ps.setString(5, id);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {

        	e.printStackTrace();
        }
    }
    
    public void deleteSupplier(String id, Supplier supplier) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
            .prepareStatement("delete from supplier where sup_id=?");

            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {

        	e.printStackTrace();
        }
    }
    
    public List<Supplier> findAllSuppliers(){
    	List<Supplier> supplierList = new ArrayList<>();
    	try (Statement st = this.dbConfig.getConnection().createStatement()){
    		String query = "SELECT * FROM supplier";
    		ResultSet rs = st.executeQuery(query);
    		while(rs.next()) {
    			Supplier supplier = new Supplier();
    			supplierList.add(this.supplierMapper.mapToSupplier(supplier, rs));
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return supplierList;
    }
}
