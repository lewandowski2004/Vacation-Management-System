package lewandowski.demo.Validators;

import lewandowski.demo.Model.Employee;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditEmployeeValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;

        ValidationUtils.rejectIfEmpty(errors,"name","name.empty");
        ValidationUtils.rejectIfEmpty(errors,"lastName","employee.lastName.empty");
        ValidationUtils.rejectIfEmpty(errors,"dateOfBirth","employee.dateOfBirth.empty");
        ValidationUtils.rejectIfEmpty(errors,"addressLine1","employee.addressLine1.empty");
        ValidationUtils.rejectIfEmpty(errors,"city","employee.city.empty");
        ValidationUtils.rejectIfEmpty(errors,"zipCode","employee.zipCode.empty");
        ValidationUtils.rejectIfEmpty(errors,"phoneNumber","employee.phoneNumber.empty");
        ValidationUtils.rejectIfEmpty(errors,"department","employee.department.empty");
        ValidationUtils.rejectIfEmpty(errors,"position","employee.position.empty");

        if (!employee.getEmail().equals(null)) {
            boolean isMatch = checkEmailOrPassword(EMAIL_PATTERN, employee.getEmail());
            if(!isMatch) {
                errors.rejectValue("email", "error.employeeEmailIsNotMatch");
            }
        }

        if (!employee.getZipCode().equals(null)) {
            boolean isMatch = checkEmailOrPassword(ZIPCODE_PATTERN, employee.getZipCode());
            if(!isMatch) {
                errors.rejectValue("zipCode", "error.employeeZipCodeIsNotMatch");
            }
        }

    }
    public static boolean checkEmailOrPassword(String pattern, String pStr) {

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pStr);
        return m.matches();
    }

    public static final String EMAIL_PATTERN = "^[a-zA-z0-9]+[\\._a-zA-Z0-9]*@[a-zA-Z0-9]+{2,}\\.[a-zA-Z]{2,}[\\.a-zA-Z0-9]*$";

    public static final String ZIPCODE_PATTERN = "\\d{2}-\\d{3}";
}
