package org.neoflex.business;

import jakarta.transaction.Transactional;
import org.neoflex.business.mail.MailSenderService;
import org.neoflex.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final MailSenderService mailSenderService;

    @Autowired
    public NotificationService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @Transactional
    public void sendNotifications(Action action) {
        sendEmailNotification(action);
    }

    @Transactional
    public void sendEmailNotification(Action action) {
        mailSenderService.send(action.getUserInfo().getUser().getEmail(),
                "Action notification",
                String.format("Action %s starts at %s \nComment: %s",
                        action.getType().getName(),
                        action.getDate(),
                        action.getComment()));
    }
}
