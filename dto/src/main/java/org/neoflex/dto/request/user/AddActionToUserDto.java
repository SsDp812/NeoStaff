package org.neoflex.dto.request.user;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.neoflex.model.ActionType;

import java.time.ZonedDateTime;

@Data
public class AddActionToUserDto {
    @Positive
    private Long userId;

    private ActionType actionType;

    @Positive
    @Future
    private ZonedDateTime actionDate;

}
