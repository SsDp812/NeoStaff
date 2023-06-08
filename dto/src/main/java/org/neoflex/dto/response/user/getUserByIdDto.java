package org.neoflex.dto.response.user;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class getUserByIdDto {
    private String email;
    private String name;
    private String departmentName;
    private ZonedDateTime hireDate;
    private String actionTypeName;
    private ZonedDateTime actionDate;
    private String actionComment;
}
