package org.neoflex.controller;

import jakarta.validation.constraints.Positive;
import org.neoflex.business.ActionService;
import org.neoflex.common.exceptions.action.ActionNotFound;
import org.neoflex.common.exceptions.user.UserNotFoundException;
import org.neoflex.dto.request.action_type.ActionTypeFilterDto;
import org.neoflex.dto.request.user.AddActionToUserDto;
import org.neoflex.dto.request.user.DeleteActionToUserDto;
import org.neoflex.dto.request.user.UpdateActionTimeToUserDto;
import org.neoflex.dto.response.action.ActionCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class ActionController {
    private ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("")
    public List<ActionCardDto> getActions(@RequestParam Long userId) throws UserNotFoundException {
        return actionService.findActionsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ActionCardDto getActionById(@RequestParam Long actionId) throws ActionNotFound {
        return actionService.findActionById(actionId);
    }

    @PostMapping("/add")
    public ActionCardDto addActionToUser(@RequestBody AddActionToUserDto dto) throws UserNotFoundException {
        return actionService.addActionToUser(dto);
    }

    @PostMapping("/update")
    public ActionCardDto updateActionFromUser(@RequestBody UpdateActionTimeToUserDto dto) throws UserNotFoundException, ActionNotFound {
        return actionService.updateUserTimeAction(dto);
    }

    @PostMapping("/delete")
    public ActionCardDto deleteActionFromUser(@RequestBody DeleteActionToUserDto dto) throws UserNotFoundException, ActionNotFound {
        return actionService.deleteActionFromUser(dto);
    }


    @PostMapping("/filter")
    public List<ActionCardDto> getActionByFilter(@RequestBody ActionTypeFilterDto dto){
        return null;
    }
}
