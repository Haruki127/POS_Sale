package shared.mapper;

import java.sql.ResultSet;

import model.Supplier;

public class SupplierMapper {

    public Supplier mapToSupplier(Supplier supplier, ResultSet rs) {
        try {
        	supplier.setSup_id(rs.getInt("sup_id"));
        	supplier.setName(rs.getString("sup_name"));
        	supplier.setPhone(rs.getString("sup_phone"));
        	supplier.setEmail(rs.getString("sup_email"));
        	supplier.setAddress(rs.getString("sup_address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplier;
    }
}
