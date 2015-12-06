package com.assignment.commands;

import org.springframework.shell.core.Completion;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.MethodTarget;

import java.time.LocalDateTime;
import java.util.List;

public class TimestampConverter implements Converter<LocalDateTime> {
    @Override
    public boolean supports(Class<?> type, String optionContext) {
        return LocalDateTime.class.isAssignableFrom(type);
    }

    @Override
    public LocalDateTime convertFromText(String value, Class<?> targetType, String optionContext) {
        return LocalDateTime.parse(value);
    }

    @Override
    public boolean getAllPossibleValues(List<Completion> completions, Class<?> targetType, String existingData, String optionContext, MethodTarget target) {
        return false;
    }
}
