package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.SauceDemoLoginPage;
import utilities.Config;
import utilities.Driver;

public class SauceDemoLogin {

    SauceDemoLoginPage sauceDemoLoginPage = new SauceDemoLoginPage();

    @Given("user is in sauce Demo login page")
    public void user_is_in_sauce_demo_login_page() {
        Driver.getDriver().get(Config.getProperty("sauceDemo"));
    }

    @When("user proved valid username")
    public void user_proved_valid_username() {
        sauceDemoLoginPage.loginInputField.sendKeys(Config.getProperty("sauceDemoUsername"));
    }

    @When("user provides valid password")
    public void user_provides_valid_password() {
        sauceDemoLoginPage.passwordInputField.sendKeys(Config.getProperty("sauceDemoPassword"));
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        sauceDemoLoginPage.loginButton.click();
    }

    @Then("verify user logged in")
    public void verify_user_logged_in() {
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(expectedUrl, Driver.getDriver().getCurrentUrl());
        Driver.getDriver().close();
    }
}
