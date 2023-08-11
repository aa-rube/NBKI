package refresh;


import model.Report;
import model.User;
import parser.SiteParser;
import reportWriter.ReportWrite;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RefreshTime {
    public void refreshTime() throws Exception {
        JSONReader jsonReader = new JSONReader();
        List<User> users = jsonReader.getUsersList();
        List<Report> reportList = new ArrayList<>();
        ReportWrite reportWrite = new ReportWrite();

        for(User user : users) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(user.getLastUpdate(), now);
            long diff = Math.abs(duration.toHours());

            if (diff >= user.getRefreshPeriodInHours()&& user.isPaid()) {
                refreshRate(user, reportList);
            }
            UsersJsonWriter usersJsonWriter = new UsersJsonWriter();
            usersJsonWriter.writeJason(users);
        }

        reportWrite.writeJason(reportList);
    }


    public void refreshRate(User user, List<Report> reportList) throws Exception {
        SiteParser.parserSite(user, reportList);
        user.setLastUpdate(LocalDateTime.now());

    }
}
