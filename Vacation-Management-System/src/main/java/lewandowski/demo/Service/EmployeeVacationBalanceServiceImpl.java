package lewandowski.demo.Service;

import lewandowski.demo.DAO.EmployeeRepository;
import lewandowski.demo.DAO.RoleRepository;
import lewandowski.demo.DAO.VacationBalanceRepository;
import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.DTO.EmployeeWithVacationBalanceDto;
import lewandowski.demo.DTO.VacationBalanceDto;
import lewandowski.demo.Model.Employee;
import lewandowski.demo.Model.Role;
import lewandowski.demo.Model.VacationBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Service
@Transactional
public class EmployeeVacationBalanceServiceImpl implements EmployeeVacationBalanceService {


    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationBalanceRepository vacationBalanceRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveEmployeeVacationBalanceDto(EmployeeWithVacationBalanceDto employeeWithVacationBalanceDto) {
        Employee employee = Employee.builder()
                .name(employeeWithVacationBalanceDto.getName())
                .lastName(employeeWithVacationBalanceDto.getLastName())
                .email(employeeWithVacationBalanceDto.getEmail())
                .pesel(employeeWithVacationBalanceDto.getPesel())
                .dateOfBirth(employeeWithVacationBalanceDto.getDateOfBirth())
                .dateOfAddition(new Date())
                .addressLine1(employeeWithVacationBalanceDto.getAddressLine1())
                .addressLine2(employeeWithVacationBalanceDto.getAddressLine2())
                .city(employeeWithVacationBalanceDto.getCity())
                .zipCode(employeeWithVacationBalanceDto.getZipCode())
                .phoneNumber(employeeWithVacationBalanceDto.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(employeeWithVacationBalanceDto.getPassword()))
                .department(departmentService.getDepartment(employeeWithVacationBalanceDto.getDepartmentDto()))
                .position(positionService.getPosition(employeeWithVacationBalanceDto.getPositionDto()))
                .active(1)
                .build();

        Role role = roleRepository.findByRole("ROLE_EMPLOYEE");
        employee.setRoles(new HashSet<Role>(Arrays.asList(role)));

        employeeRepository.save(employee);


            VacationBalance vacationBalance = VacationBalance.builder()
                    .annualVacation(employeeWithVacationBalanceDto.getAnnualVacation())
                    .emergencyVacation(4)
                    .vacationLeave(employeeWithVacationBalanceDto.getAnnualVacation()-4)
                    .vacationLimit(employeeWithVacationBalanceDto.getAnnualVacation())
                    //.employee(employeeService.getEmployee(employeeWithVacationBalanceDto.getEmployeeDto()))
                    .year(new Date())
                    .build();

        //Employee lastSaveEmployee = employeeRepository.getLastEmployee();
        vacationBalance.setEmployee(employee);

        vacationBalanceRepository.save(vacationBalance);

    }
}
