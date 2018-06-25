package ru.parma.mirs.loader.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.parma.mirs.loader.model.LoaderFormDto;

@Component
public class LoaderFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return LoaderFormDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

        LoaderFormDto cust = (LoaderFormDto) target;
        if (StringUtils.isNotEmpty(cust.getEndPeriod()) && !cust.getEndPeriod().matches("^\\d{8}$")) {
            errors.rejectValue("endPeriod", "required.endPeriod");
        }
/*
        if (true) {
            errors.rejectValue("elementName", "message_bundle.required.favFrameworks");
        }
*/

    }

}