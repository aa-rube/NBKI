package refresh;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {

    public List<User> getUsersList() {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<>();
        try {
            JsonNode rootNode = mapper.readTree(new File("src/arube/main/resources/users_in.json"));
            JsonNode userArray = rootNode.get("users");

            for (JsonNode userNode : userArray) {
                String telegramId = userNode.get("telegramId").asText();
                String userName = userNode.get("userName").asText();
                String name = userNode.get("name").asText();
                String loginNBKI = userNode.get("loginNBKI").asText();
                String passwordNBKI = userNode.get("passwordNBKI").asText();
                int refreshPeriodInHours = userNode.get("refreshPeriodInHours").asInt();
                LocalDateTime lastUpdate = LocalDateTime.parse(userNode.get("lastUpdate").asText(),
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                boolean paid = userNode.get("paid").asBoolean();
                User user = new User(telegramId,userName,name,loginNBKI,passwordNBKI,refreshPeriodInHours,lastUpdate,paid);

                users.add(user);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
