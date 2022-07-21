package dev.group3.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage {
    
    private WebDriver driver;
    
    // === Top Nav Buttons ===
    
    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/form/button")
    public WebElement logoutBtn;
    
    // === Side Nav Buttons ===
    
    @FindBy(xpath = "//*[@id=\"userPanel_tag\"]")
    public WebElement editUserProfileBtn;
    
    // === Constructor ===
    
    public IndexPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    
    // === Common User Actions ===
    
    public void aCommonAction() {
        // TODO: meh
    }
}
