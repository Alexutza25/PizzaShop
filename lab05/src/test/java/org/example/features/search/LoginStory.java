package org.example.features.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.example.steps.serenity.LoginSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RunWith(SerenityRunner.class)
public class LoginStory {

    @Managed
    WebDriver driver;

    @Steps
    LoginSteps loginSteps;

    public Map<String, Map<String, String>> loadCredentials() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("src/test/resources/credentials.json"), Map.class);
    }

    @Test
    public void login_with_valid_magento_credentials() throws IOException {
        Map<String, Map<String, String>> creds = loadCredentials();
        String user = creds.get("valid").get("email");
        String pass = creds.get("valid").get("password");

        loginSteps.opens_login_page();
        loginSteps.enters_credentials(user, pass);
        loginSteps.submits_login();
        loginSteps.should_see_user_logged_in();
    }
    @Test
    public void login_with_invalid_credentials() throws IOException {
        Map<String, Map<String, String>> creds = loadCredentials();
        String user = creds.get("invalid").get("email");
        String pass = creds.get("invalid").get("password");

        loginSteps.opens_login_page();
        loginSteps.enters_credentials(user, pass);
        loginSteps.submits_login();
        loginSteps.should_see_login_error();
    }

}