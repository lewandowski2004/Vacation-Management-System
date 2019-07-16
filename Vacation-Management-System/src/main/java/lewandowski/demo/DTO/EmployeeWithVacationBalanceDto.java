package lewandowski.demo.DTO;

import lewandowski.demo.Model.*;
import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class EmployeeWithVacationBalanceDto {

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    @NotEmpty(message = "Pole nie może być puste")
    private String name;

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    @NotEmpty(message = "Pole nie może być puste")
    private String lastName;

    @NotEmpty(message = "Pole nie może być puste")
    @Pattern(regexp = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", message = "Podaj poprawny email!")
    private String email;

    @NotEmpty(message = "Pole nie może być puste")
    @PESEL(message = "Nieprawidłowy numer PESEL !")
    private String pesel;

    //@NotNull(message = "Pole nie może być puste")
    @DateTimeFormat(pattern="dd-MMM-yyyy")
    private Date dateOfBirth;

    private Date dateOfAddition;

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    @NotEmpty(message = "Pole nie może być puste")
    private String addressLine1;

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    private String addressLine2;

    @Size(max = 255, message = "Pole może zawierać więcej niż 255 znaków")
    @NotEmpty(message = "Pole nie może być puste")
    private String city;

    @NotEmpty(message = "Pole nie może być puste")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Podaj poprawny kod pocztowy: 00-000")
    private String zipCode;

    @NotEmpty(message = "Pole nie może być puste")
    private String phoneNumber;

    //@NotEmpty(message = "Pole nie może być puste")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{8,12}$",
            message = "Hasło powinno zawierać dużą i małą literę, cyfrę oraz jeden ze znaków !, @, #, $.")
    private String password;

    //@NotEmpty(message = "Pole nie może być puste")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{8,12}$",
            message = "Hasło powinno zawierać dużą i małą literę, cyfrę oraz jeden ze znaków !, @, #, $.")
    private String confirmPassword;

    private int active;

    private Set<RoleDto> rolesDto;

    private Set<ApplicationDto> applicationsDto;

    private Set<VacationBalanceDto> vacationBalancesDto;

    @NotNull(message = "Wybierz dział z listy")
    private DepartmentDto departmentDto ;

    @NotNull(message = "Wybierz stanowisko z listy")
    private PositionDto positionDto;

    @NotNull(message = "Wybierz role z listy")
    private int nrRoli;

    @NotNull(message = "Wybierz dział z listy")
    private int nrDepartment;

    @NotNull(message = "pole nie może być puste")
    private int vacationLimit;

    @NotNull(message = "pole nie może być puste")
    private int annualVacation;

    @NotNull(message = "pole nie może być puste")
    private int vacationLeave;

    @NotNull(message = "pole nie może być puste")
    private int emergencyVacation;

    private Date year;

    private EmployeeDto employeeDto;
}
