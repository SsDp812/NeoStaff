package org.neoflex.dto.request.action_type;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Duration;

@Data
public class CreateActionTypeDto {
    @NotBlank(message = "Action type name must be not blank")
    @Size(max = 30, message = "Action type name should not be more than 30 symbols")
    private String actionTypeName;
    @Positive
    private Duration interval;
    private Boolean isNotify;
}
