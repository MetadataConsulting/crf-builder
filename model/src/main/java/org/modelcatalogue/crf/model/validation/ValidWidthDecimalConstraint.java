package org.modelcatalogue.crf.model.validation;

import org.modelcatalogue.crf.model.Item;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidWidthDecimalConstraint implements ConstraintValidator<ValidWidthDecimal, Item> {

    @Override public void initialize(ValidWidthDecimal constraintAnnotation) {}

    @Override
    public boolean isValid(Item value, ConstraintValidatorContext context) {
        if (value.getDataType() == null || value.getWidthDecimal() == null) {
            return true;
        }

        switch (value.getDataType()) {
            case INT:
                if ("w(d)".equals(value.getWidthDecimal())) {
                    return true;
                }
                Pattern intPattern = Pattern.compile("(\\d{1,2})\\(d\\)");
                Matcher intMatcher = intPattern.matcher(value.getWidthDecimal());
                if (!intMatcher.matches()) {
                    return false;
                }
                Integer intWidth = Integer.parseInt(intMatcher.group(1), 10);
                return intWidth >= 1 && intWidth <= 26;
            case ST:
                if ("w(d)".equals(value.getWidthDecimal())) {
                    return true;
                }
                Pattern stPatter = Pattern.compile("(\\d{1,4})\\(d\\)");
                Matcher stMatcher = stPatter.matcher(value.getWidthDecimal());
                if (!stMatcher.matches()) {
                    return false;
                }
                Integer stWidth = Integer.parseInt(stMatcher.group(1), 10);
                return stWidth >= 1 && stWidth <= 4000;
            case REAL:
                Pattern realPattern = Pattern.compile("([w\\d{1,4}])\\(([d\\d{1,4})])\\)");
                Matcher realMatcher = realPattern.matcher(value.getWidthDecimal());
                if (!realMatcher.matches()) {
                    return false;
                }
                Integer realWidth = realMatcher.group(1) != null && !"w".equals(realMatcher.group(1)) ? Integer.parseInt(realMatcher.group(1), 10) : 26;
                Integer realDecimal = realMatcher.group(2) != null && !"d".equals(realMatcher.group(2)) ? Integer.parseInt(realMatcher.group(2)) : Math.min(realWidth, 20);
                return realDecimal <= 20 && realDecimal <= realWidth && realWidth >= 1 && realWidth <= 26 ;
            default:
                return false;
        }
    }
}
