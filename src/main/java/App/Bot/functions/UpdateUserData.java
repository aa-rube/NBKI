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

    public void createUser(long chatId, Update update) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.getUsersHashMap();

        User user = new User();
        user.setChatId(chatId);
        user.setUserName(update.getMessage().getFrom().getUserName());
        user.setUserName(update.getMessage().getFrom().getFirstName());
        user.setLoginNBKI(update.getMessage().getText());
        user.setPasswordNBKI("null");
        user.setRefreshPeriodInHours(0);
        user.setLastUpdate(LocalDateTime.now());
        user.setPaid(false);
        user.setAccountSetupComplete(false);

        userMap.put(chatId, user);
        writer.writeToFile(userMap);
    }

    public void createUserLogin(long chatId, String email) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.getUsersHashMap();
        User user = userMap.get(chatId);
        user.setLoginNBKI(email);

        writer.writeUserHashMap(user);
    }

    public void createPassword(long chatId, String password) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.getUsersHashMap();
        User user = userMap.get(chatId);
        user.setPasswordNBKI(password);
        user.setAccountSetupComplete(true);

        writer.writeUserHashMap(user);
    }

    public boolean setPeriodUpdate(long chatId, String messageText) {
        int period = TimeConverter.convertToHours(messageText);

        if (period < 1) {
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

    public void changeUpdateTime(long chatId, int countHours) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.getUsersHashMap();
        User user = userMap.get(chatId);
        user.setRefreshPeriodInHours(countHours);

        writer.writeUserHashMap(user);
    }
}