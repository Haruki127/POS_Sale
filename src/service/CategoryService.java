package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import shared.mapper.CategoryMapper;

public class CategoryService {
    private CategoryMapper categoryMapper = new CategoryMapper();
	private config.DBconfig dbConfig = new config.DBconfig();

	public CategoryService() {
	    this.categoryMapper = new CategoryMapper();
		this.dbConfig = new config.DBconfig();
	}

	public void createCategory(Category category) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("INSERT INTO category (categoryName) VALUES (?)");

			ps.setString(1, category.getCategory());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Category findCategoryById(String id) {
		Category category = new Category();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT * FROM category WHERE category_id = " + id + ";";

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.categoryMapper.mapToCategory(category, rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return category;
	}
	
    public void updateCategory(String id, Category category) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE category SET categoryName=? WHERE category_id=?");

            ps.setString(1, category.getCategory());
            ps.setString(2, id);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {

        	e.printStackTrace();
        }
    }
    
    public void deleteCategory(String id, Category category) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
            .prepareStatement("delete from category where category_id=?");

            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {

        	e.printStackTrace();
        }
    }
    
    public List<Category> findAllCategories(){
    	List<Category> categoryList = new ArrayList<>();
    	try (Statement st = this.dbConfig.getConnection().createStatement()){
    		String query = "SELECT * FROM category";
    		ResultSet rs = st.executeQuery(query);
    		while(rs.next()) {
    			Category category = new Category();
    			categoryList.add(this.categoryMapper.mapToCategory(category, rs));
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return categoryList;
    }


}
