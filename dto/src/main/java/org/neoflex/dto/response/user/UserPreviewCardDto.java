package org.neoflex.dto.response.user;


import lombok.Data;

@Data
public class UserPreviewCardDto {
    private Long userId;
    private String userName;
    private String email;
    private String departmentName;
}
