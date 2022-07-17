package dev.group3.runner;

import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.WebDriver;

import dev.group3.page.IndexPage;
import dev.group3.page.LoginPage;
import dev.group3.page.UserProfilePage;
import dev.group3.util.WebDriverUtil;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

@Suite
public class CucumberRunner {
    
    public static WebDriver driver;
    public static LoginPage loginPage;
    public static UserProfilePage userProfilePage;
    public static IndexPage indexPage;
    
    @BeforeAll
    public static void setup() {
        driver = WebDriverUtil.getFireFoxDriver();
        loginPage = new LoginPage(driver);
        userProfilePage = new UserProfilePage(driver);
        indexPage = new IndexPage(driver);
    }
    
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
