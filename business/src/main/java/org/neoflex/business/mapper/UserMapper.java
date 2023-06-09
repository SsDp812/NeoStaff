package org.neoflex.business.mapper;

import org.neoflex.dto.response.action.ActionCardDto;
import org.neoflex.dto.response.user.UserCardDto;
import org.neoflex.dto.response.user.UserPreviewCardDto;
import org.neoflex.model.Action;
import org.neoflex.model.User;

import java.util.ArrayList;

public class UserMapper {
    public static UserCardDto getUserCard(User user) {
        UserCardDto card = new UserCardDto();
        card.setName(user.getUserInfo().getName());
        card.setEmail(user.getEmail());
        card.setHireDate(user.getUserInfo().getHireDate());
        card.setDepartmentName(user.getUserInfo().getDepartment().getName());
        card.setActionCardsDto(new ArrayList<ActionCardDto>());
        for (var action : user.getUserInfo().getActions()) {
            card.getActionCardsDto().add(ActionMapper.getActionCard(action));
        }
        return card;
    }

    public static UserPreviewCardDto getUserPreviewCard(User user) {
        UserPreviewCardDto card = new UserPreviewCardDto();
        card.setUserId(user.getId());
        card.setEmail(user.getEmail());
        card.setUserName(user.getUserInfo().getName());
        card.setDepartmentName(user.getUserInfo().getDepartment().getName());
        return card;
    }
}
