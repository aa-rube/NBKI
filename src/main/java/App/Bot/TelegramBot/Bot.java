package App.Bot.TelegramBot;

import App.Bot.Services.ButtonsClicksReplyService;
import App.Bot.Services.CheckInt;
import App.Bot.Services.CreateUserService;
import App.Bot.functions.EmailValidator;
import App.Bot.functions.TimeConverter;
import App.Bot.config.BotConfig;

import App.model.User;
import App.utils.JsonHashMapReader;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
            textQuery(update);
        }

        if (update.hasCallbackQuery()) {
            callbackQuery(update);
        }

    }

    private void textQuery(Update update) {
        JsonHashMapReader reader = new JsonHashMapReader();

        executorService.submit(() -> {

            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            User currentUser = reader.getUsersHashMap().get(chatId);

            switch (messageText) {
                case "/start" -> createUserService.start(chatId, update.getMessage().getFrom().getFirstName(), update);
                case "Пoлyчить ceйчac\uD83D\uDCF2" -> buttonsClicksReplyService.returnRatingRightNow(chatId);
                case "Пеpиoд обнoвления⏲\uD83D\uDD04" -> buttonsClicksReplyService.returnTimeList(chatId);

                default -> {
                    if (EmailValidator.isValid(messageText)) {
                        createUserService.createLogin(chatId, messageText);
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

    public void callbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        CheckInt checkInt = new CheckInt();
        if(callbackData.equals("close&send")) {
            editMessageText(chatId,messageId,"Вы можете всегда вернуться к этому.");
            return;
        }

        if (checkInt.isInt(callbackData)) {
            buttonsClicksReplyService.setTimeUpdate(chatId, callbackData);
            editMessageText(chatId, messageId,"Период успешно установлен!");
        }
//        switch (callbackData) {
//            case "3 часа":
//                // Логика для обработки выбора "3 часа"
//                break;
//            case "12 часов":
//                // Логика для обработки выбора "12 часов"
//                break;
//            // ... (для других кнопок)
//            case "close&send":
//                // Если вы хотите удалить клавиатуру или отправить сообщение пользователю
//                // Например, вы можете использовать EditMessageText
//                EditMessageText newMessage = new EditMessageText()
//                        .setChatId(chatId)
//                        .setMessageId(messageId)
//                        .setText("Выбрана опция: Закрыть")
//                        .setReplyMarkup(null); // Это удалит кнопки
//                try {
//                    execute(newMessage); // Отправка обновленного сообщения
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
    }

    private void editMessageText(long chatId, int messageId, String text) {
        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(text);
        newMessage.setReplyMarkup(null);
        try {
            execute(newMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
