package org.neoflex.dto.response.action_type;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Period;

@Data
public class ActionTypeCardDto {

    private Long actionId;

    private String actionName;

    private Period interval;

    private Boolean isNotify;
}
