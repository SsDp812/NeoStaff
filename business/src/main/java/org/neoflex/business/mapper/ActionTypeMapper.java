package org.neoflex.business.mapper;

import org.neoflex.dto.response.action.ActionCardDto;
import org.neoflex.dto.response.action_type.ActionTypeCardDto;
import org.neoflex.model.Action;
import org.neoflex.model.ActionType;

public class ActionTypeMapper {

    public static ActionTypeCardDto getActionTypeCard(ActionType actionType) {
        ActionTypeCardDto card = new ActionTypeCardDto();
        card.setActionId(actionType.getId());
        card.setActionName(actionType.getName());
        card.setInterval(actionType.getInterval());
        card.setIsNotify(actionType.getIsNotify());
        return card;
    }

}
