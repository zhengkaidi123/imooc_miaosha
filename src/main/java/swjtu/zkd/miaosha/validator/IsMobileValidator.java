package swjtu.zkd.miaosha.validator;

import org.apache.commons.lang3.StringUtils;
import swjtu.zkd.miaosha.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!required && StringUtils.isEmpty(value)) {
            return true;
        } else {
            return ValidatorUtil.isMobile(value);
        }
    }
}
