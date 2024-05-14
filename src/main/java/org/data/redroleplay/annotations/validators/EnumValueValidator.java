package org.data.redroleplay.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.data.redroleplay.annotations.EnumValue;

import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {


    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return false;

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(enumValue -> enumValue.name().equals(value));

    }
}
