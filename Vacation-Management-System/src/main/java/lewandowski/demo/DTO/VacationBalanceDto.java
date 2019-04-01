package lewandowski.demo.DTO;

import lewandowski.demo.Model.Employee;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class VacationBalanceDto {

    private int id;

    private int vacationLimit;

    @NotNull(message = "Pole nie może być puste")
    private int annualVacation;

    private int vacationLeave;

    private int emergencyVacation;

    //@NotNull(message = "Wybierz rok")
    private Date year;

    private EmployeeDto employeeDto;
}
