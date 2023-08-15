package App.parserNBKI.updateSevice;

import App.Bot.Services.MessageSendingService;
import App.utils.JsonHashMapReader;
import App.model.User;
import App.parserNBKI.parser.SiteParser;
import App.utils.JsonHashMapWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

@Component
public class UpdateRating {

    private final MessageSendingService messageSendingService;
    private final StringBuilder rate = new StringBuilder();
    private final LocalDateTime now = LocalDateTime.now();

    @Autowired
    public UpdateRating(MessageSendingService messageSendingService) {
        this.messageSendingService = messageSendingService;
    }


    public void checkTImeToUp() throws Exception {
        JsonHashMapReader hashMapUsers = new JsonHashMapReader();
        HashMap<Long, User> users = hashMapUsers.getUsersHashMap();
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
        messageSendingService.sendMsg(user.getChatId(), getRateString(user));

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