package lewandowski.demo.Service;

import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.Model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    /*Entity method*/

    Employee findEmployeeById(UUID id);
    Page<Employee> findAllEmployees(Pageable pageable);
    List<Employee> findAllEmployeesList();
    void saveEmployee(Employee employee);
    Employee findEmployeeByEmail(String email);
    void updateEmployeePassword(String newPassword, String email);
    void updateDepartmentAndPositionEmployee(Integer newDepartmentId,Integer newPositionId,UUID id);
    Boolean confirmPassword(String oldPassword, String encodePassword);
    Boolean emailIsUnique(String email);
    void deleteEmployeeById(UUID id);
    void updateEmployeeProfile(String newName, String newLastName, String newEmail, Long newPesel, Date newDateOfBirth,String newAddressLine1,
                               String newAddressLine2, String newCity, String newZipCode, String newPhoneNumber,UUID id);
    void updateEmployeeRole(UUID id, int nrRoli);
    //UUID getLastEmployeeId();

    /*DTO method*/

    void saveEmployeeDto(EmployeeDto employeeDto);
    List<EmployeeDto> findAllEmployeesDto();
    List<EmployeeDto> findAllEmployeesDtoByVacationBalancesWhereYearIs(Date year);
    List<EmployeeDto> findAllEmployeesDtoByVacationBalancesWhereYearIsNot(Date year);
    List<EmployeeDto> findEmployeesByPositionId(int positionId);
    List<EmployeeDto> findEmployeesByDepartmentId(int departmentId);
    List<EmployeeDto> findEmployeesByIdNotIn(List<UUID> employeeId);
    EmployeeDto findById(UUID id);
    EmployeeDto findEmployeeByVacationBalancesWhereYearIs(UUID employeeId, Date year);
    EmployeeDto findByName(String name);
    EmployeeDto findByEmail(String email);
    Employee getEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeDto(Employee employee);

}
