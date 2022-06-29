package shared.mapper;

import java.sql.ResultSet;

import model.Employee;

public class EmployeeMapper {

    public Employee mapToEmployee(Employee employee, ResultSet rs) {
        try {
            employee.setEmp_id(rs.getInt("emp_id"));
            employee.setName(rs.getString("emp_name"));
            employee.setPhone(rs.getString("emp_phone"));
            employee.setEmail(rs.getString("emp_email"));
            employee.setAddress(rs.getString("emp_address"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
            employee.setRole(rs.getString("role"));
            employee.setActive(rs.getInt("active"));
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
