package org.neoflex.business;

import org.neoflex.dto.request.user.AddActionToUserDto;
import org.neoflex.dto.request.user.DeleteActionToUserDto;
import org.neoflex.dto.request.user.UpdateActionTimeToUserDto;
import org.neoflex.dto.response.action.ActionCardDto;
import org.neoflex.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {
    private ActionRepository actionRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public ActionCardDto addActionToUser(AddActionToUserDto dto){
        return null;
    }

    public ActionCardDto updateUserTimeAction(UpdateActionTimeToUserDto dto){
        return null;
    }

    public ActionCardDto deleteActionFromUser(DeleteActionToUserDto dto){
        return null;
    }

    public List<ActionCardDto> findActionsByUserId(Long userId){
        return null;
    }

    public ActionCardDto findActionById(Long actionId){
        return null;
    }
}
