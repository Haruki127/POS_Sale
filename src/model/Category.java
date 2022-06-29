package model;

public class Category {
	private int category_id;
	private String category_name;
	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory() {
		return category_name;
	}
	public void setCategory(String category_name) {
		this.category_name = category_name;
	}
	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", category_name=" + category_name + "]";
	}
}