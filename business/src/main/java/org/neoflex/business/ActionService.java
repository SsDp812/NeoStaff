package org.neoflex.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.neoflex.business.mapper.ActionMapper;
import org.neoflex.common.exceptions.action.ActionNotFound;
import org.neoflex.common.exceptions.user.UserNotFoundException;
import org.neoflex.dto.request.user.AddActionToUserDto;
import org.neoflex.dto.request.user.DeleteActionToUserDto;
import org.neoflex.dto.request.user.UpdateActionTimeToUserDto;
import org.neoflex.dto.response.action.ActionCardDto;
import org.neoflex.model.Action;
import org.neoflex.model.User;
import org.neoflex.repository.ActionRepository;
import org.neoflex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActionService {
    private ActionRepository actionRepository;
    private UserRepository userRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public ActionCardDto addActionToUser(@Valid AddActionToUserDto dto) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Action action = new Action();
            action.setType(dto.getActionType());
            action.setDate(dto.getActionDate());
            action.setUserInfo(user.getUserInfo());
            action.setIsDeleted(false);
            action.setComment(null);
            user.getUserInfo().getActions().add(action);
            actionRepository.save(action);
            userRepository.save(user);
            return ActionMapper.getActionCard(action);

        } else {
            throw new UserNotFoundException();
        }
    }

    public ActionCardDto updateUserTimeAction(@Valid UpdateActionTimeToUserDto dto) throws ActionNotFound, UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Action> optionalAction = actionRepository.findById(dto.getActionId());
            if (optionalAction.isPresent()) {
                Action action = optionalAction.get();
                action.setDate(dto.getActionTime());
                action = actionRepository.save(action);
                return ActionMapper.getActionCard(action);
            } else {
                throw new ActionNotFound();
            }

        } else {
            throw new UserNotFoundException();
        }
    }

    public ActionCardDto deleteActionFromUser(@Valid DeleteActionToUserDto dto) throws ActionNotFound, UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Action> optionalAction = actionRepository.findById(dto.getActionId());
            if (optionalAction.isPresent()) {
                Action action = optionalAction.get();
                action.setIsDeleted(true);
                action = actionRepository.save(action);
                return ActionMapper.getActionCard(action);
            } else {
                throw new ActionNotFound();
            }

        } else {
            throw new UserNotFoundException();
        }
    }

    public List<ActionCardDto> findActionsByUserId(@Positive Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<ActionCardDto> actionCardsDto = new ArrayList<ActionCardDto>();
            for(var action : user.getUserInfo().getActions()){
                actionCardsDto.add(ActionMapper.getActionCard(action));
            }
            return actionCardsDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    public ActionCardDto findActionById(@Positive Long actionId) throws ActionNotFound {
        Optional<Action> optionalAction = actionRepository.findById(actionId);
        if(optionalAction.isPresent()){
            return ActionMapper.getActionCard(optionalAction.get());
        }else{
            throw new ActionNotFound();
        }
    }
}
