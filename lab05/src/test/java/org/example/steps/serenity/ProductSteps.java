package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;
import org.example.pages.ProductPage;
import static org.junit.Assert.assertTrue;

public class ProductSteps {

    ProductPage productPage; // 👈 corect: injectăm ProductPage

    @Step
    public void open_product_page(String productUrl) {
        productPage.openUrl(productUrl);
    }

    @Step
    public void add_product_to_cart() {
        productPage.select_size_and_color();
        productPage.add_to_cart();
    }

    @Step
    public void should_see_product_added() {
        assertTrue(productPage.is_product_added_successfully());
    }

    @Step
    public void perform_logout() {
        productPage.openUrl("https://magento.softwaretestingboard.com/customer/account/logout/");
    }
}
