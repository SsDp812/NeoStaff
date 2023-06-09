package org.neoflex.dto.request.action;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.time.Period;

@Data
public class CreateActionTypeDto {
    @NotBlank(message = "Action type name must be not blank")
    @Size(max = 30, message = "Action type name should not be more than 30 symbols")
    private String actionTypeName;
    @Positive
    private Period interval;
    private boolean isNotify;
}
