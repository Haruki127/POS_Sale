package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.CreateAccount;
import model.Employee;
import shared.mapper.BrandMapper;

public class CreateAccountService {
    private BrandMapper brandMapper = new BrandMapper();
	private config.DBconfig dbConfig = new config.DBconfig();

	public CreateAccountService() {
	    this.brandMapper = new BrandMapper();
		this.dbConfig = new config.DBconfig();
	}
	

	public void createCreateAccount(Employee passInfo,CreateAccount ca) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("update employee "
							+ "set username=?, password=?, role=?, active=?"
							+ " WHERE emp_name = '" +passInfo.getName()+"' and emp_phone = '"+passInfo.getPhone()+"' and emp_email = '"+passInfo.getEmail()+"' and emp_address = '"+passInfo.getAddress()+"'");

			ps.setString(1, ca.getName());
			ps.setString(2, ca.getPassword());
			ps.setString(3, ca.getRole());
			ps.setInt(4, 1);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createCreateAccount1(Object passInfo) {
		// TODO Auto-generated method stub
		
	}


//
//	public Brand findBrandById(String id) {
//		Brand brand = new Brand();
//
//		try (Statement st = this.dbConfig.getConnection().createStatement()) {
//
//			String query = "SELECT * FROM brand WHERE brand_id = " + id + ";";
//
//			ResultSet rs = st.executeQuery(query);
//
//			if (rs.next()) {
//				this.brandMapper.mapToBrand(brand, rs);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return brand;
//	}
//	
//    public void updateBrand(String id, Brand brand) {
//        try {
//            PreparedStatement ps = this.dbConfig.getConnection()
//                    .prepareStatement("UPDATE brand SET brandName=? WHERE brand_id=?");
//
//            ps.setString(1, brand.getBrand());
//            ps.setString(2, id);
//
//            ps.executeUpdate();
//            ps.close();
//        } catch (Exception e) {
//
//        	e.printStackTrace();
//        }
//    }
//    
//    public void deleteBrand(String id, Brand brand) {
//        try {
//            PreparedStatement ps = this.dbConfig.getConnection()
//            .prepareStatement("delete from brand where brand_id=?");
//
//            ps.setString(1, id);
//            ps.executeUpdate();
//            ps.close();
//        } catch (Exception e) {
//
//        	e.printStackTrace();
//        }
//    }
//    
//    public List<Brand> findAllBrands(){
//    	List<Brand> brandList = new ArrayList<>();
//    	try (Statement st = this.dbConfig.getConnection().createStatement()){
//    		String query = "SELECT * FROM brand";
//    		ResultSet rs = st.executeQuery(query);
//    		while(rs.next()) {
//    			Brand brand = new Brand();
//    			brandList.add(this.brandMapper.mapToBrand(brand, rs));
//    		}
//    		
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    	}
//    	return brandList;
//    }

	
}
