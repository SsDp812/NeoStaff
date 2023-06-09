package org.neoflex.business;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.neoflex.model.User;
import org.neoflex.model.UserInfo;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class JiraIntegration {


    public static void main(String[] args) throws UnirestException {
        JiraIntegration.getAllJiraUsers();
    }

    private static void getAllJiraUsers() throws UnirestException {


        HttpResponse<JsonNode> response = Unirest.get("https://babim.atlassian.net/rest/api/3/users/search")
                .basicAuth("qwwe798@gmail.com", "ATATT3xFfGF0e20EWJuEdvsMOFG9qsHCdSnzgttsguqotvlK_ZBvUwGLr9ZrlfdIf1AR3RroNUlUhF7FakWra09XfPanbIKRC4qopBUFIwS5UCWsaorXmZQdDqQzkbJ7LoFBfn9f7Kj40GmR7tE0oFLU2zywubU_vhingm39_JpDWdAQU-rg8Rc=19398EF3")
                .header("Accept", "application/json")
                .asJson();

        for (Object itVar : response.getBody().getArray()) {


            System.out.println(itVar);
            JSONObject jo = (JSONObject) itVar;

            System.out.println(jo);

            if(!((String) jo.get("accountType")).equals("atlassian") || !((Boolean) jo.get("active"))){
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





        }

    }

    private static String genPassword() {
        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return password;
    }


}


