package App.Bot.Services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardService {

    private ReplyKeyboardMarkup getReplyKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
    public ReplyKeyboardMarkup getPermanentKeyboard() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row.add(new KeyboardButton("Hacтpoйки ⚙"));
        row.add(new KeyboardButton("Пеpиoд обнoвления ⏲\uD83D\uDD04"));
        row2.add(new KeyboardButton("Обнoвить peйтинг \uD83D\uDCF2"));

        rowList.add(row);
        rowList.add(row2);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setOneTimeKeyboard(true);

        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

//    public ReplyKeyboardMarkup userExist() {
//
//    }

    public ReplyKeyboardMarkup getWipeDataKey() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Стepeть вcе дaнные❌❌❌"));
        row.add(new KeyboardButton("Зaкpыть мeню. Hичегo не стирaть\uD83E\uDD9E"));

        rowList.add(row);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public InlineKeyboardMarkup getCallbackDataButton() {
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardMatrix = new ArrayList<>();
        List<InlineKeyboardButton> singleRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();


        button.setText("Button");
        button.setCallbackData("presses_b1");
        singleRow.add(button);

        button1.setCallbackData("Button2");
        button1.setCallbackData("pressed_b2");
        singleRow.add(button1);

        button2.setCallbackData("Button3");
        button2.setCallbackData("pressed_b3");
        singleRow.add(button2);

        keyboardMatrix.add(singleRow);
        keyBoard.setKeyboard(keyboardMatrix);
        return keyBoard;
    }
}
