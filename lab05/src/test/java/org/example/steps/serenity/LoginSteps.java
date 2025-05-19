package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.example.pages.LoginPage;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class LoginSteps {

    LoginPage loginPage;

    @Step
    public void opens_login_page() {
        loginPage.open();
    }

    @Step
    public void enters_credentials(String email, String password) {
        loginPage.enter_email(email);
        loginPage.enter_password(password);
    }

    @Step
    public void submits_login() {
        loginPage.click_login();
    }
    @Step
    public void should_see_login_error() {
        Assert.assertTrue(loginPage.is_login_error_displayed());
    }


    @Step
    public void should_see_user_logged_in() {
        assertTrue(loginPage.is_logged_in_successfully());
    }
}