package org.neoflex.business;

import jakarta.transaction.Transactional;
import org.neoflex.business.mail.MailSender;
import org.neoflex.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final MailSender mailSender;

    @Autowired
    public NotificationService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Transactional
    public void sendNotifications(Action action) {
        sendEmailNotification(action);
    }

    @Transactional
    public void sendEmailNotification(Action action) {
        mailSender.send(action.getUserInfo().getUser().getEmail(),
                "Action notification",
                String.format("Action %s starts at %s \nComment: %s",
                        action.getType().getName(),
                        action.getDate(),
                        action.getComment()));
    }
}
