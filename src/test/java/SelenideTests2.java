import com.codeborne.selenide.*;
import com.codeborne.selenide.testng.ScreenShooter;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.codeborne.selenide.testng.SoftAsserts;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;



@Listeners({SoftAsserts.class, ScreenShooter.class})
public class SelenideTests2 {

    public SelenideTests2() {
        baseUrl = "https://demoqa.com/books";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        browserCapabilities = options;
        browserSize = null;
        timeout = 20000;
        holdBrowserOpen = false;
        reportsFolder = "src/main/resources/Reports";
        screenshots=true;
        reopenBrowserOnFail = true;
        fastSetValue=true;

        fileDownload= FileDownloadMode.HTTPGET;
        downloadsFolder="src/main/resources/images";
        savePageSource = false;
    }

    @Test
    public void firstTest() {
        open(baseUrl);

        SoftAssert softAssert = new SoftAssert();

        ElementsCollection books = $(".rt-table")
                .find(".rt-tbody")
                .findAll(".rt-tr-group")
                .filterBy(text("O'Reilly Media"))
                .filterBy(text("Javascript"));

        softAssert.assertEquals(books.size(), 10);

        String firstRowTitle = books.get(0).$(".rt-td:nth-child(2)").getText();

        softAssert.assertEquals(firstRowTitle, "Learning JavaScript Design Patterns");

        books.stream().forEach(el -> {
            el.$(".rt-td:nth-child(2) a").scrollTo().click();
            $("#addNewRecordButton").scrollTo().click();
        });


        softAssert.assertAll();
    }


    @Test
    public void secondTest() {
        open(baseUrl);

        $(".rt-table")
                .find(".rt-tbody")
                .findAll(".rt-tr-group")
                .filterBy(text("O'Reilly Media"))
                .filterBy(text("Javascript"))
                .stream().forEach(el -> {
                    el.$(By.xpath(".//img")).shouldHave(attribute("src"));
                });


    }


}
