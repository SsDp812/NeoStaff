package org.neoflex.controller;

import org.neoflex.business.ActionTypeService;
import org.neoflex.common.exceptions.action_type.ActionTypeNotFound;
import org.neoflex.dto.request.action_type.CreateActionTypeDto;
import org.neoflex.dto.request.action_type.UpdateActionTypeDto;
import org.neoflex.dto.request.user.UpdateActionTimeToUserDto;
import org.neoflex.dto.response.action_type.ActionTypeCardDto;
import org.neoflex.model.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action_types")
public class ActionTypeController {
    private ActionTypeService actionTypeService;
    @Autowired
    public ActionTypeController(ActionTypeService actionTypeService) {
        this.actionTypeService = actionTypeService;
    }

    @PostMapping("/create")
    public ActionTypeCardDto createActionType(@RequestBody CreateActionTypeDto dto){
        return  actionTypeService.createActionType(dto);
    }

    @PostMapping("/update")
    public ActionTypeCardDto updateActionType(@RequestBody UpdateActionTypeDto dto) throws ActionTypeNotFound {
        return actionTypeService.updateActionType(dto);
    }

    @PostMapping("/delete")
    public ActionTypeCardDto deleteActionType(@RequestBody Long actionId) throws ActionTypeNotFound {
        return actionTypeService.deleteActionType(actionId);
    }
}
