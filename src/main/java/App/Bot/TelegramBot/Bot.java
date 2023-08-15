package App.Bot.TelegramBot;

import App.Bot.Services.ChatService;
import App.Bot.functions.EmailValidator;
import App.Bot.functions.TimeConverter;
import App.Bot.config.BotConfig;

import App.model.User;
import App.utils.JsonHashMapReader;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final ChatService chatService;

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
        JsonHashMapReader reader = new JsonHashMapReader();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            User currentUser = reader.getUsersHashMap().get(chatId);

            switch (messageText) {
                case "/start":
                    chatService.start(chatId, update.getMessage().getFrom().getFirstName());
                    break;
                case "/now":
                    chatService.sendRatingRightNow(chatId, reader.getUsersHashMap());
                    break;
                default:
                    if (messageText.contains("@") && EmailValidator.isValid(messageText)) {
                        chatService.createLogin(chatId, update);
                    } else if (currentUser.getPasswordNBKI().equals("p") && currentUser.getLoginNBKI().contains("@")) {
                        chatService.createPassword(chatId, messageText);
                    } else if (TimeConverter.isTimePeriod(messageText)) {
                        chatService.createPeriodUpdate(chatId, messageText);
                    } else {
                        chatService.wrongFormat(chatId);
                    }
                    break;
            }
        }
    }
}