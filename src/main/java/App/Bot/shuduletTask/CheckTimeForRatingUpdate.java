package App.Bot.shuduletTask;

import App.parserNBKI.updateSevice.UpdateRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CheckTimeForRatingUpdate {

    private final UpdateRating rate;
    @Scheduled(fixedRateString = "${bot.period}")
    public void refreshRate() throws Exception {
        rate.checkTImeToUp();
    }
    @Autowired
    public CheckTimeForRatingUpdate(UpdateRating rate) {
        this.rate = rate;
    }
}
