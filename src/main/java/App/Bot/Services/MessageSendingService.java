package App.Bot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageSendingService {
    private ApplicationContext context;

    @Autowired
    public MessageSendingService(ApplicationContext context) {
        this.context = context;
    }

    public void sendMsg(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        executeMsg(sendMessage);
    }

    public void sendMsg(Long chatId, String message, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        sendMessage.setReplyMarkup(keyboardMarkup);
        executeMsg(sendMessage);
    }

    public void sendMsg(Long chatId, String message, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);

        sendMessage.setReplyMarkup(inlineKeyboard);
        executeMsg(sendMessage);
    }

    private void executeMsg(SendMessage sendMessage) {
        try {
            TelegramLongPollingBot bot = context.getBean(TelegramLongPollingBot.class);
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}