package App.parserNBKI.updateSevice;

import App.utils.JsonHashMapReader;
import App.model.User;
import App.parserNBKI.parser.SiteParser;
import App.utils.JsonHashMapWriter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Update {

    private final HashMap<Long, String> userData = new HashMap<>();
    private final StringBuilder rate = new StringBuilder();

    private void getUsersRate(User user) throws Exception {
        rate.setLength(0);
        rate.append(SiteParser.getRating(user));

        user.setLastUpdate(LocalDateTime.now());
        userData.put(user.getChatId(), rate.toString());

        JsonHashMapWriter writer = new JsonHashMapWriter();
        writer.writeUserHashMap(user);
    }

    public HashMap<Long, String> checkTImeToUp() throws Exception {
        JsonHashMapReader hashMapUsers = new JsonHashMapReader();
        HashMap<Long, User> users = hashMapUsers.read();

        for (User user : users.values()) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(user.getLastUpdate(), now);
            long diff = Math.abs(duration.toHours());

            if (diff >= user.getRefreshPeriodInHours() && user.isPaid()) {
                getUsersRate(user);
            }
        }
        return userData;
    }
}