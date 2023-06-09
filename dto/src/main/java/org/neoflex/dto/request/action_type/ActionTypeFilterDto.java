package org.neoflex.dto.request.action_type;

import jakarta.validation.constraints.Positive;
import lombok.*;
import org.neoflex.model.ActionType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionTypeFilterDto {
    private ActionType actionType;
    @Positive
    private LocalDate actionDate;
}
