package org.neoflex.dto.response.user;


import lombok.Data;

@Data
public class userPreviewCardDto {
    private Long userId;
    private String userName;
    private String email;
    private String departmentName;
}
