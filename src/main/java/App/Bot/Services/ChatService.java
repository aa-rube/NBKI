package App.Bot.Services;

import App.model.User;
import App.Bot.functions.UpdateUserData;
import App.parserNBKI.updateSevice.UpdateRating;
import App.utils.Buffer;
import App.utils.JsonHashMapReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ChatService {
    private final UpdateUserData updateUserData;
    private final MessageSendingService messageSendingService;
    private final UpdateRating updateRating;
    private final KeyboardService keyboardService = new KeyboardService();

    private final Buffer buffer = new Buffer();

    @Autowired
    public ChatService(UpdateUserData updateUserData, MessageSendingService messageSendingService, UpdateRating updateRating) {
        this.updateUserData = updateUserData;
        this.messageSendingService = messageSendingService;
        this.updateRating = updateRating;
    }

    public void start(Long chatId, String name) {
        JsonHashMapReader reader = new JsonHashMapReader();
        buffer.add(chatId);

        if (reader.getUsersHashMap().get(chatId) != null ) {
            String answer = name + ", Ваши данные уже сохранены.";
            messageSendingService.sendMsg(chatId, answer,keyboardService.getPermanentKeyboard());
        } else {

            String answer = "Добро пожаловать в чат-бот НБКИ!\n" +
                    name + ", этот бот создан, чтобы помочь Вам следить за вашим официальным " +
                    "рейтингом в Национальном Бюро Кредитной Истории. " +
                    "Для начала работы, пожалуйста, введите вашу электронную " +
                    "почту, которая привязана к вашему аккаунту в НБКИ.";
            messageSendingService.sendMsg(chatId, answer);
        }
    }

    public void createLogin(long chatId, org.telegram.telegrambots.meta.api.objects.Update update) {
        if(!buffer.isExist(chatId)) {
            wrongFormat(chatId);
            return;
        }
        updateUserData.createUserLogin(chatId, update);
        messageSendingService.sendMsg(chatId, "Спасибо за информацию. Введите теперь Ваш пароль:");
    }

    public void createPassword(long chatId, String messageText) {
        if(!buffer.isExist(chatId)) {
            wrongFormat(chatId);
        } else {
            buffer.delete(chatId);
        }

        updateUserData.createPassword(chatId, messageText);
        messageSendingService.sendMsg(chatId, "Отлично! Все ваши данные успешно сохранены. Мы храним их в зашифрованном виде, " +
                "так что даже мы не можем их прочитать.\n" +
                "Теперь, пожалуйста, выберите, как часто вы хотели бы проверять рейтинг.\n" +
                "Введите период в формате: \"23 часа\", \"6 дней\" или \"3 недели\". " +
                "Вы можете выбрать любой период до 4 недель.");

    }

    public void createPeriodUpdate(long chatId, String messageText) {
        if (!updateUserData.setPeriodUpdate(chatId, messageText)) {
            String text = "Извините, я не смог распознать введенный вами временной период. " +
                    "Пожалуйста, убедитесь, что вы вводите период в правильном формате. Попробуйте еще раз!";

            String text2 = "Введите период в формате: \"23 часа\", \"6 дней\" или \"3 недели\". " +
                    "Вы можете выбрать любой период до 4 недель.";
            messageSendingService.sendMsg(chatId, text);
            messageSendingService.sendMsg(chatId, text2);

        } else {
            JsonHashMapReader reader = new JsonHashMapReader();
            messageSendingService.sendMsg(chatId, "Вы установили период обновления " +
                    reader.getUsersHashMap().get(chatId).getRefreshPeriodInHours() +
                    "час/а/ов) \n" + "Настройка успешно завершена. Прздарвляем!" +
                    "Теперь вы можете в полной мере пользоваться всеми возможностями нашего сервиса. " +
                    "Желаем удачи и приятного использования!", keyboardService.getPermanentKeyboard());
        }
    }

    public void wrongFormat(long chatId) {
        messageSendingService.sendMsg(chatId, "Не верный формат. Попробуйте пожалуйста еще раз.");
    }

    public void sendRatingRightNow(Long chatId) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userHashMap = reader.getUsersHashMap();

        if(userHashMap.get(chatId) == null) {
            messageSendingService.sendMsg(chatId, "Вы еще не внесли данные.");
        }
        messageSendingService.sendMsg(chatId, "Данные формируются. Время ожидания около 30 секунд");
        String rate = "Что-то пошло не так";
        try {
            rate = updateRating.getRateString(userHashMap.get(chatId));
        } catch (Exception e) {
            messageSendingService.sendMsg(chatId, rate);
            throw new RuntimeException(e);
        }
        messageSendingService.sendMsg(chatId, rate);
    }

//    public void sendMsgToUser(long chatId) {
//        messageSendingService.sendMsg(chatId,"");
//    }

}
