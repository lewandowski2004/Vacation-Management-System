package lewandowski.demo.Validators;

import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.Model.Employee;
import lewandowski.demo.Model.Position;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmployeeDto employeeDto = (EmployeeDto) o;

        ValidationUtils.rejectIfEmpty(errors, "oldPassword", "oldPassword.empty");
        ValidationUtils.rejectIfEmpty(errors,"newPassword","newPassword.empty");
        ValidationUtils.rejectIfEmpty(errors,"confirmPassword","confirmPassword.empty");

        if (!employeeDto.getNewPassword().equals(null)) {
            boolean isMatch = checkEmailOrPassword(PASSWORD_PATTERN, employeeDto.getNewPassword());
            if(!isMatch) {
                errors.rejectValue("newPassword", "error.employeePasswordIsNotMatch");
            }
        }
    }
    public static boolean checkEmailOrPassword(String pattern, String pStr) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pStr);
        return m.matches();
    }
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{8,12}$";
}