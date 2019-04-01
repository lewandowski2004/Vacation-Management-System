package lewandowski.demo.DAO;

import lewandowski.demo.Model.Employee;
import lewandowski.demo.Model.VacationBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface VacationBalanceRepository extends JpaRepository<VacationBalance, Integer> {

    VacationBalance findByEmployee_IdAndYear(UUID employeeId, Date year);
    VacationBalance save (VacationBalance vacationBalance);
    void deleteVacationBalanceByEmployee_Id(UUID employeeId);
    List<VacationBalance> findVacationBalancesByYear(Date year);
    List<VacationBalance> findVacationBalancesByYearIsNotIn(Date year);


    @Modifying
    @Query(value = "UPDATE vacation_balance v SET v.vacation_leave = :newVacationLeave WHERE v.employee_id = :employeeId AND year = :year", nativeQuery = true)
    void updateVacationLeave(@Param("newVacationLeave") Integer newVacationLeave, @Param("employeeId") UUID employeeId, @Param("year") Date year);

    @Modifying
    @Query(value = "UPDATE vacation_balance v SET v.emergency_vacation = :newEmergencyLeave WHERE v.employee_id = :employeeId AND year = :year", nativeQuery = true)
    void updateEmergencyLeave(@Param("newEmergencyLeave") Integer newEmergencyLeave, @Param("employeeId") UUID employeeId, @Param("year") Date year);

    @Modifying
    @Query(value = "UPDATE vacation_balance v SET v.annual_vacation = :newAnnualLeave WHERE v.employee_id = :employeeId AND year = :year", nativeQuery = true)
    void updateAnnualLeave(@Param("newAnnualLeave") Integer newAnnualLeave, @Param("employeeId") UUID employeeId, @Param("year") Date year);

}
