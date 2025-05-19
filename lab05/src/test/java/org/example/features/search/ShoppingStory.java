package org.example.features.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.example.steps.serenity.LoginSteps;
import org.example.steps.serenity.ProductSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RunWith(SerenityRunner.class)
public class ShoppingStory {

    @Managed
    WebDriver driver;

    @Steps
    LoginSteps loginSteps;

    @Steps
    ProductSteps productSteps;

    public Map<String, Map<String, String>> loadCredentials() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("src/test/resources/credentials.json"), Map.class);
    }

    @Test
    public void user_should_be_able_to_login_add_product_and_logout() throws IOException {
        Map<String, Map<String, String>> creds = loadCredentials();
        String user = creds.get("valid").get("email");
        String pass = creds.get("valid").get("password");

        loginSteps.opens_login_page();
        loginSteps.enters_credentials(user, pass);
        loginSteps.submits_login();

        productSteps.open_product_page("https://magento.softwaretestingboard.com/neve-studio-dance-jacket.html");
        productSteps.add_product_to_cart();
        productSteps.should_see_product_added();

        productSteps.perform_logout();
    }
}
