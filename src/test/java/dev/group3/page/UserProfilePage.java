package dev.group3.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserProfilePage {

    private WebDriver driver;
    
    // === xpaths ===
    
    public final String firstNameXpath = "//*[@id=\"UPinputFirstName\"]";

    // === Fields ===

    @FindBy(xpath = "//*[@id=\"UPinputEmail\"]")
    public WebElement emailField;

    @FindBy(xpath = "//*[@id=\"UPinputPassword\"]")
    public WebElement passwordField;

    @FindBy(xpath = firstNameXpath)
    public WebElement firstNameField;

    @FindBy(xpath = "//*[@id=\"UPinputLastName\"]")
    public WebElement lastNameField;

    @FindBy(xpath = "//*[@id=\"UPinputPhoneNumber\"]")
    public WebElement phoneNumberField;

    @FindBy(xpath = "//*[@id=\"UPinputFunds\"]")
    public WebElement fundsField;

    // === Buttons / Links ===

    @FindBy(xpath = "//*[@id=\"userProfileBackBtn\"]")
    public WebElement backBtn;

    @FindBy(xpath = "//*[@id=\"userProfileSubmitBtn\"]")
    public WebElement submitBtn;

    // === Labels ===

    @FindBy(xpath = "//*[@id=\"UPtitle\"]")
    public WebElement titleLable;

    // === Errors ===

    @FindBy(xpath = "//*[@id=\"userProfileError\"]")
    public WebElement errorMsg;

    @FindBy(xpath = "//*[@id=\"UPinputEmailError\"]")
    public WebElement emailErrorMsg;

    @FindBy(xpath = "//*[@id=\"UPinputPassword\"]")
    public WebElement passwordErrorMsg;

    @FindBy(xpath = "//*[@id=\"UPinputFirstName\"]")
    public WebElement firstNameErrorMsg;

    @FindBy(xpath = "//*[@id=\"UPinputLastName\"]")
    public WebElement lastNameErrorMsg;

    @FindBy(xpath = "//*[@id=\"UPinputPhoneNumber\"]")
    public WebElement phoneNumberErrorMsg;

    @FindBy(xpath = "//*[@id=\"UPinputFunds\"]")
    public WebElement fundsErrorMsg;

    // === Constructor ===

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    // === Common User Actions ===

    public void fillOutForm(String email, String password, String firstName, String lastName, String phoneNumber,
            String funds) {
        if (email != null)       enterEmail(email);
        if (password != null)    enterPassword(password);
        if (firstName != null)   enterFirstName(firstName);
        if (lastName != null)    enterLastName(lastName);
        if (phoneNumber != null) enterPhoneNumber(phoneNumber);
        if (funds != null)       enterFunds(funds);
    }

    public void submitNewForm(String email, String password, String firstName, String lastName, String phoneNumber,
            String funds) {
        fillOutForm(email, password, firstName, lastName, phoneNumber, funds);
        submitBtn.click();
    }
    
    public void saveExistingForm(String password, String firstName, String lastName, String phoneNumber) {
        fillOutForm(null, password, firstName, lastName, phoneNumber, null);
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
