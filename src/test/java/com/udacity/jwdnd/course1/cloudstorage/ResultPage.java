package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(id = "aResultSuccess")
    private WebElement ResultSuccess;

    private final JavascriptExecutor js;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    public void clickOk() {
        js.executeScript("arguments[0].click();", ResultSuccess);
    }

}
