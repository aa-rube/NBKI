package App.Bot.Services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardService {

    private ReplyKeyboardMarkup getReplyOneTimeKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getReplyKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }
    public ReplyKeyboardMarkup getPermanentKeyboard() {
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row.add(new KeyboardButton("Hacтpoйки ⚙"));
        row.add(new KeyboardButton("Пеpиoд обнoвления⏲\uD83D\uDD04"));
        row2.add(new KeyboardButton("Пoлyчить ceйчac\uD83D\uDCF2"));

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
        row.add(new KeyboardButton("Стepeть вcе дaнные❌❌❌"));
        row2.add(new KeyboardButton("Зaкpыть мeню. Hичегo не стирaть\uD83E\uDD9E"));

        rowList.add(row);
        rowList.add(row2);

        ReplyKeyboardMarkup replyKeyboard = getReplyKeyboard();
        replyKeyboard.setKeyboard(rowList);
        return replyKeyboard;
    }

    public InlineKeyboardMarkup getTimePeriods() {
        InlineKeyboardMarkup inLineKeyBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardMatrix = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();

        InlineKeyboardButton oneHour  = new InlineKeyboardButton();
        InlineKeyboardButton twoHours = new InlineKeyboardButton();
        InlineKeyboardButton threeHours = new InlineKeyboardButton();
        InlineKeyboardButton threeDays = new InlineKeyboardButton();
        InlineKeyboardButton sevenDays = new InlineKeyboardButton();
        InlineKeyboardButton fourteenDays = new InlineKeyboardButton();
        InlineKeyboardButton oneMonth = new InlineKeyboardButton();
        InlineKeyboardButton twoMonth = new InlineKeyboardButton();
        InlineKeyboardButton threeMonth = new InlineKeyboardButton();
        InlineKeyboardButton goBack = new InlineKeyboardButton();


        oneHour.setText("3ч");
        oneHour.setCallbackData("3 часа");
        twoHours.setText("12ч");
        twoHours.setCallbackData("12 часов");
        threeHours.setText("24ч");
        threeHours.setCallbackData("24 часа");
        firstRow.add(oneHour);
        firstRow.add(twoHours);
        firstRow.add(threeDays);

        threeDays.setText("3д");
        threeDays.setCallbackData("3 дня");
        sevenDays.setText("7д");
        sevenDays.setCallbackData("7 дней");
        fourteenDays.setText("14д");
        fourteenDays.setCallbackData("14 дней");
        secondRow.add(threeDays);
        secondRow.add(sevenDays);
        secondRow.add(fourteenDays);

        oneMonth.setText("1м");
        oneMonth.setCallbackData("1 месяц");
        twoMonth.setText("2м");
        twoMonth.setCallbackData("2 месца");
        threeMonth.setText("3м");
        threeMonth.setCallbackData("3 месяца");
        thirdRow.add(oneMonth);
        thirdRow.add(twoMonth);
        thirdRow.add(threeMonth);

        goBack.setText("Назад");
        goBack.setCallbackData("close&send");
        fourthRow.add(goBack);

        keyboardMatrix.add(firstRow);
        keyboardMatrix.add(secondRow);
        keyboardMatrix.add(thirdRow);
        keyboardMatrix.add(fourthRow);
        inLineKeyBoard.setKeyboard(keyboardMatrix);
        return inLineKeyBoard;
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
