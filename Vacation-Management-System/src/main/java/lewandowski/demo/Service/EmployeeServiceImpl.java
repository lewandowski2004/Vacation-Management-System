package lewandowski.demo.Service;

import lewandowski.demo.DAO.RoleRepository;
import lewandowski.demo.DAO.EmployeeRepository;
import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.Model.Employee;
import lewandowski.demo.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private VacationBalanceService vacationBalanceService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;

    /*Entity method*/

    @Override
    public Employee findEmployeeById(UUID id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Page<Employee> findAllEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }
    @Override
    public Boolean emailIsUnique(String email) {
        if(employeeRepository.findByEmail(email) == null){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public List<Employee> findAllEmployeesList() {
        return employeeRepository.findAll();
    }
    @Override
    public void saveEmployee(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setActive(1);

        Role role = roleRepository.findByRole("ROLE_EMPLOYEE");
        employee.setRoles(new HashSet<Role>(Arrays.asList(role)));
        employeeRepository.save(employee);
    }
    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    @Override
    public void updateEmployeePassword(String newPassword, String email) {
        employeeRepository.updateEmployeePassword(bCryptPasswordEncoder.encode(newPassword), email);
    }
    @Override
    public Boolean confirmPassword(String oldPassword, String encodePassword) {
       return bCryptPasswordEncoder.matches(oldPassword, encodePassword);
    }
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEmployeeById(UUID id) {
        employeeRepository.deleteEmployeeFromEmployeeRole(id);
        employeeRepository.deleteEmployeeById(id);
    }
    @Override
    public void updateEmployeeProfile(String newName, String newLastName, String newEmail, Long newPesel, Date newDateOfBirth,
                                      String newAddressLine1, String newAddressLine2, String newCity, String newZipCode,
                                      String newPhoneNumber,UUID id) {
        employeeRepository.updateEmployeeProfile(newName,newLastName,newEmail,newPesel,newDateOfBirth,newAddressLine1,
                                                    newAddressLine2,newCity,newZipCode,newPhoneNumber, id);
    }
    @Override
    public void updateDepartmentAndPositionEmployee(Integer newDepartmentId, Integer newPositionId, UUID id) {
        employeeRepository.updateDepartmentAndPositionEmployee(newDepartmentId,newPositionId,id);
    }
    @Override
    public void updateEmployeeRole(UUID id, int nrRoli) {
        employeeRepository.updateRoleEmployee(nrRoli,id);
    }
/*    @Override
    public UUID getLastEmployeeId() {
        return employeeRepository.getLastEmployeeId();
    }*/

    /*Dto method*/

    public EmployeeDto findById(UUID id) {
        if (!id.equals("")) {
            Employee employee = employeeRepository.findById(id);
            if (employee != null) {
                return getEmployeeDto(employee);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    @Override
    public EmployeeDto findByName(String name) {
        if (!name.equals("")) {
            Employee employee = employeeRepository.findByName(name);
            if (employee != null) {
                return getEmployeeDto(employee);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void saveEmployeeDto(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .name(employeeDto.getName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .pesel(employeeDto.getPesel())
                .dateOfBirth(employeeDto.getDateOfBirth())
                .dateOfAddition(new Date())
                .addressLine1(employeeDto.getAddressLine1())
                .addressLine2(employeeDto.getAddressLine2())
                .city(employeeDto.getCity())
                .zipCode(employeeDto.getZipCode())
                .phoneNumber(employeeDto.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(employeeDto.getPassword()))
                .department(departmentService.getDepartment(employeeDto.getDepartmentDto()))
                .position(positionService.getPosition(employeeDto.getPositionDto()))
                .build();

        Role role = roleRepository.findByRole("ROLE_EMPLOYEE");
        employee.setRoles(new HashSet<Role>(Arrays.asList(role)));
        employee.setActive(1);

        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> findAllEmployeesDto() {
        return findAllEmployeesDto(employeeRepository.findAll());
    }

    @Override
    public List<EmployeeDto> findAllEmployeesDtoByVacationBalancesWhereYearIs(Date year) {
        return findAllEmployeesDto(employeeRepository.findEmployeeByVacationBalancesWhereYearIs(year));
    }

    @Override
    public List<EmployeeDto> findAllEmployeesDtoByVacationBalancesWhereYearIsNot(Date year) {
        return findAllEmployeesDto(employeeRepository.findEmployeeByVacationBalancesWhereYearIsNot(year));
    }



    public List<EmployeeDto> findAllEmployeesDto(List<Employee> employeesList) {
        List<EmployeeDto> employeeDtoListDtoList = new ArrayList<>();
        for (Employee employee : employeesList) {
            int numerRoli = employee.getRoles().iterator().next().getId();
            employee.setNrRoli(numerRoli);
            EmployeeDto employeeDto = getEmployeeDto(employee);
            employeeDtoListDtoList.add(employeeDto);
        }
        return employeeDtoListDtoList;
    }

    public EmployeeDto findByEmail(String email) {
        if (!email.equals("")) {
            Employee employee = employeeRepository.findByEmail(email);
            if (employee != null) {
                return getEmployeeDto(employee);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public Employee getEmployee(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .pesel(employeeDto.getPesel())
                .dateOfBirth(employeeDto.getDateOfBirth())
                .addressLine1(employeeDto.getAddressLine1())
                .addressLine2(employeeDto.getAddressLine2())
                .city(employeeDto.getCity())
                .zipCode(employeeDto.getZipCode())
                .phoneNumber(employeeDto.getPhoneNumber())
                .password(employeeDto.getPassword())
                .confirmPassword(employeeDto.getConfirmPassword())
                .active(employeeDto.getActive())
                .roles(roleService.getRoles(employeeDto.getRolesDto()))
                .department(departmentService.getDepartment(employeeDto.getDepartmentDto()))
                .position(positionService.getPosition(employeeDto.getPositionDto()))
                //.vacationBalances(vacationBalanceService.getVacationsBalance(employeeDto.getVacationBalancesDto()))
                .nrRoli(employeeDto.getNrRoli())
                .nrDepartment(employeeDto.getNrDepartment())
                .build();
    }

    public EmployeeDto getEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .pesel(employee.getPesel())
                .dateOfBirth(employee.getDateOfBirth())
                .addressLine1(employee.getAddressLine1())
                .addressLine2(employee.getAddressLine2())
                .city(employee.getCity())
                .zipCode(employee.getZipCode())
                .phoneNumber(employee.getPhoneNumber())
                .password(employee.getPassword())
                .confirmPassword(employee.getConfirmPassword())
                .active(employee.getActive())
                .rolesDto(roleService.getRolesDto(employee.getRoles()))
                .departmentDto(departmentService.getDepartmentDtoWithoutPosition(employee.getDepartment()))
                .positionDto(positionService.getPositionDtoWithoutDepartment(employee.getPosition()))
                .vacationBalancesDto(vacationBalanceService.getVacationsBalanceDto(employee.getVacationBalances()))
                .nrRoli(employee.getNrRoli())
                .nrDepartment(employee.getNrDepartment())
                .build();
    }

}
