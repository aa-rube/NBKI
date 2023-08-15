package App.Bot.Services;

public interface MessageSender {
    void sendRateMessage(Long chatId, String message);

}
