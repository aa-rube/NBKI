package parser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {
    public WebDriver driver;
    public ProfilePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }

    @FindBy(xpath = "//*[@id=\"main-layout\"]/main/section[2]/div[2]/div[3]/div[2]/button")
    private WebElement refreshRate;

    @FindBy(xpath = "//*[@id=\"main-layout\"]/main/section[1]/div/ul/li[4]/span")
    private WebElement history;

    @FindBy(xpath = "//*[@id=\"main-layout\"]/header/nav/div/div[1]/div/button")
    private WebElement exitBtn;

    @FindBy(xpath = "//*[@id=\"main-layout\"]/header/nav/div/div[2]/span")
    private WebElement logoutBtn;

    @FindBy(xpath = "//*[@id=\"main-layout\"]/main/section[2]/div/div/ul/li[1]/div/span[3]")
    WebElement rate;

    public String getUserRate() {
        return rate.getText();
    }

    public void refreshRate() {
        refreshRate.click();
    }

    public void exitBtn() {
        exitBtn.click();
    }

    public void userLogout() {
        logoutBtn.click();
    }

    public void  history() {
        history.click();
    }
}