package App.Bot.TelegramBot;

import App.Bot.Services.CreateUser;
import App.Bot.Services.EmailValidator;
import App.Bot.Services.TimeConverter;
import App.Bot.config.BotConfig;
import App.parserNBKI.updateSevice.Update;
import App.utils.JsonHashMapReader;
import App.model.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

@Component
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> userMap = reader.read();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String userName = update.getMessage().getFrom().getFirstName();
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                start(chatId, userName);
            }

            if (messageText.contains("@") && EmailValidator.isValid(messageText)) {
                createLogin(chatId, update);
            }

            if (userMap.get(chatId).getPasswordNBKI().equals("0")) {
                createPassword(chatId, messageText);
            }

            if (TimeConverter.isTimePeriod(messageText)) {
                createPeriodUpdate(chatId, messageText);
            } else {
                String str = "Не верный формат. Повторите пожалуйста еще раз.";
                sendMessage(chatId, str);
            }
        }
    }

    private void start(Long chatId, String name) {
        CreateUser createUser = new CreateUser();
        if (createUser.isUserExist(chatId)) {
            String answer = "Аккаунт существует.";
            sendMessage(chatId, answer);
        }

        String answer = "Добро пожаловать в чат-бот НБКИ!\n" +
                name + ", этот бот создан, чтобы помочь Вам следить за вашим официальным " +
                "рейтингом в Национальном Бюро Кредитной Истории." +
                "Для начала работы, пожалуйста, введите вашу электронную " +
                "почту, которая привязана к вашему аккаунту в НБКИ.";

        sendMessage(chatId, answer);
    }

    private void createLogin(long chatId, org.telegram.telegrambots.meta.api.objects.Update update) {
        CreateUser createUser = new CreateUser();
        createUser.createUserLogin(chatId, update);
        sendMessage(chatId, "Спасибо за информацию. Введите теперь Ваш пароль:");
    }

    private void createPassword(long chatId, String messageText) {
        CreateUser createUser = new CreateUser();
        createUser.createPassword(chatId, messageText);

        sendMessage(chatId, "Отлично! Все ваши данные успешно сохранены. Мы храним их в зашифрованном виде, " +
                "так что даже мы не можем их прочитать.\n" +
                "Теперь, пожалуйста, выберите, как часто вы хотели бы проверять рейтинг.\n" +
                "Введите период в формате: \"23 часа\", \"6 дней\" или \"3 недели\". " +
                "Вы можете выбрать любой период до 4 недель.");
    }

    private void createPeriodUpdate(long chatId, String messageText) {
        CreateUser createUser = new CreateUser();
        if (!createUser.setPeriodUpdate(chatId, messageText)) {
            String text = "Извините, я не смог распознать введенный вами временной период. " +
                    "Пожалуйста, убедитесь, что вы вводите период в правильном формате. Попробуйте еще раз!";

            String text2 = "Введите период в формате: \"23 часа\", \"6 дней\" или \"3 недели\". " +
                    "Вы можете выбрать любой период до 4 недель.";
            sendMessage(chatId, text);
            sendMessage(chatId, text2);

        } else {

            HashMap<Long, User> userMap = new JsonHashMapReader().read();
            sendMessage(chatId, "Поздравляем! " + "Вы установили период обновления " + userMap.get(chatId).getRefreshPeriodInHours() +
                    "часа(ов). \n" + "Регистрация успешно завершена. " +
                    "Теперь вы можете в полной мере пользоваться всеми возможностями нашего сервиса. " +
                    "Желаем удачи и приятного использования!");
        }
    }

    @Scheduled(fixedRate = 60_000)
    public void refreshRate() throws Exception {
        Update rate = new Update();
        HashMap<Long, String> userData = rate.checkTImeToUp();

        if(userData.size() > 0) {
            for (Long key : userData.keySet()) {
                sendMessage(key, userData.get(key));
            }

        } else {
            System.out.println("Nothing to send");
        }
    }

    public void sendMessage(long chatId, String text) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}