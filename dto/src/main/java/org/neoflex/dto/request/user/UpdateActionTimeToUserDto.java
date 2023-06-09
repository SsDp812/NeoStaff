package org.neoflex.dto.request.user;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UpdateActionTimeToUserDto {
    @Positive
    private Long userId;
    @Positive
    private Long actionId;
    @Positive
    @Future
    private ZonedDateTime actionTime;
}
