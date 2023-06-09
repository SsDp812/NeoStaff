package org.neoflex.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.neoflex.business.mapper.ActionTypeMapper;
import org.neoflex.common.exceptions.action_type.ActionTypeNotFound;
import org.neoflex.dto.request.action_type.CreateActionTypeDto;
import org.neoflex.dto.request.action_type.UpdateActionTypeDto;
import org.neoflex.dto.response.action_type.ActionTypeCardDto;
import org.neoflex.model.ActionType;
import org.neoflex.repository.ActionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActionTypeService {
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    public ActionTypeService(ActionTypeRepository actionTypeRepository) {
        this.actionTypeRepository = actionTypeRepository;
    }

    private ActionTypeCardDto createActionType(@Valid CreateActionTypeDto dto){
        ActionType actionType = new ActionType();
        actionType.setName(dto.getActionTypeName());
        actionType.setInterval(dto.getInterval());
        if(dto.getIsNotify() != null){
            actionType.setIsNotify(dto.getIsNotify());
        }else{
            actionType.setIsNotify(false);
        }
        actionType = actionTypeRepository.save(actionType);
        return ActionTypeMapper.getActionTypeCard(actionType);
    }


    private ActionTypeCardDto updateActionType(@Valid UpdateActionTypeDto dto) throws ActionTypeNotFound {
        Optional<ActionType> optionalActionType = actionTypeRepository.findById(dto.getActionId());
        if(optionalActionType.isPresent()){
            ActionType actionType = optionalActionType.get();
            actionType.setName(dto.getName());
            actionType.setInterval(dto.getInterval());
            if(dto.getIsNotify() != null){
                actionType.setIsNotify(dto.getIsNotify());
            }
            actionType = actionTypeRepository.save(actionType);
            return ActionTypeMapper.getActionTypeCard(actionType);
        }else{
            throw new ActionTypeNotFound();
        }
    }

    private ActionTypeCardDto deleteActionType(@Positive Long actionTypeId) throws ActionTypeNotFound {
        Optional<ActionType> optionalActionType = actionTypeRepository.findById(actionTypeId);
        if(optionalActionType.isPresent()){
            ActionType actionType = optionalActionType.get();
            actionTypeRepository.delete(actionType);
            return ActionTypeMapper.getActionTypeCard(actionType);
        }else{
            throw new ActionTypeNotFound();
        }
    }
}
