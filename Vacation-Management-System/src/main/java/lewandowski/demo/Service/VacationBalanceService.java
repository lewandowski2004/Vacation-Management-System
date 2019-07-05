package lewandowski.demo.Service;

import lewandowski.demo.DTO.VacationBalanceDto;
import lewandowski.demo.Model.VacationBalance;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface VacationBalanceService {

    /*Entity method*/

    VacationBalance saveVacationBalance (VacationBalance vacationBalance);
    void deleteVacationBalanceByEmployeeId(UUID employeeId);
    VacationBalance findByEmployee_IdAndYear(UUID employeeId, Date year);
    void updateVacationLeave(Integer newVacationLeave,  UUID employeeId, Date year);
    void updateEmergencyLeave(Integer newEmergencyLeave, UUID employeeId, Date year);
    void updateAnnualLeave(Integer newAnnualLeave, UUID employeeId, Date year);
    void updateDaysOfVacation(Integer newVacationLeave, Integer newEmergencyLeave, Integer newAnnualLeave,
                              Integer newVacationLimit, UUID employeeId, Date year);

    /*Dto method*/

    void saveVacationBalanceDto(VacationBalanceDto vacationBalanceDto);
    VacationBalance getVacationBalance(VacationBalanceDto vacationBalanceDto);
    List<VacationBalanceDto> findVacationBalancesByYear(Date year);
    List<VacationBalanceDto> findVacationBalancesByYearIsNotIn(Date year);
    Set<VacationBalanceDto> getVacationsBalanceDto(Set<VacationBalance> vacationBalanceList);
    Set<VacationBalance> getVacationsBalance(Set<VacationBalanceDto> vacationBalanceDtoList);
    VacationBalanceDto getVacationBalanceDto(VacationBalance vacationBalance);
    VacationBalanceDto findVacationBalanceDtoByEmployee_IdAndYear(UUID employeeId, Date year);
}
