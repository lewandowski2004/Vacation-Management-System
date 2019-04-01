package lewandowski.demo.Utilities;

import lewandowski.demo.Model.Employee;
import lewandowski.demo.Service.EmployeeService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EmployeeModel {

    private  EmployeeService employeeService;

    public static String getLoggedEmployee() {

        String username = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            username = auth.getName();
        }
        return username;
    }

}
