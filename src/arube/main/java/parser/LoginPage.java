package parser;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class LoginPage {
    public WebDriver driver;
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    @FindBy(xpath = "//*[@id=\"fLogin\"]")
    private WebElement loginField;
    @FindBy(xpath = "//*[@id=\"fPassword\"]")
    private WebElement passwdField;

    @FindBy(xpath = "//*[@id=\"auth-layout\"]/main/section/div/div/button[1]")
    private WebElement loginBtn;

    public void inputLogin(String login) {
        loginField.sendKeys(Keys.CONTROL + "a");
        loginField.sendKeys(Keys.DELETE);
        loginField.sendKeys(login);
    }

    public void inputPasswd(String passwd) {
        passwdField.sendKeys(passwd);
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }
}