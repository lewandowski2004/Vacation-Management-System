package lewandowski.demo.DTO;

import lewandowski.demo.Model.Employee;
import lewandowski.demo.Model.Position;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class DepartmentDto {

    private int id;

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    @NotEmpty(message = "Pole nie może być puste")
    private String name;

    private Set<PositionDto> positionsDto;

    private EmployeeDto employeeDto;
}
