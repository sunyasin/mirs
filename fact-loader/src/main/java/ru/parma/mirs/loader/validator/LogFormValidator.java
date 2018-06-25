package ru.parma.mirs.loader.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.parma.mirs.loader.model.LogForm;

@Component
public class LogFormValidator implements Validator {

    public boolean supports( Class clazz ) {
        return LogForm.class.isAssignableFrom(clazz);
    }

    public void validate( Object target, Errors errors ) {

        LogForm form = (LogForm) target;
        if (StringUtils.isNotEmpty(form.getLogDate()) && !form.getLogDate().matches("^\\d{8}$")) {
            errors.rejectValue("logDate", "required.endPeriod");
        }
    }

}