package org.example.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.concurrent.TimeUnit;

public class ProductPage extends PageObject {

    @FindBy(css = "div.swatch-option.text")
    private WebElementFacade sizeOption;

    @FindBy(css = "div.swatch-option.color")
    private WebElementFacade colorOption;

    @FindBy(id = "product-addtocart-button")
    private WebElementFacade addToCartButton;

    @FindBy(css = ".message-success.success.message")
    private WebElementFacade successMessage;

    public void select_size_and_color() {
        sizeOption.click();
        colorOption.click();
    }

    public void add_to_cart() {
        addToCartButton.click();
    }

    public boolean is_product_added_successfully() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(successMessage);
        return successMessage.isDisplayed();
    }

}
