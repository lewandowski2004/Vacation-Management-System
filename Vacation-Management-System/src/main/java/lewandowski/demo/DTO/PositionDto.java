package lewandowski.demo.DTO;

import lewandowski.demo.Model.Department;
import lewandowski.demo.Model.Employee;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PositionDto {

    private int id;

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    @NotEmpty(message = "Pole nie może być puste")
    private String name;

    @NotNull(message = "Wybierz dział z listy")
    private DepartmentDto departmentDto;

    private EmployeeDto employeeDto;

    private int nrDepartment;
}
