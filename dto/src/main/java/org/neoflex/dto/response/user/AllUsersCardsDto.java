package org.neoflex.dto.response.user;


import lombok.Data;

import java.util.List;

@Data
public class AllUsersCardsDto {
    public List<UserPreviewCardDto> userPreviewCards;
}
