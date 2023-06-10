package org.neoflex.business;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.neoflex.model.User;
import org.neoflex.model.UserInfo;
import org.neoflex.repository.UserInfoRepository;
import org.neoflex.repository.UserRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@EnableScheduling
public class JiraIntegration {

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final UserInfoRepository userInfoRepository;
    private final ActionService actionService;

    public JiraIntegration(UserRepository userRepository, NotificationService notificationService, UserInfoRepository userInfoRepository, ActionService actionService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.userInfoRepository = userInfoRepository;
        this.actionService = actionService;
    }


    @Scheduled(cron = "0 * * * * ?")
    private void getAllJiraUsers() throws UnirestException {

        HttpResponse<JsonNode> response = Unirest.get("https://babim.atlassian.net/rest/api/3/users/search")
                .basicAuth("qwwe798@gmail.com", "ATATT3xFfGF0IB-1lnbEtbNqyqkvavqaeGdOfCDWY0DKo5wQwTT0XYW_cXM-RbhKLyx0a67WmySzkyMN_atJU_eZQhPNO5jz2UqKp1qJLKLzA0e-9amfjcVC9dZtsMUfo7jE0vhXDy-YeW5SEMiOhSUYzSrOu7zfT8KC1C5TSLuT1K2WzMpznac=F2FA5AC6")
                .header("Accept", "application/json")
                .asJson();

        for (Object itVar : response.getBody().getArray()) {
            JSONObject jo = (JSONObject) itVar;

            if(!((String) jo.get("accountType")).equals("atlassian") || !((Boolean) jo.get("active"))){
                continue;
            }

            if(userRepository.existsByJiraAccountId((String) jo.get("accountId"))){
                continue;
            }

            User user = new User();
            user.setJiraAccountId((String) jo.get("accountId"));
            user.setEmail((String) jo.get("emailAddress"));

            UserInfo userInfo = new UserInfo();
            userInfo.setUser(user);
            userInfo.setName((String) jo.get("displayName"));
            user.setUserInfo(userInfo);
            user.setPassword(genPassword());

            userInfoRepository.save(userInfo);

            actionService.createActionsForNewUser(user);


            notificationService.sendEmailPassword(user);

        }

    }

    private static String genPassword() {
        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return password;
    }


}


