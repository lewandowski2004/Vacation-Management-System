package lewandowski.demo.DAO;

import lewandowski.demo.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findById(UUID id);
    Employee findByName(String name);
    Employee findByEmail(String email);
    Employee save (Employee employee);
    List<Employee> findEmployeesByPosition_Id(int positionId);


    @Query(value = "SELECT * FROM employee INNER JOIN vacation_balance ON employee.employee_id = vacation_balance.employee_id WHERE year = :yea ", nativeQuery = true)
    List<Employee> findEmployeeByVacationBalancesWhereYearIs(@Param("yea") Date yea);

    @Query(value = "SELECT * FROM employee INNER JOIN vacation_balance ON employee.employee_id = vacation_balance.employee_id WHERE year != :yea ", nativeQuery = true)
    List<Employee> findEmployeeByVacationBalancesWhereYearIsNot(@Param("yea") Date yea);

    @Query(value = "SELECT * FROM employee ORDER BY date_of_addition DESC LIMIT 1", nativeQuery = true)
    Employee getLastEmployee();

    @Modifying
    @Query("UPDATE Employee e SET e.password = :newPassword WHERE e.email= :email")
    void updateEmployeePassword(@Param("newPassword") String password, @Param("email") String email);


    @Modifying
    @Query(value = "UPDATE employee e SET e.department_id = :newDepartmentId, e.position_id = :newPositionId WHERE e.employee_id = :id", nativeQuery = true)
    void updateDepartmentAndPositionEmployee(@Param("newDepartmentId") Integer newDepartmentId, @Param("newPositionId") Integer newPositionId,
                                             @Param("id") UUID id);

    @Modifying
    @Query("UPDATE Employee e SET e.name = :newName, e.lastName = :newLastName, e.email = :newEmail,e.pesel =:newPesel, e.dateOfBirth= :newDateOfBirth," +
            "  e.addressLine1 = :newAddressLine1, e.addressLine2 = :newAddressLine2, e.city = :newCity, e.zipCode= :newZipCode, e.phoneNumber = :newPhoneNumber" +
            " WHERE e.id= :id")
    void updateEmployeeProfile(@Param("newName") String newName, @Param("newLastName") String newLastName,
                               @Param("newEmail") String newEmail,@Param("newPesel") Long newPesel,
                               @Param("newDateOfBirth") Date newDateOfBirth, @Param("newAddressLine1") String newAddressLine2,
                               @Param("newAddressLine2") String newAddressLine1,  @Param("newCity") String newCity,
                               @Param("newZipCode") String newZipCode, @Param("newPhoneNumber") String newPhoneNumber,
                               @Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE employee_role r SET r.role_id = :roleId WHERE r.employee_id= :id", nativeQuery = true)
    void updateRoleEmployee(@Param("roleId") int nrRoli, @Param("id") UUID id);

    @Modifying
    @Query(value = "DELETE FROM employee_role WHERE employee_id = :id", nativeQuery = true)
    void deleteEmployeeFromEmployeeRole(@Param("id") UUID id);

    void deleteEmployeeById(UUID employeeId);


}
