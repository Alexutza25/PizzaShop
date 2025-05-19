/*package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;
import org.example.pages.LoginPage;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartSteps {

    LoginPage loginPage;

    @Step
    public void adds_bulldog_to_cart() {
        loginPage.click_add_to_cart();
    }

    @Step
    public void should_see_list_price(String expectedPrice) {
        assertThat(loginPage.get_list_price(), containsString(expectedPrice));
    }

    @Step
    public void sets_quantity_to(String qty) {
        loginPage.set_quantity(qty);
        loginPage.update_cart();
    }

    @Step
    public void should_see_total_cost(String expectedCost) {
        assertThat(loginPage.get_total_cost(), containsString(expectedCost));
    }
}*/
