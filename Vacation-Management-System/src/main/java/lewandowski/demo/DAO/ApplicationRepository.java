package lewandowski.demo.DAO;

import lewandowski.demo.Model.Application;
import lewandowski.demo.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Application findById(UUID id);
    Application save (Application application);
    void deleteApplicationById(UUID applicationId);
    void deleteApplicationByEmployee_Id(UUID employeeId);
    void delete(Application application);
    List<Application> findAll();
    List<Application> findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDesc(UUID employeeId, boolean vacationPlan);
    List<Application> findApplicationsByEmployee_IdAndVacationPlan(UUID employeeId, boolean vacationPlan);
    List<Application> findApplicationByApplicationStatus_Id(int id);

    @Query(value = "SELECT * FROM application WHERE employee_id = :employeeId AND :date1 between start_of_vacation AND end_of_vacation", nativeQuery = true)
    List<Application> findAllApplicationsWithDuplicateDate(@Param("employeeId") UUID employeeId, @Param("date1") String date1);

    @Query(value = "SELECT * FROM application WHERE employee_id != :employeeId ORDER BY date_of_addition DESC", nativeQuery = true)
    List<Application> findAllApplicationsAdmin(@Param("employeeId") UUID employeeId);

    @Query(value = "SELECT * FROM application WHERE employee_id != :employeeId AND vacation_plan = true ORDER BY date_of_addition DESC", nativeQuery = true)
    List<Application> findAllVacationPlansAdmin(@Param("employeeId") UUID employeeId);


    @Query(value = "SELECT * FROM application INNER JOIN employee ON application.employee_id = employee.employee_id WHERE department_id = :departmentId and " +
            "application.employee_id != :employeeId ORDER BY application.date_of_addition DESC", nativeQuery = true)
    List<Application> findApplicationsEmployeeByDepartment(@Param("departmentId") Integer departmentId, @Param("employeeId") UUID employeeId);

    @Query(value = "SELECT * FROM application INNER JOIN employee ON application.employee_id = employee.employee_id WHERE department_id = :departmentId and " +
            "application.employee_id != :employeeId and vacation_plan = true ORDER BY application.date_of_addition DESC", nativeQuery = true)
    List<Application> findVacationPlansEmployeeByDepartment(@Param("departmentId") Integer departmentId, @Param("employeeId") UUID employeeId);

    @Modifying
    @Query(value = "UPDATE application a SET a.application_status_id = :applicationStatus where a.application_id= :id",nativeQuery = true)
    void updateStatusApplication(@Param("applicationStatus") Integer applicationStatus,@Param("id") UUID id);

}
