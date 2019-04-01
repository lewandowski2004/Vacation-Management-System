package lewandowski.demo.Service;

import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.DTO.EmployeeWithVacationBalanceDto;
import lewandowski.demo.DTO.VacationBalanceDto;

public interface EmployeeVacationBalanceService {

    void saveEmployeeVacationBalanceDto(EmployeeWithVacationBalanceDto employeeWithVacationBalanceDto);
}
