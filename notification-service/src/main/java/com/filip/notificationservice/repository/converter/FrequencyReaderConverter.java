package com.filip.notificationservice.repository.converter;

import com.filip.notificationservice.model.Frequency;
import org.springframework.core.convert.converter.Converter;

public class FrequencyReaderConverter implements Converter<Integer, Frequency> {
    @Override
    public Frequency convert(Integer days) {
        return Frequency.withDays(days);
    }
}
