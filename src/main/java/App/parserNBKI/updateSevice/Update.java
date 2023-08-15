package App.parserNBKI.updateSevice;

import App.Bot.Services.MessageSender;
import App.utils.JsonHashMapReader;
import App.model.User;
import App.parserNBKI.parser.SiteParser;
import App.utils.JsonHashMapWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Update {

    private final MessageSender messageSender;
    private final StringBuilder rate = new StringBuilder();
    private final LocalDateTime now = LocalDateTime.now();

    public Update(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void checkTImeToUp() throws Exception {
        JsonHashMapReader hashMapUsers = new JsonHashMapReader();
        HashMap<Long, User> users = hashMapUsers.read();
        LocalDateTime now = LocalDateTime.now();

        for (User user : users.values()) {
            Duration duration = Duration.between(user.getLastUpdate(), now);
            long diff = Math.abs(duration.toHours());

            if (diff >= user.getRefreshPeriodInHours() && user.isPaid()) {
                getScheduleUsersRate(user);
            }
        }
    }

    private void getScheduleUsersRate(User user) throws Exception {
        messageSender.sendRateMessage(user.getChatId(), getRateString(user));
        JsonHashMapWriter writer = new JsonHashMapWriter();
        writer.writeUserHashMap(user);
    }

    public String getRateString(User user) throws Exception {
        rate.setLength(0);
        rate.append(SiteParser.getRating(user));
        user.setLastUpdate(now);
        return rate.toString();
    }

}