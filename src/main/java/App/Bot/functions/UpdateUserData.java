package App.Bot.functions;

import App.utils.JsonHashMapReader;
import App.utils.JsonHashMapWriter;
import App.model.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
public class UpdateUserData {
    private final JsonHashMapWriter writer = new JsonHashMapWriter();
    public void createUserLogin(long chatId, Update update) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.getUsersHashMap();
        User user = new User();

        user.setPasswordNBKI("p");
        user.setChatId(update.getMessage().getChatId());
        user.setName(update.getMessage().getFrom().getFirstName());
        user.setUserName(update.getMessage().getFrom().getUserName());
        user.setLoginNBKI(update.getMessage().getText());

        user.setPaid(true);
        user.setLastUpdate(LocalDateTime.now());
        userMap.put(chatId, user);
        user.setRefreshPeriodInHours(24);

        writer.writeToFile(userMap);
    }

    public void createPassword(long chatId, String password) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.getUsersHashMap();
        User user = userMap.get(chatId);
        user.setPasswordNBKI(password);
        writer.writeUserHashMap(user);
    }

    public boolean setPeriodUpdate(long chatId, String messageText) {
        int period = TimeConverter.convertToHours(messageText);

        if(period < 1) {
            return false;
        } else {
            JsonHashMapReader reader = new JsonHashMapReader();
            HashMap<Long, User> userMap = reader.getUsersHashMap();
            User user = userMap.get(chatId);
            user.setRefreshPeriodInHours(period);

            writer.writeUserHashMap(user);
            return true;
        }
    }

}