package org.neoflex.business;

import jakarta.transaction.Transactional;
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
import org.neoflex.model.ActionType;
import org.neoflex.model.User;
import org.neoflex.model.UserInfo;
import org.neoflex.repository.ActionRepository;
import org.neoflex.repository.ActionTypeRepository;
import org.neoflex.repository.UserRepository;
import org.neoflex.repository.specification.ActionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActionService {
    private final ActionRepository actionRepository;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    private final ActionTypeRepository actionTypeRepository;



    @Value("${action.daysBeforeNotification}")
    private long daysBeforeNotification;

    @Autowired
    public ActionService(ActionRepository actionRepository, UserRepository userRepository, NotificationService notificationService, ActionTypeRepository actionTypeRepository) {
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.actionTypeRepository = actionTypeRepository;
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
            for (var action : user.getUserInfo().getActions()) {
                actionCardsDto.add(ActionMapper.getActionCard(action));
            }
            return actionCardsDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    public ActionCardDto findActionById(@Positive Long actionId) throws ActionNotFound {
        Optional<Action> optionalAction = actionRepository.findById(actionId);
        if (optionalAction.isPresent()) {
            return ActionMapper.getActionCard(optionalAction.get());
        } else {
            throw new ActionNotFound();
        }
    }

    @Scheduled(cron = "${action.repeat.cron}")
    @Transactional
    public void repeatActions() {
        ZonedDateTime repeatDate = ZonedDateTime.now();
        List<Action> repeatableActions = actionRepository.findAll(ActionSpecification.getAllRepeatables(repeatDate));
        if (repeatableActions.isEmpty())
            return;

        var newActions = repeatableActions.stream()
                .map(this::getRepeatAction)
                .toList();

        actionRepository.saveAll(newActions);
    }

    @Scheduled(cron = "${action.notification.cron}")
    @Transactional
    public void notifyUser() {
        ZonedDateTime actionDate = ZonedDateTime.now().plusDays(daysBeforeNotification);
        List<Action> actions = actionRepository.findAll(ActionSpecification.getAllNeedNotify(actionDate));

        notificationService.sendNotifications(actions);
    }

    @Transactional
    public Action getRepeatAction(Action action) {
        Duration interval = action.getType().getInterval();

        return new Action(
                null,
                action.getUserInfo(),
                action.getType(),
                ZonedDateTime.from(interval.addTo(action.getDate())),
                action.getComment(),
                false);
    }

    @Transactional
    public void createActionsForNewUser(User user){
        UserInfo userInfo = user.getUserInfo();

        List<ActionType> actionTypes = actionTypeRepository.findByIntervalIsNotNull();

        for(ActionType type : actionTypes){
            Action action = new Action();
            action.setUserInfo(userInfo);
            action.setType(type);
            action.setDate(ZonedDateTime.now().plusDays(daysBeforeNotification));

            actionRepository.save(action);
        }


    }

}
