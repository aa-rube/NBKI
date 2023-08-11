package parser;

import model.Report;
import model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SiteParser {
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static WebDriver driver = new ChromeDriver();
    public static void parserSite(User user, List<Report> reportList) throws Exception {
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().window().minimize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://person.nbki.ru/login");
        login(user, reportList);
    }

    private static void login(User user, List<Report> reportList) throws Exception {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        loginPage.inputLogin(user.getLoginNBKI());
        loginPage.inputPasswd(user.getPasswordNBKI());
        loginPage.clickLoginBtn();
        tearDown(user, reportList);
    }

    private static void tearDown(User user, List<Report> reportList) throws  Exception{
        Thread.sleep(5000);
        profilePage.refreshRate();

        Thread.sleep(5500);
        profilePage.history();

        Thread.sleep(5000);
        String rate = profilePage.getUserRate();
        reportList.add(getReport(user, rate));

        profilePage.exitBtn();
        Thread.sleep(500);
        profilePage.userLogout();
    }

    private static Report getReport (User user, String rate) {
        return new Report(user.getTelegramId(), user.getUserName(), rate);
    }
}