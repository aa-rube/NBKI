package App.Bot.TelegramBot;

import App.Bot.Services.ButtonsClicksReplyService;
import App.Bot.Services.CreateUserService;
import App.Bot.functions.EmailValidator;
import App.Bot.functions.TimeConverter;
import App.Bot.config.BotConfig;

import App.model.User;
import App.utils.JsonHashMapReader;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ExecutorService;

@Component
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final CreateUserService createUserService;
    private final ExecutorService executorService;
    private final ButtonsClicksReplyService buttonsClicksReplyService;
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            makeChoose(update);
        }
    }

    private void makeChoose(Update update) {
        JsonHashMapReader reader = new JsonHashMapReader();

        executorService.submit(() -> {

            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            User currentUser = reader.getUsersHashMap().get(chatId);

            switch (messageText) {
                case "/start" -> createUserService.start(chatId, update.getMessage().getFrom().getFirstName());
                case "Пoлyчить ceйчac\uD83D\uDCF2" -> buttonsClicksReplyService.returnRatingRightNow(chatId);
                case "Пеpиoд обнoвления⏲\uD83D\uDD04" -> buttonsClicksReplyService.returnTimeList(chatId);

                default -> {
                    if (EmailValidator.isValid(messageText)) {
                        createUserService.createLogin(chatId, update);
                    } else if (currentUser.getPasswordNBKI().equals("null") && EmailValidator.isValid(currentUser.getLoginNBKI())) {
                        createUserService.createPassword(chatId, messageText);
                    } else if (TimeConverter.isTimePeriod(messageText)) {
                        createUserService.createPeriodUpdate(chatId, messageText);
                    } else {
                        createUserService.wrongFormat(chatId);
                    }
                }
            }
        });
    }
}