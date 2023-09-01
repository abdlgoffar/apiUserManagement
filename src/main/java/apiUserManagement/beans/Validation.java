package apiUserManagement.beans;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@AllArgsConstructor
@Component
public class Validation {

    private final Validator validator;

    /**
     * validation bean
     * @param request
     */
    public void valid(Object request) {
        Set<ConstraintViolation<Object>> validate = this.validator.validate(request);
        if (validate.size() != 0) {
            throw new ConstraintViolationException(validate);
        }
    }
}
