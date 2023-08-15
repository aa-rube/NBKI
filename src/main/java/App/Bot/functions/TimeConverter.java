package App.Bot.functions;

import java.util.regex.*;

public class TimeConverter {

    public static boolean isTimePeriod(String messageText) {
        String[] timePeriods = {"дней", "день", "дня", "час", "часа", "часов", "неделя", "недели"};

        for (String period : timePeriods) {
            if (messageText.contains(period)) {
                return true;
            }
        }
        return false;
    }

    public static int convertToHours(String text) {
        Pattern pattern = Pattern.compile("(\\d+)\\s*(дней|день|дня|часов|часа|час|недель|неделя|недели)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            int number = Integer.parseInt(matcher.group(1));
            String period = matcher.group(2);

            switch (period) {
                case "день":
                case "дня":
                case "дней":
                    return number * 24;
                case "час":
                case "часа":
                case "часов":
                    return number;
                case "неделя":
                case "недели":
                case "недель":
                    return number * 24 * 7;
                default:
                    return -1;
            }
        }

        return -1;
    }
}

