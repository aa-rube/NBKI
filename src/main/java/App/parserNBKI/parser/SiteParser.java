package App.parserNBKI.parser;

import App.utils.JsonHashMapReader;
import App.utils.JsonHashMapWriter;
import App.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SiteParser {
    public static LoginPage loginPage;
    public static ProfilePage profilePage;

    private  static String rating;
    private  static final WebDriver driver = new ChromeDriver();
    private static void parserSite() {
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().window().minimize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://person.nbki.ru/login");
    }

    private static void login(User user) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        loginPage.inputLogin(user.getLoginNBKI());
        loginPage.inputPasswd(user.getPasswordNBKI());
        loginPage.clickLoginBtn();
    }

    private static void tearDown() throws  Exception{
        Thread.sleep(5000);
        profilePage.refreshRate();

        Thread.sleep(5500);
        profilePage.history();

        Thread.sleep(5000);
        rating = profilePage.getUserRate();

        profilePage.exitBtn();
        Thread.sleep(500);
        profilePage.userLogout();

    }

    public static String getRating(User user) throws Exception {
        parserSite();
        login(user);
        tearDown();
        return rating;
    }
}