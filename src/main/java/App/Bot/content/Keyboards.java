package App.Bot.content;

import App.p2pkassa.model.PayData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class Keyboards {
    private ReplyKeyboardMarkup getReplyKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMainKeyboard() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row.add(new KeyboardButton(Buttons.SETTINGS.getStr()));
        row.add(new KeyboardButton(Buttons.REFRESH_TIME.getStr()));
        row2.add(new KeyboardButton(Buttons.GET_RIGHT_NOW.getStr()));

        rowList.add(row);
        rowList.add(row2);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public ReplyKeyboardMarkup getWipeDataKey() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row.add(new KeyboardButton(Buttons.WIPE_ALL_DATA.getStr()));
        row2.add(new KeyboardButton(Buttons.CLOSE_WIPE.getStr()));

        rowList.add(row);
        rowList.add(row2);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public ReplyKeyboardMarkup getSettingsKeyBoard() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row.add(new KeyboardButton(Buttons.BUY_SUBSCRIPTION.getStr()));
        row2.add(new KeyboardButton(Buttons.WIPE_ALL_DATA.getStr()));
        row3.add(new KeyboardButton(Buttons.GET_BACK.getStr()));

        rowList.add(row);
        rowList.add(row2);
        rowList.add(row3);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public InlineKeyboardMarkup getTimePeriods() {
        InlineKeyboardMarkup inLineKeyBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardMatrix = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        InlineKeyboardButton oneHour = new InlineKeyboardButton();


        oneHour.setText("3ч");
        oneHour.setCallbackData("3");
        firstRow.add(oneHour);


        keyboardMatrix.add(firstRow);

        inLineKeyBoard.setKeyboard(keyboardMatrix);
        return inLineKeyBoard;
    }

    public ReplyKeyboardMarkup questionSure() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(new KeyboardButton(Buttons.NO_NO_NO.getStr()));
        row.add(new KeyboardButton(Buttons.WIPE_ALL_DATA.getStr()));
        row2.add(new KeyboardButton(Buttons.GET_BACK.getStr()));

        rowList.add(row);
        rowList.add(row2);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public ReplyKeyboardMarkup getHavePromoOrNot() {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add(new KeyboardButton(Buttons.I_HAVE_PROMO.getStr()));
        row.add(new KeyboardButton(Buttons.I_DONT_HAVE_PROMO.getStr()));

        rowList.add(row);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public InlineKeyboardMarkup getSubscriptionsButtons(double d) {
        InlineKeyboardMarkup inLineKeyBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardMatrix = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();

        InlineKeyboardButton six = new InlineKeyboardButton();
        InlineKeyboardButton twelve = new InlineKeyboardButton();
        InlineKeyboardButton back = new InlineKeyboardButton();

        six.setText(Buttons.TP_6M.getStr() + 399 * d);
        six.setCallbackData(Buttons.TP_6M.getStr() + 399 * d);
        twelve.setText(Buttons.TP_12M.getStr() + 699 * d);
        twelve.setCallbackData(Buttons.TP_12M.getStr() + 699 * d);
        back.setText(Buttons.GET_BACK.getStr());
        back.setCallbackData(Buttons.GET_BACK.getStr());

        firstRow.add(six);
        firstRow.add(twelve);
        secondRow.add(back);

        keyboardMatrix.add(firstRow);
        keyboardMatrix.add(secondRow);

        inLineKeyBoard.setKeyboard(keyboardMatrix);
        return inLineKeyBoard;
    }

    public InlineKeyboardMarkup getPayOptions(double amount, String buttonData) {
        InlineKeyboardMarkup inLineKeyBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardMatrix = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        List<InlineKeyboardButton> secondRow = new ArrayList<>();

        InlineKeyboardButton card = new InlineKeyboardButton();
        InlineKeyboardButton sbp = new InlineKeyboardButton();
        InlineKeyboardButton close = new InlineKeyboardButton();

        String s = String.valueOf((buttonData.split("₽")[0]).trim().split("м")[0]);
        System.out.println(s);
        card.setText(Buttons.PAY_CARD.getStr());
        card.setCallbackData(PayData.CARD.getStr() + ":" + amount + ":" + s);

        sbp.setText(Buttons.PAY_SBP.getStr());
        sbp.setCallbackData(PayData.SBP.getStr() + ":" + amount + ":" + s);

        close.setText(Buttons.CLOSE.getStr());
        close.setCallbackData(Buttons.CLOSE.getStr());

        firstRow.add(card);
        firstRow.add(sbp);
        secondRow.add(close);

        keyboardMatrix.add(firstRow);
        keyboardMatrix.add(secondRow);

        inLineKeyBoard.setKeyboard(keyboardMatrix);
        return inLineKeyBoard;
    }

    public InlineKeyboardMarkup iSendMyMoney(Long chatId, int t, int remoteOrderId) {
        InlineKeyboardMarkup inLineKeyBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardMatrix = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        InlineKeyboardButton card = new InlineKeyboardButton();
        card.setText("Осталось " + t + "минут. Нaжать, если платеж внесен.");
        card.setCallbackData(Buttons.I_MADE_THIS_PAY.getStr() + chatId + ":" + remoteOrderId);
        firstRow.add(card);
        keyboardMatrix.add(firstRow);
        inLineKeyBoard.setKeyboard(keyboardMatrix);
        return inLineKeyBoard;
    }
}
