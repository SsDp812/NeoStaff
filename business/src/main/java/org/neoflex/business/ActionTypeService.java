package org.neoflex.business;

import org.neoflex.dto.request.action_type.CreateActionTypeDto;
import org.neoflex.dto.request.action_type.UpdateActionTypeDto;
import org.neoflex.dto.response.action_type.ActionTypeCardDto;
import org.neoflex.repository.ActionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionTypeService {
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    public ActionTypeService(ActionTypeRepository actionTypeRepository) {
        this.actionTypeRepository = actionTypeRepository;
    }

    private ActionTypeCardDto createActionType(CreateActionTypeDto dto){
        return null;
    }

    private ActionTypeCardDto updateActionType(UpdateActionTypeDto dto){
        return null;
    }

    private ActionTypeCardDto deleteActionType(Long actionTypeId){
        return null;
    }
}
