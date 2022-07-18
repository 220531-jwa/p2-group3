package dev.group3.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.group3.page.IndexPage;
import dev.group3.page.LoginPage;
import dev.group3.page.UserProfilePage;
import dev.group3.runner.CucumberRunner;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserProfileSteps {
    
    private WebDriver driver = CucumberRunner.driver;
    private LoginPage loginPage = CucumberRunner.loginPage;
    private UserProfilePage userProfilePage = CucumberRunner.userProfilePage;
    private IndexPage indexPage = CucumberRunner.indexPage;
    
    private int explicitWaitSec = 5;
    
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
    
    // === New User Profile ===
    
    @Given("a user is on the login page")
    public void a_user_is_on_the_login_page() {
        driver.get("http://localhost:8080/html/login.html");
    }

    @When("the user clicks on the new account link")
    public void the_user_clicks_on_the_new_account_link() {
        waitForClickable(loginPage.createAccountLink);
        loginPage.createAccountLink.click();
    }

    @Then("the user will be on the new user profile page")
    public void the_user_will_be_on_the_new_user_profile_page() {
        assertEquals("New User:", userProfilePage.titleLable.getText());
    }

    @Given("a use is on the new account page")
    public void a_use_is_on_the_new_account_page() {
        driver.get("http://localhost:8080/html/login.html");
        waitForClickable(loginPage.createAccountLink);
        loginPage.createAccountLink.click();
    }

    @When("the user enters valid account information")
    public void the_user_enters_valid_account_information() {
        userProfilePage.fillOutForm("newEmail@email.com", "Pass123!", "Wolfy", "Flowy", "111-222-3333", "100.00");
    }

    @When("clicks on the submit button")
    public void clicks_on_the_submit_button() {
        userProfilePage.submitBtn.click();
    }

    @Then("the user will be on the home page")
    public void the_user_will_be_on_the_home_page() {
        waitForClickable(indexPage.logoutBtn);
        assertEquals("Home Page", driver.getTitle());
    }

    @Then("and will have a new account")
    public void and_will_have_a_new_account() {
        indexPage.editUserProfileBtn.click();
//        waitForClickable(userProfilePage.submitBtn);
        waitForUserProfileToFill();
        assertEquals("Wolfy", userProfilePage.firstNameField.getAttribute("value"));
    }
    
    // === View User Profile ===
    
    @Given("A user is logged in with {string} {string} and on the homepage")
    public void a_user_is_logged_in_with_and_on_the_homepage(String username, String password) {
        login(username, password);
    }

    @When("User clicks on the <edit user profile> button")
    public void user_clicks_on_the_edit_user_profile_button() {
        waitForClickable(indexPage.editUserProfileBtn);
        indexPage.editUserProfileBtn.click();
    }

    @Then("User Profile page appears")
    public void user_profile_page_appears() {
        assertEquals("Edit User:", userProfilePage.titleLable.getText());
    }
    
    // === Edit User Profile ===

    @Given("A user is logged in with {string} {string} and on the end user profile page")
    public void a_user_is_logged_in_with_and_on_the_end_user_profile_page(String username, String password) {
        login(username, password);
        waitForClickable(indexPage.editUserProfileBtn);
        indexPage.editUserProfileBtn.click();
    }

    @When("User enters new valid user information")
    public void user_enters_new_valid_user_information() {
        userProfilePage.fillOutForm(null, null, "newFirstName", "newLastName", "111-111-1111", null);
    }

    @When("User clicks the save button")
    public void user_clicks_the_save_button() {
        userProfilePage.submitBtn.click();
    }

    @Then("The user profile information is changed")
    public void the_user_profile_information_is_changed() {
        waitForClickable(indexPage.editUserProfileBtn);
        waitForUserProfileToFill();
        assertEquals("newFirstName", userProfilePage.firstNameField.getAttribute("value"));
    }
    
    // === Utility ===
    
    public void login(String username, String password) {
        driver.get("http://localhost:8080/html/login.html");
        waitForClickable(loginPage.signInBtn);
        loginPage.login(username, password);
    }
    
    public void waitForClickable(WebElement click) {
        new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSec))
        .until(ExpectedConditions.elementToBeClickable(click));
    }
    
    public void waitForUserProfileToFill() {
        new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSec))
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath(userProfilePage.firstNameXpath)).getAttribute("value").length() != 0;
            }});
    }
}
