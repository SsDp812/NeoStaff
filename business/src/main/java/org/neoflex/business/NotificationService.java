package org.neoflex.business;

import jakarta.transaction.Transactional;
import org.neoflex.business.mail.MailSenderService;

import org.neoflex.business.telegram.TelegramService;


import org.neoflex.model.Action;
import org.neoflex.model.User;
import org.neoflex.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class NotificationService {

    private final MailSenderService mailSenderService;


    private final TelegramService telegramService;

    @Autowired
    public NotificationService(MailSenderService mailSenderService, TelegramService telegramService) {
        this.mailSenderService = mailSenderService;
        this.telegramService = telegramService;
    }

    @Transactional
    public void sendNotifications(List<Action> actions) {
        HashMap<UserInfo, List<Action>> userActions = new HashMap<>();
        actions.forEach(action -> {
            if (!userActions.containsKey(action.getUserInfo()))
                userActions.put(action.getUserInfo(), new ArrayList<>());

            userActions.get(action.getUserInfo()).add(action);
        });

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z", Locale.getDefault());

        userActions.keySet().forEach(userInfo -> {
            sendEmailNotifications(userInfo, userActions.get(userInfo), formatter);
        });

        actions.forEach(this::sendActionToTelegram);
    }

    @Transactional
    public void sendEmailNotifications(UserInfo userInfo, List<Action> actions, DateTimeFormatter dateTimeFormatter) {
        String delimiter ="\n" +
                "=====================================================================================" + "\n";
        StringBuilder message = new StringBuilder();
        actions.forEach(action -> {
            message.append("Активность \"").append(action.getType().getName()).append("\"\n")
                    .append("Начинается в ").append(action.getDate().format(dateTimeFormatter)).append("\n")
                    .append(action.getComment()).append(delimiter);
        });

        try {
            mailSenderService.send(userInfo.getUser().getEmail(),
                    "Action notifications", message.toString());
        }
        catch (RuntimeException e) {
            System.out.println("[EMAIL] Сообщение не было доставлено: " + userInfo.getUser().getEmail());
        }
    }
    @Transactional
    public void sendActionToTelegram(Action action){
        telegramService.send(
                action.getUserInfo().getUser(),
                String.format("Action %s starts at %s \nComment: %s",
                        action.getType().getName(),
                        action.getDate(),
                        action.getComment())
        );

    }

    public void sendEmailPassword(User user) {
        mailSenderService.send(user.getEmail(), "New Password for NeoStaff",
                String.format("New password for NeoStaff: %s", user.getPassword()));
    }
}
