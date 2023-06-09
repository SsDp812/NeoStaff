package org.neoflex.dto.response.user;

import lombok.Data;
import org.neoflex.dto.response.action.ActionCardDto;

import java.time.ZonedDateTime;
import java.util.Set;

@Data
public class UserCardDto {
    private String email;
    private String name;
    private String departmentName;
    private ZonedDateTime hireDate;
    private Set<ActionCardDto> actionCardsDto;
}
