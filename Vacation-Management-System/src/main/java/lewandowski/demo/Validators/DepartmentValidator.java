package lewandowski.demo.Validators;

import lewandowski.demo.DTO.DepartmentDto;
import lewandowski.demo.Model.Department;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DepartmentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return DepartmentDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DepartmentDto departmentDto = (DepartmentDto) o;

        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
    }
}
