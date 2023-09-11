package App.parserNBKI;

import App.Bot.content.StringsData;
import App.Bot.model.UserNBKI;


public class ParserManager {
    private final StringsData data = new StringsData();

    public String getRating(UserNBKI user) {
        if (!user.isPaid()) {
            return data.subscriptionEnd;
        }

        try {
            return SiteParser.getRating(user);

        } catch (Exception e) {
            return "";

        }
    }
}
