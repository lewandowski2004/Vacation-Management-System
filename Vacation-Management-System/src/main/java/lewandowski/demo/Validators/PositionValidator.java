package lewandowski.demo.Validators;

import lewandowski.demo.Model.Position;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PositionValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Position.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Position position = (Position) o;

        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors,"department","employee.department.empty");
    }
}
