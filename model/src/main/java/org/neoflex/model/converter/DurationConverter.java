package org.neoflex.model.converter;

import jakarta.persistence.AttributeConverter;

import java.time.Duration;

public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        if (duration == null)
            return null;
        return duration.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long seconds) {
        if (seconds == null)
            return null;
        return Duration.ofSeconds(seconds);
    }
}
