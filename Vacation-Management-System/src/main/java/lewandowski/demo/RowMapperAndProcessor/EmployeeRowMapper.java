package lewandowski.demo.RowMapperAndProcessor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import lewandowski.demo.Model.Employee;
import org.springframework.jdbc.core.RowMapper;


public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setName(rs.getString("name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        //employee.setPesel(rs.getLong("pesel"));
        //employee.setDateOfBirth(rs.getDate("dateOfBirth"));
        //employee.setDateOfAddition(rs.getDate("dateOfAddition"));
        //employee.setAddressLine1(rs.getString("addressLine1"));
        //employee.setAddressLine2(rs.getString("addressLine2"));
        //employee.setCity(rs.getString("city"));
        //employee.setZipCode(rs.getString("zipCode"));
        //employee.setPhoneNumber(rs.getString("phoneNumber"));
        //employee.setPassword(rs.getString("password"));
        //employee.setActive(rs.getInt("active"));
        //employee.setDepartment(rs.getObject("department"));
        //employee.setPosition(rs.getString("position"));

        return employee;
    }

}