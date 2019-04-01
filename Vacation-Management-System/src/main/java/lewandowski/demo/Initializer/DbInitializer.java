/*package lewandowski.demo.Initializer;

import lewandowski.demo.DAO.*;
import lewandowski.demo.Model.Department;
import lewandowski.demo.Model.Employee;
import lewandowski.demo.Model.Position;
import lewandowski.demo.Model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
    private DepartmentRepository departmentRepository;
    private PositionRepository positionRepository;
    private RoleRepository roleRepository;
    private EmployeeRepository employeeRepository;
    private VacationBalanceRepository vacationBalanceRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(String... args) throws Exception {

        Department department = new Department("Magazyn");
        Position position = new Position("Magazynier",department);
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        *//*Employee employee = new Employee();
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee = new Employee("Radek","Lewandowski","lewandowski2004@o2.pl",930420099,1993-20-04,"ciółkówko 49",
                "Ciółkówko","09-451","512445654",employee,)*//*
        departmentRepository.save(department);
        positionRepository.save(position);
        roleRepository.save(role1);
        roleRepository.save(role2);
    }
}*/
