package App.Bot.Services;

import App.Bot.functions.UpdateUserData;
import App.utils.JsonHashMapReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class CreateUserService {
    private final UpdateUserData updateUserData;
    private final MessageSendingService messageSendingService;
    private final KeyboardService keyboardService = new KeyboardService();

    @Autowired
    public CreateUserService(UpdateUserData updateUserData, MessageSendingService messageSendingService) {
        this.updateUserData = updateUserData;
        this.messageSendingService = messageSendingService;
    }

    public void start(Long chatId, String name,Update update) {
        JsonHashMapReader reader = new JsonHashMapReader();

        if (reader.getUsersHashMap().get(chatId) == null) {
            updateUserData.createUser(chatId, update);
            String answer = "Добро пожаловать в чат-бот НБКИ!\n" +
                    name + ", этот бот создан, чтобы помочь Вам следить за вашим официальным " +
                    "рейтингом в Национальном Бюро Кредитной Истории. " +
                    "Для начала работы, пожалуйста, введите вашу электронную " +
                    "почту, которая привязана к вашему аккаунту в НБКИ.";
            messageSendingService.sendMsg(chatId, answer);

        } else {
            String answer = name + ", Ваши данные уже сохранены.";
            messageSendingService.sendMsg(chatId, answer, keyboardService.getPermanentKeyboard());
        }
    }

    public void createLogin(long chatId, String email) {
        JsonHashMapReader reader = new JsonHashMapReader();
        if (reader.getUsersHashMap().get(chatId).isAccountSetupComplete()) {
            wrongFormat(chatId);
        } else {
            updateUserData.createUserLogin(chatId, email);
            messageSendingService.sendMsg(chatId, "Спасибо за информацию. Введите теперь Ваш пароль:");
        }
    }

    public void createPassword(long chatId, String messageText) {
        JsonHashMapReader reader = new JsonHashMapReader();
        if (reader.getUsersHashMap().get(chatId).isAccountSetupComplete()) {
            wrongFormat(chatId);
        } else {
            updateUserData.createPassword(chatId, messageText);
            messageSendingService.sendMsg(chatId, """
                    Отлично! Все ваши данные успешно сохранены. 
                    Мы храним их в зашифрованном виде, так что даже мы не можем их прочитать.
                    Теперь, пожалуйста, выберите, как часто вы хотели бы проверять рейтинг.
                    Введите период в формате: "23 часа", "6 дней" или "3 недели". Вы можете выбрать любой период.""");
        }

    }

    public void createPeriodUpdate(long chatId, String messageText) {
        if (!updateUserData.setPeriodUpdate(chatId, messageText)) {
            String text = "Извините, я не смог распознать введенный вами временной период. " +
                    "Пожалуйста, убедитесь, что вы вводите период в правильном формате. Попробуйте еще раз!";

            String text2 = "Введите период в формате: \"23 часа\", \"6 дней\" или \"3 недели\". " +
                    "Вы можете ввести любой период.";
            messageSendingService.sendMsg(chatId, text);
            messageSendingService.sendMsg(chatId, text2);

        } else {
            JsonHashMapReader reader = new JsonHashMapReader();
            int hours =  reader.getUsersHashMap().get(chatId).getRefreshPeriodInHours();
            messageSendingService.sendMsg(chatId, "Вы установили период обновления " + hours + "час/а/ов)!");
            messageSendingService.sendMsg(chatId,"Настройка успешно завершена. Прздарвляем!\n" +
                    "Теперь вы можете в полной мере пользоваться всеми возможностями нашего сервиса." +
                    "Желаем удачи и приятного использования!", keyboardService.getPermanentKeyboard());
        }
    }

    public void wrongFormat(long chatId) {
        messageSendingService.sendMsg(chatId, "Не верный формат. Попробуйте пожалуйста еще раз.");
    }
}
