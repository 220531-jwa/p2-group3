package dev.group3.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserProfilePage {
    
    private WebDriver driver;
    
    // === Fields ===
    
    @FindBy(xpath = "//*[@id=\"inputEmail\"]")
    public WebElement emailField;
    
    @FindBy(xpath = "//*[@id=\"inputPassword\"]")
    public WebElement passwordField;
    
    @FindBy(xpath = "//*[@id=\"inputFirstName\"]")
    public WebElement firstNameField;
    
    @FindBy(xpath = "//*[@id=\"inputLastName\"]")
    public WebElement lastNameField;
    
    @FindBy(xpath = "//*[@id=\"inputPhoneNumber\"]")
    public WebElement phoneNumberField;
    
    @FindBy(xpath = "//*[@id=\"inputFunds\"]")
    public WebElement fundsField;
    
    // === Buttons / Links ===
    
    @FindBy(xpath = "//*[@id=\"createUserDiv\"]/div/div[2]/div[1]/button")
    public WebElement backBtn;
    
    @FindBy(xpath = "//*[@id=\"submitButton\"]")
    public WebElement submitBtn;
    
    // === Labels ===
    
    @FindBy(xpath = "//*[@id=\"title\"]")
    public WebElement titleLable;
    
    // === Errors ===
    
    @FindBy(xpath = "//*[@id=\"error\"]")
    public WebElement errorMsg;
    
    @FindBy(xpath = "//*[@id=\"inputEmailError\"]")
    public WebElement emailErrorMsg;
    
    @FindBy(xpath = "//*[@id=\"inputPasswordError\"]")
    public WebElement passwordErrorMsg;
    
    @FindBy(xpath = "//*[@id=\"inputFirstNameError\"]")
    public WebElement firstNameErrorMsg;

    @FindBy(xpath = "//*[@id=\"inputLastNameError\"]")
    public WebElement lastNameErrorMsg;

    @FindBy(xpath = "//*[@id=\"inputPhoneNumberError\"]")
    public WebElement phoneNumberErrorMsg;

    @FindBy(xpath = "//*[@id=\"inputFundsError\"]")
    public WebElement fundsErrorMsg;
    
    // === Constructor ===
    
    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    
    // === Common User Actions ===
    
    public void fillOutForm(String email, String password, String firstName,
                            String lastName, String phoneNumber, String funds) {
        enterEmail(email);
        enterPassword(password);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPhoneNumber(phoneNumber);
        enterFunds(funds);
    }
    
    public void submitNewForm(String email, String password, String firstName,
            String lastName, String phoneNumber, String funds) {
        fillOutForm(email, password, firstName, lastName, phoneNumber, funds);
        submitBtn.click();
    }
    
    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }
    
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }
    
    public void enterFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }
    
    public void enterLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }
    
    public void enterPhoneNumber(String phoneNumber) {
        phoneNumberField.sendKeys(phoneNumber);
    }
    
    public void enterFunds(String funds) {
        fundsField.sendKeys(funds);
    }
}
