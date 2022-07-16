package dev.group3.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    
    private WebDriver driver;
    
    // === Fields ===
    
    @FindBy(xpath = "//*[@id=\"inputUsername\"]")
    public WebElement usernameField;
    
    @FindBy(xpath = "//*[@id=\"inputPassword\"]")
    public WebElement passwordField;
    
    // === Buttons / Links ===
    
    @FindBy(xpath = "//*[@id=\"loginform\"]/form/div[4]/button")
    public WebElement signInBtn;
    
    @FindBy(xpath = "//*[@id=\"loginform\"]/form/p[2]/a")
    public WebElement createAccountLink;
    
    // === Errors ===
    
    @FindBy(xpath = "//*[@id=\"error\"]")
    public WebElement errorMsg;
    
    // === Constructor ===
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    
    // === Common User Actions ===
    
    /**
     * Signs in the user with the given credentials
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signInBtn.click();
    }
}
