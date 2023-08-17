package App.Bot.Services;

import App.model.User;
import App.parserNBKI.updateSevice.UpdateRating;
import App.utils.JsonHashMapReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ButtonsClicksReplyService {
    private final MessageSendingService messageSendingService;
    private final UpdateRating updateRating;
    private final KeyboardService keyboardService = new KeyboardService();

    @Autowired
    public ButtonsClicksReplyService(UpdateRating updateRating, MessageSendingService messageSendingService) {
        this.updateRating = updateRating;
        this.messageSendingService = messageSendingService;
    }

    public void returnRatingRightNow(Long chatId) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userHashMap = reader.getUsersHashMap();
        String rate = "Что-то пошло не так";

        if (!userHashMap.get(chatId).isAccountSetupComplete()) {
            messageSendingService.sendMsg(chatId, "Вы еще не внесли данные.");
            return;
        }

        messageSendingService.sendMsg(chatId, "Данные формируются. Время ожидания около 30 секунд");

        try {
            rate = updateRating.getRateString(userHashMap.get(chatId));
            messageSendingService.sendMsg(chatId, rate, keyboardService.getPermanentKeyboard());

        } catch (Exception e) {
            messageSendingService.sendMsg(chatId, rate, keyboardService.getPermanentKeyboard());
            throw new RuntimeException(e);
        }
    }

    public void returnTimeList(long chatId) {
        String answer = "Выберите один из временных периодов, представленных ниже:";
        messageSendingService.sendMsg(chatId, answer, keyboardService.getTimePeriods());
    }
}
