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

    public JiraIntegration(UserRepository userRepository, NotificationService notificationService, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.userInfoRepository = userInfoRepository;
    }


    @Scheduled(cron = "0 * * * * ?")
    private void getAllJiraUsers() throws UnirestException {

        System.out.println(123);
        HttpResponse<JsonNode> response = Unirest.get("https://babim.atlassian.net/rest/api/3/users/search")
                .basicAuth("qwwe798@gmail.com", "ATATT3xFfGF0-BUsUpuAzBpiTZBSAcKTM3P2zWDAncHoIy8dcf_j2mjJ9Xl8HHz1EFCuGdLZ4SjaDwlcAUE4tiwExeTqpQX0h9Vp87Llh17YYRiavbOT3MET2en2AY48ALX4IF7L422u8y6Vwz-45I1akR1dSKDctLyvTkvXQ29H8tKroxNovWA=3D734516")
                .header("Accept", "application/json")
                .asJson();

        for (Object itVar : response.getBody().getArray()) {
            System.out.println(itVar);
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

            System.out.println(userInfo);

            user.setPassword(genPassword());

            //проверить, что все нормально тянуллось

            //userRepository.save(user);
            userInfoRepository.save(userInfo);

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


