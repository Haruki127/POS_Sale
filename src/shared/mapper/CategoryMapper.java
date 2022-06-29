package shared.mapper;

import java.sql.ResultSet;

import model.Category;

public class CategoryMapper {

    public Category mapToCategory(Category category, ResultSet rs) {
        try {
        	category.setCategory_id(rs.getInt("category_id"));
        	category.setCategory(rs.getString("categoryName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }
}
