package App.Bot.content;

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

        row.add(new KeyboardButton(Buttons.YES.getStr()));
        row.add(new KeyboardButton(Buttons.NO.getStr()));

        rowList.add(row);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public ReplyKeyboardMarkup getSubscriptionsButtons(double d) {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(new KeyboardButton(Buttons.TP_1M.getStr() + 199 * d));
        row.add(new KeyboardButton(Buttons.TP_6M.getStr() + 699 * d));
        row.add(new KeyboardButton(Buttons.TP_12M.getStr() + 1299 * d));
        row2.add(new KeyboardButton(Buttons.GET_BACK.getStr()));
        rowList.add(row);
        rowList.add(row2);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }
}
