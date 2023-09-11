package App.Bot.TelegramChat;

import App.Bot.content.Buttons;
import App.Bot.content.Keyboards;
import App.Bot.config.BotConfig;

import App.Bot.content.StringsData;
import App.Bot.functions.EmailValidator;
import App.Bot.functions.UserService;
import App.Bot.model.PromoCode;
import App.Bot.model.UserNBKI;
import App.parserNBKI.ParserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class Chat extends TelegramLongPollingBot {

    private final  HashSet<Long> hasPromo = new HashSet<>();
    private final HashSet<Long> waitEmail = new HashSet<>();
    private final HashSet<Long> waitPass = new HashSet<>();
    private final HashMap<Long, Integer> chatIdMsgId = new HashMap<>();
    @Autowired
    private BotConfig botConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailValidator validator;
    @Autowired
    private StringsData data;
    @Autowired
    private Keyboards keyboard;

    private final ParserManager manager = new ParserManager();

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
        if (update.hasMessage()) {
            messageTextHandle(update);
            return;
        }

        if (update.hasCallbackQuery()) {
            callbackQuery(update);
        }
    }

    private void messageTextHandle(Update update) {
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        chatIdMsgId.put(chatId, update.getMessage().getMessageId());
        if(text.contains("мec ₽")) {

        }
        if (hasPromo.contains(chatId)) {
            hasPromo.remove(chatId);
            PromoCode code = data.getPromoInfo(text);

            if (code == null) {
                sendMsg(chatId, data.getPromoDoesNotExist(), keyboard.getSubscriptionsButtons(1));
                return;
            }
            sendMsg(chatId, data.getHooray(), keyboard.getSubscriptionsButtons(code.getCoefficient()));
            return;
        }

        if (text.equals(Buttons.YES.getStr())) {
            hasPromo.add(chatId);
            sendMsg(chatId, data.getInputYouPromo());
            return;
        }

        if (text.equals(Buttons.NO.getStr())) {
            sendMsg(chatId, data.getChooseOption(), keyboard.getSubscriptionsButtons(1));
            return;
        }

        if (text.equals(Buttons.BUY_SUBSCRIPTION.getStr())) {
            sendMsg(chatId, data.doYouHaveAPromo, keyboard.getHavePromoOrNot());
            return;
        }

        if(text.equals(Buttons.WIPE_ALL_DATA.getStr())) {
            sendMsg(chatId, data.getDoYouSure(), keyboard.questionSure());
            return;
        }

        if (text.equals(Buttons.GET_BACK.getStr())) {
            sendMsg(chatId, "Ok", keyboard.getMainKeyboard());
            return;
        }

        if (text.equals(Buttons.SETTINGS.getStr())) {
            sendMsg(chatId, data.getSettingType(),keyboard.getSettingsKeyBoard());
            return;
        }

        if (text.equals(Buttons.GET_RIGHT_NOW.getStr())) {
            Optional<UserNBKI> userNBKIOptional = userService.findUserNBKIByChatId(chatId);

            if (userNBKIOptional.isPresent()) {
                try {
                    sendMsg(chatId, manager.getRating(userNBKIOptional.get()));
                    return;
                } catch (Exception e) {
                    sendMsg(chatId, data.somethingWentWrong);
                    return;
                }
            }
            sendMsg(chatId, data.getNoRegistered());
            return;
        }

        if (text.equals("/start")) {
            if (userService.findUserNBKIByChatId(chatId).isPresent()) {
                sendMsg(chatId, data.welcomeBack, keyboard.getMainKeyboard());
                return;
            }

            waitEmail.add(chatId);
            sendMsg(chatId, userService.createUser(chatId, update));
            return;
        }

        if (validator.isValid(text.trim()) && waitEmail.contains(chatId)) {
            waitEmail.remove(chatId);
            waitPass.add(chatId);
            sendMsg(chatId, userService.createUserLogin(chatId, text.trim()));
            return;
        }

        if (!validator.isValid(text) && !waitEmail.contains(chatId) && waitPass.contains(chatId)) {
            waitPass.remove(chatId);
            sendMsg(chatId, userService.createPassword(chatId, text));
            return;
        }

        if (userService.setNewPeriodUpdate(chatId, text).length() > 160) {
            sendMsg(chatId, data.createComplete(text), keyboard.getMainKeyboard());
            return;
        }

        sendMsg(chatId, data.getWrongFormat(), keyboard.getMainKeyboard());
    }

    public void callbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        chatIdMsgId.put(chatId, messageId);

    }

    @Scheduled(fixedRate = 600000)
    private void scheduleSendRate() {
        LocalDateTime now = LocalDateTime.now();
        List<UserNBKI> users = userService.getAllPaidUsers();

        for (UserNBKI user : users) {
            if (user.getLastUpdate().plusHours(user.getRefreshPeriodInHours()).isBefore(now)) {
                sendMsg(user.getChatId(), manager.getRating(user));
                user.setLastUpdate(now);
            }
        }

        userService.saveAllUsers(users);
    }


    public void sendAndDelete(long chatId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(keyboard);

        try {
            Message sentMessage = execute(message);
            deleteMessage(chatId, sentMessage.getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(Long chatId, File file) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(file));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private synchronized void responseToUser(Long senderId, int messageId, String text) {
        SendMessage responseMessage = new SendMessage();
        responseMessage.setChatId(senderId);
        responseMessage.setText(text);
        responseMessage.setReplyToMessageId(messageId);
        responseMessage.setReplyMarkup(null);

        try {
            execute(responseMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessageKeyboard(Long chatId, int messageId, String content, InlineKeyboardMarkup keyboard) {
        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(content);
        newMessage.setReplyMarkup(keyboard);
        try {
            execute(newMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editMessageText(long chatId, String text) {
        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(chatIdMsgId.get(chatId));
        newMessage.setText(text);
        newMessage.setReplyMarkup(null);
        try {
            execute(newMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        executeMsg(message);
    }

    private void sendMsg(Long chatId, String message, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        sendMessage.setReplyMarkup(keyboardMarkup);
        executeMsg(sendMessage);
    }

    private void sendMsg(Long chatId, String message, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(inlineKeyboard);
        executeMsg(sendMessage);
    }

    public void deleteMessage(Long chatId, int msgId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(msgId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void executeMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
