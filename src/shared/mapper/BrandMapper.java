package shared.mapper;

import java.sql.ResultSet;

import model.Brand;

public class BrandMapper {

    public Brand mapToBrand(Brand brand, ResultSet rs) {
        try {
        	brand.setBrand_id(rs.getInt("brand_id"));
        	brand.setBrand(rs.getString("brandName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brand;
    }
}
