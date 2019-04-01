package lewandowski.demo.DTO;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ApplicationDto {

    private UUID id;

    private Date dateOfAddition;

    @NotNull(message = "Wybierz datę rozpoczęcia")
    private Date startOfVacation;

    @NotNull(message = "Wybierz datę zakończenia")
    private Date endOfVacation;

    private int vacationDays;

    private boolean vacationPlan;

    private ApplicationStatusDto applicationStatusDto;

    @NotNull(message = "Wybierz typ urlopu  z listy")
    private VacationTypeDto vacationTypeDto;

    private EmployeeDto employeeDto;

    private int nrStatus;
}
