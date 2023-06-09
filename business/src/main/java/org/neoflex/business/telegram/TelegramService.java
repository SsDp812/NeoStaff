package org.neoflex.business.telegram;

import jakarta.validation.constraints.Email;
import org.neoflex.business.UserService;
import org.neoflex.business.config.TelegramConfig;
import org.neoflex.common.exceptions.telegram.UnexpectedMessageException;
import org.neoflex.model.User;
import org.neoflex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;
import java.util.Optional;

@Component
public class TelegramService extends TelegramLongPollingBot {
    @Autowired
    private TelegramConfig telegramConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();
            switch (messageText){
                case "/start":
                    startBot(chatId,memberName);
                    break;
                default:
                    checkAndAddEmail(chatId,messageText);
            }


        }
    }


    private void startBot(Long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello, " + userName + "! Give me your email: ");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAndAddEmail(Long chatId, String msg){
        Optional<User> optionalUser = userRepository.findByEmail(msg);
        System.out.println(msg);
        SendMessage message = new SendMessage();
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setChatId(chatId);
            userRepository.save(user);
            message.setChatId(chatId);
            message.setText("Thanks, we will notify you about new actions!");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }else{
            message.setChatId(chatId);
            message.setText("Not correct email!");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void send(User user,String messageText) {
       if(user.getChatId() != null){
           SendMessage message = new SendMessage();
           message.setChatId(user.getChatId());
           message.setText(messageText);
           try {
               execute(message);
           } catch (TelegramApiException e) {
               throw new RuntimeException(e);
           }
       }
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getToken();
    }
}
