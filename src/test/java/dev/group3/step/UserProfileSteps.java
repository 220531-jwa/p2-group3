package dev.group3.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.group3.page.IndexPage;
import dev.group3.page.LoginPage;
import dev.group3.page.UserProfilePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserProfileSteps {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private UserProfilePage userProfilePage;
    private IndexPage indexPage;
    
    // === Logging in ===
    
    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        driver.get("http://localhost:8080/html/login.html");
    }

    @When("the user enters their {string} and {string} and clicks the login button")
    public void the_user_enters_their_and_and_clicks_the_login_button(String username, String password) {
        waitForClickable(loginPage.signInBtn);
        loginPage.login(username, password);
    }

    @Then("the user will be on the homepage")
    public void the_user_will_be_on_the_homepage() {
        waitForClickable(indexPage.logoutBtn);
        assertEquals("Home Page", driver.getTitle());
    }
    
    // === Logging out ===

    @Given("the user is logged in as <email1> <pass1> and on the homepage")
    public void the_user_is_logged_in_as_email1_password_and_on_the_homepage() {
        login("email1", "pass1");
    }

    @When("the user clicks the logout button in the nav bar")
    public void the_user_clicks_the_logout_button_in_the_nav_bar() {
        waitForClickable(indexPage.logoutBtn);
        indexPage.logoutBtn.click();
    }

    @Then("the user will be logged out and on the login page")
    public void the_user_will_be_logged_out_and_on_the_login_page() {
        waitForClickable(loginPage.signInBtn);
        assertEquals("Login Page", driver.getTitle());
    }
    
    // === Utility ===
    
    public void login(String username, String password) {
        driver.get("http://localhost:8080/html/login.html");
        waitForClickable(loginPage.signInBtn);
        loginPage.login(username, password);
    }
    
    public void waitForClickable(WebElement click) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.elementToBeClickable(click));
    }
}
