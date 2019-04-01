package lewandowski.demo.Controller;

import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.Model.Employee;
import lewandowski.demo.Utilities.EmployeeModel;
import lewandowski.demo.Service.EmployeeService;
import lewandowski.demo.Validators.ChangePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.validation.Valid;
import javax.ws.rs.GET;


@Controller
public class ProfileController {

    @Autowired
    private EmployeeService employeeService;

    @GET
    @RequestMapping(value = "/profil")
    public String showEmployeeProfile(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        int nrRoli = employeeDto.getRolesDto().iterator().next().getId();
        employeeDto.setNrRoli(nrRoli);
        model.addAttribute("employeeDto", employeeDto);
        return "profil";
    }

    @GET
    @RequestMapping(value = "/editPassword")
    public String editEmployeePassword(EmployeeDto employeeDto, BindingResult result, Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        employeeDto = employeeService.findByEmail(username);
        model.addAttribute("employeeDto", employeeDto);
        return "changePassword";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String editEmployeePasswordAction(EmployeeDto employeeDto, BindingResult result, Model model) {
        new ChangePasswordValidator().validate(employeeDto, result);
        if (result.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak");
            return "changePassword";
        } else {
            if (employeeService.confirmPassword(employeeDto.getOldPassword(), employeeService.findByEmail(employeeDto.getEmail()).getPassword()) == false) {
                model.addAttribute("failedMessage", "Stare hasło jest nieprawidłowe");
                return "changePassword";
            } else if (employeeDto.getNewPassword().equals(employeeDto.getConfirmPassword()) == false) {
                model.addAttribute("failedMessage", "Hasła muszą być takie same");
                return "changePassword";
            } else {
                employeeService.updateEmployeePassword(employeeDto.getNewPassword(), employeeDto.getEmail());
                return "redirect:/logout";
            }
        }
    }
}
