package org.example.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@DefaultUrl("https://magento.softwaretestingboard.com/customer/account/login/")
public class LoginPage extends PageObject {

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "pass")
    WebElement passwordField;

    @FindBy(id = "send2")
    WebElement loginButton;

    @FindBy(css = ".greet.welcome .logged-in")
    WebElementFacade welcomeText;


    @FindBy(css = ".message-error div")
    WebElement loginErrorMessage;

    public boolean is_login_error_displayed() {
        withTimeoutOf(Duration.ofDays(5)).waitFor(loginErrorMessage);
        return loginErrorMessage.isDisplayed() &&
                loginErrorMessage.getText().toLowerCase().contains("incorrect");
    }

    public void enter_email(String email) {
        emailField.sendKeys(email);
    }

    public void enter_password(String password) {
        passwordField.sendKeys(password);
    }

    public void click_login() {
        loginButton.click();
    }

    public boolean is_logged_in_successfully() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(welcomeText);
        return welcomeText.isDisplayed() && welcomeText.getText().toLowerCase().contains("welcome");
    }


    public String getUrl() {
        return "https://magento.softwaretestingboard.com/customer/account/login/";
    }
    @FindBy(css = "div.swatch-option.text")  // Selectează mărimea
    private WebElementFacade sizeOption;

    @FindBy(css = "div.swatch-option.color")  // Selectează culoarea
    private WebElementFacade colorOption;

    @FindBy(id = "product-addtocart-button")  // Buton "Add to Cart"
    private WebElementFacade addToCartButton;

    @FindBy(css = ".message-success.success.message")  // Mesaj de succes
    private WebElementFacade successMessage;

    public void select_size_and_color() {
        sizeOption.click();
        colorOption.click();
    }

    public void add_to_cart() {
        addToCartButton.click();
    }

    public boolean is_product_added_successfully() {
        return successMessage.waitUntilVisible().isDisplayed();
    }

    @FindBy(css = ".customer-menu .authorization-link a")  // Link de logout din meniu
    private WebElementFacade logoutLink;

    public void logout() {
        logoutLink.waitUntilVisible().click();
    }
}