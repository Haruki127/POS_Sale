package model;

public class CreateAccount {
	private String name;
	private String password;
	private String role;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "CreateAccount [name=" + name + ", password=" + password + ", role=" + role + "]";
	}
}
