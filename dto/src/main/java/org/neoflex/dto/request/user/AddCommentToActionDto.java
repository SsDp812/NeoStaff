package org.neoflex.dto.request.user;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class AddCommentToActionDto {
    @Positive
    private Long userId;
    @Positive
    private Long actionId;
    @NotBlank(message = "Action comment must be not blank")
    @Size(max = 1000, message = "Action comment should not be more than 30 symbols")
    private String actionComment;
}
