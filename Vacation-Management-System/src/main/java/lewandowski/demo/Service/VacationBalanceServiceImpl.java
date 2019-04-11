package lewandowski.demo.Service;

import lewandowski.demo.DAO.VacationBalanceRepository;
import lewandowski.demo.DTO.VacationBalanceDto;
import lewandowski.demo.Model.VacationBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class VacationBalanceServiceImpl implements VacationBalanceService {

    @Autowired
    private VacationBalanceRepository vacationBalanceRepository;
    @Autowired
    private EmployeeService employeeService;

    /*Entity method*/

    @Override
    public VacationBalance saveVacationBalance(VacationBalance vacationBalance) {
        return vacationBalanceRepository.save(vacationBalance);
    }

    @Override
    public void deleteVacationBalanceByEmployeeId(UUID employeeId) {
        vacationBalanceRepository.deleteVacationBalanceByEmployee_Id(employeeId);
    }

    @Override
    public void updateVacationLeave(Integer newVacationLeave, UUID employeeId, Date year) {
        vacationBalanceRepository.updateVacationLeave(newVacationLeave,employeeId, year);
    }

    @Override
    public void updateEmergencyLeave(Integer newEmergencyLeave, UUID employeeId, Date year) {
        vacationBalanceRepository.updateEmergencyLeave(newEmergencyLeave,employeeId, year);
    }

    @Override
    public VacationBalance findByEmployee_IdAndYear(UUID employeeId, Date year) {
        return vacationBalanceRepository.findByEmployee_IdAndYear(employeeId,year);
    }

    @Override
    public void updateAnnualLeave(Integer newAnnualLeave, UUID employeeId, Date year) {
        vacationBalanceRepository.updateAnnualLeave(newAnnualLeave,employeeId, year);
    }

    /*Dto method*/

    @Override
    public void saveVacationBalanceDto(VacationBalanceDto vacationBalanceDto) {
        VacationBalance vacationBalance = VacationBalance.builder()
                .annualVacation(vacationBalanceDto.getAnnualVacation())
                .emergencyVacation(4)
                .vacationLeave(vacationBalanceDto.getAnnualVacation()-4)
                .vacationLimit(vacationBalanceDto.getAnnualVacation())
                .employee(employeeService.getEmployee(vacationBalanceDto.getEmployeeDto()))
                .year(vacationBalanceDto.getYear())
                .build();

        vacationBalanceRepository.save(vacationBalance);
    }

    @Override
    public List<VacationBalanceDto> findVacationBalancesByYear(Date year) {
        return findAllVacationsBalanceDtoWithEmployee(vacationBalanceRepository.findVacationBalancesByYear(year));
    }

    @Override
    public List<VacationBalanceDto> findVacationBalancesByYearIsNotIn(Date year) {
        return findAllVacationsBalanceDtoWithEmployee(vacationBalanceRepository.findVacationBalancesByYearIsNotIn(year));
    }


    public List<VacationBalanceDto> findAllVacationsBalanceDtoWithEmployee(List<VacationBalance> employeesList) {
        List<VacationBalanceDto> vacationBalanceDtoList = new ArrayList<>();
        for (VacationBalance vacationBalance : employeesList) {
            VacationBalanceDto vacationBalanceDto = getVacationBalanceDtoWithEmployee(vacationBalance);
            vacationBalanceDtoList.add(vacationBalanceDto);
        }
        return vacationBalanceDtoList;
    }

    @Override
    public VacationBalanceDto findVacationBalanceDtoByEmployee_IdAndYear(UUID employeeId, Date year) {
        VacationBalance vacationBalance = vacationBalanceRepository.findByEmployee_IdAndYear(employeeId, year);
        if (vacationBalance != null) {
            return getVacationBalanceDto(vacationBalance);
        } else {
            return null;
        }
    }

    public VacationBalance getVacationBalance(VacationBalanceDto vacationBalanceDto) {
        return VacationBalance.builder()
                .id(vacationBalanceDto.getId())
                .annualVacation(vacationBalanceDto.getAnnualVacation())
                .emergencyVacation(vacationBalanceDto.getEmergencyVacation())
                .vacationLeave(vacationBalanceDto.getVacationLeave())
                .vacationLimit(vacationBalanceDto.getVacationLimit())
                //.employee(employeeService.getEmployee(vacationBalanceDto.getEmployeeDto()))
                .year(vacationBalanceDto.getYear())
                .build();
    }
    public VacationBalanceDto getVacationBalanceDto(VacationBalance vacationBalance) {
        return VacationBalanceDto.builder()
                .id(vacationBalance.getId())
                .annualVacation(vacationBalance.getAnnualVacation())
                .emergencyVacation(vacationBalance.getEmergencyVacation())
                .vacationLeave(vacationBalance.getVacationLeave())
                .vacationLimit(vacationBalance.getVacationLimit())
                //.employeeDto(employeeService.getEmployeeDto(vacationBalance.getEmployee()))
                .year(vacationBalance.getYear())
                .build();
    }

    public VacationBalanceDto getVacationBalanceDtoWithEmployee(VacationBalance vacationBalance) {
        return VacationBalanceDto.builder()
                .id(vacationBalance.getId())
                .annualVacation(vacationBalance.getAnnualVacation())
                .emergencyVacation(vacationBalance.getEmergencyVacation())
                .vacationLeave(vacationBalance.getVacationLeave())
                .vacationLimit(vacationBalance.getVacationLimit())
                .employeeDto(employeeService.getEmployeeDto(vacationBalance.getEmployee()))
                .year(vacationBalance.getYear())
                .build();
    }

    public Set<VacationBalanceDto> getVacationsBalanceDto(Set<VacationBalance> vacationBalanceList) {
        Set<VacationBalanceDto> vacationsBalanceDto = new HashSet<>();
        for (VacationBalance vacationBalance : vacationBalanceList) {
            vacationsBalanceDto.add(getVacationBalanceDto(vacationBalance));
        }
        return vacationsBalanceDto;
    }
    public Set<VacationBalance> getVacationsBalance(Set<VacationBalanceDto> vacationBalanceDtoList) {
        Set<VacationBalance> vacationsBalance = new HashSet<>();
        for (VacationBalanceDto vacationBalanceDto : vacationBalanceDtoList) {
            vacationsBalance.add(getVacationBalance(vacationBalanceDto));
        }
        return vacationsBalance;
    }

}
