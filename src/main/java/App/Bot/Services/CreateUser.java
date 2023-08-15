package App.Bot.Services;

import App.utils.JsonHashMapReader;
import App.utils.JsonHashMapWriter;
import App.model.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CreateUser {
    private final JsonHashMapReader reader = new JsonHashMapReader();
    private final JsonHashMapWriter writer = new JsonHashMapWriter();
    public void createUserLogin(long chatId, Update update) {
        HashMap<Long, User> userMap = reader.read();
        User user;

        if (!userMap.containsKey(chatId)) {
            user = new User();
        } else {
           return;
        }
        user.setPasswordNBKI("0O0");
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
        HashMap<Long, User> userMap = reader.read();
        User user = userMap.get(chatId);
        user.setPasswordNBKI(password);
        writer.writeUserHashMap(user);
    }

    public boolean setPeriodUpdate(long chatId, String messageText) {
        HashMap<Long, User> userMap = reader.read();
        User user = userMap.get(chatId);
        int period = TimeConverter.convertToHours(messageText);

        if(period < 0) {
            return false;
        } else {
            user.setRefreshPeriodInHours(period);
            writer.writeUserHashMap(user);
            return true;
        }
    }

    public boolean isUserExist(long chatId) {
        HashMap<Long, User> users = reader.read();
        return  users.containsKey(chatId);
    }
}