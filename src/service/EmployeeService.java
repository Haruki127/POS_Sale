package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

//import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import config.DBconfig;
import model.Employee;
import model.Supplier;
import shared.mapper.EmployeeMapper;

public class EmployeeService {
	private final EmployeeMapper employeeMapper;
	private final config.DBconfig dbConfig;
	public EmployeeService() {
	    this.employeeMapper = new EmployeeMapper();
		this.dbConfig = new config.DBconfig();
	}
	public void createEmployee(Employee employee)
	{
		try
		{
			PreparedStatement ps=this.dbConfig.getConnection().
					prepareStatement("INSERT INTO employee(emp_name,emp_phone,emp_email,emp_address) VALUES(?,?,?,?)");
			ps.setString(1, employee.getName());
	        ps.setString(2, employee.getPhone());
	        ps.setString(3, employee.getEmail());
	        ps.setString(4, employee.getAddress());
	        System.out.println(ps.toString());
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Employee findEmployeeById(String id) {
		Employee employee = new Employee();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "SELECT * FROM employee WHERE emp_id = " + id + ";";

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.employeeMapper.mapToEmployee(employee, rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employee;
	}
	
	public void updateEmployee(String id, Employee employee) 
	{
		try
		{
			PreparedStatement ps = this.dbConfig.getConnection()
			  .prepareStatement("UPDATE employee SET emp_name=?,emp_phone=?,"
			  		+ "emp_email=?, emp_address=? WHERE emp_id=?");

			ps.setString(1, employee.getName());
			ps.setString(2, employee.getPhone());
			ps.setString(3, employee.getEmail());
			ps.setString(4, employee.getAddress());
	        ps.setString(5, id);
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteEmployee(String id,Employee employee)
	{
		try
		{
			PreparedStatement ps=this.dbConfig.getConnection().
					prepareStatement("DELETE FROM employee WHERE emp_id=?");
			
			ps.setString(1, id);
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
				e.printStackTrace();
			
		}
	}
	
	public List<Employee> findAllEmployees()
	{
		List<Employee> employeeList=new ArrayList<>();
		try(Statement st=this.dbConfig.getConnection().createStatement())
		{
//			String query="SELECT * FROM employee";
			String query="SELECT * FROM employee WHERE username is null and password is null and role is null and active is null";
			ResultSet rs=st.executeQuery(query);
			while(rs.next())
			{
				Employee employee=new Employee();
				employeeList.add(this.employeeMapper.mapToEmployee(employee, rs));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return employeeList;
	}
}
