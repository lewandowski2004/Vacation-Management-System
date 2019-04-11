package lewandowski.demo.Service;

import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.DTO.EmployeeWithVacationBalanceDto;
import lewandowski.demo.DTO.VacationBalanceDto;

import java.text.ParseException;

public interface EmployeeVacationBalanceService {

    void saveEmployeeVacationBalanceDto(EmployeeWithVacationBalanceDto employeeWithVacationBalanceDto) throws ParseException;
}
