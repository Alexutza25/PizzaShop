package org.example.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.WhenPageOpens;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;

@DefaultUrl("https://dexonline.ro/")
public class DictionaryPage extends PageObject {

    @FindBy(id="searchField")
    private WebElementFacade searchTerms;

    @FindBy(id="searchButton")
    private WebElementFacade lookupButton;

    @WhenPageOpens
    public void waitUntilSearchFieldAppears() {
        element(By.id("searchField")).waitUntilVisible();
    }



    public void enter_keywords(String keyword) {
        System.out.println("DEBUG: Entering keywords");
        List<WebElement> inputs = getDriver().findElements(By.id("searchField"));
        System.out.println("DEBUG: Found " + inputs.size() + " inputs");
        if (inputs.size() == 0) {
            System.out.println("!!! Elementul NU există în DOM la momentul acesta !!!");
        } else {
            System.out.println(">>> Elementul există, dar posibil să NU fie vizibil/interactiv încă");
        }

        searchTerms.waitUntilVisible().type(keyword);
    }


    public void lookup_terms() {
        lookupButton.click();
    }

    public List<String> getDefinitions() {
        List<WebElementFacade> defs = findAll(By.cssSelector(".tree-def.html"));
        return defs.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}