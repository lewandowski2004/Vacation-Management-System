package lewandowski.demo.Controller;

import lewandowski.demo.Model.Employee;
import lewandowski.demo.Service.EmployeeService;
import lewandowski.demo.Utilities.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAuthentication {

    @Autowired
    private HttpSession session;
    @Autowired
    private EmployeeService employeeService;

    @ModelAttribute("employee")
    private Employee loggedEmployee() {
        String username = EmployeeModel.getLoggedEmployee();
        Employee employee = employeeService.findEmployeeByEmail(username);
        return employee;
    }
}