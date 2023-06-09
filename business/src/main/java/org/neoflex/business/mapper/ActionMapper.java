package org.neoflex.business.mapper;

import org.neoflex.dto.response.action.ActionCardDto;
import org.neoflex.model.Action;

public class ActionMapper {
    public static ActionCardDto getActionCard(Action action){
        ActionCardDto card = new ActionCardDto();
        card.setActionTypeName(action.getType().getName());
        card.setActionDate(action.getDate());
        card.setActionComment(action.getComment());
        return card;
        }
    }
}
