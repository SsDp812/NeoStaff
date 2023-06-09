package org.neoflex.dto.request.action_type;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.time.Duration;
import java.time.Period;

@Data
public class UpdateActionTypeDto {
    @Positive
    private Long actionId;

    @NotBlank(message = "Action type name must be not blank")
    @Size(max = 30, message = "Action type name should not be more than 30 symbols")
    private String name;

    @Positive
    private Duration interval;


    private Boolean isNotify;
}
