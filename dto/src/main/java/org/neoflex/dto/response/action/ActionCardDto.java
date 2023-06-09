package org.neoflex.dto.response.action;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.time.ZonedDateTime;

@Data
public class ActionCardDto {
    private String actionTypeName;
    private ZonedDateTime actionDate;
    private String actionComment;
}
