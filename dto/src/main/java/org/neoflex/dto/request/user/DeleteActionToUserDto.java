package org.neoflex.dto.request.user;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DeleteActionToUserDto {
    @Positive
    private Long userId;
    @Positive
    private Long actionId;
}
