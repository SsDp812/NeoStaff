package org.neoflex.business;

import jakarta.transaction.Transactional;
import org.neoflex.model.Action;
import org.neoflex.repository.ActionRepository;
import org.neoflex.repository.specification.ActionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ActionRepositoryService {
    private final ActionRepository repository;

    private final NotificationService notificationService;

    @Value("${action.minutesBeforeNotification}")
    private long minutesBeforeNotification;

    @Autowired
    public ActionRepositoryService(ActionRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }


    @Scheduled(cron = "${action.repeat.cron}")
    @Transactional
    public void repeatActions() {
        List<Action> repeatableActions = repository.findAll(ActionSpecification.getAllRepeatables());
        var newActions = repeatableActions.stream()
                .map(this::getRepeatAction)
                .toList();
        repository.saveAll(newActions);
    }

    @Scheduled(cron = "${action.notification.cron}")
    @Transactional
    public void notifyUser() {
        ZonedDateTime actionDate = ZonedDateTime.now().plusMinutes(minutesBeforeNotification);
        List<Action> actions = repository.findAll(ActionSpecification.getAllNeedNotify(actionDate));

        actions.forEach(notificationService::sendNotifications);
    }

    @Transactional
    public Action getRepeatAction(Action action) {
        Period interval = action.getType().getInterval();
        return new Action(
                null,
                action.getUserInfo(),
                action.getType(),
                ZonedDateTime.from(interval.addTo(action.getDate())),
                action.getComment(),
                false);
    }
}
