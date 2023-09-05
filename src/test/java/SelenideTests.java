import com.codeborne.selenide.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideTests {
    public SelenideTests() {
        baseUrl = "http://the-internet.herokuapp.com";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null;
        timeout = 20000;
        holdBrowserOpen = false;
    }

    @Test
    public void checkBoxes() {
        open("/checkboxes");

        $("#checkboxes input:first-child").setSelected(true);

        $$("#checkboxes input").forEach(el -> el.shouldHave(type("checkbox")));

        // ესეც იმუშავებდა, მაგრამ ზემოთა კოდი უფრო შესაფერისია დავალების წესით:)
        //$$("#checkboxes input[type='checkbox']").shouldHave(size(2));
    }

    @Test
    public void dropDown() {
        open("/dropdown");

        SelenideElement dropDown = $("#dropdown");

        dropDown.getSelectedOption().shouldHave(text("Please select an option"));

        dropDown.selectOption("Option 2");

        dropDown.getSelectedOption().shouldHave(text("Option 2"));
    }

    @Test
    public void textBox() {
        open("https://demoqa.com/text-box");

        //by id
        $(byId("userName")).setValue("giorgi gengashvili");

        //by attribute
        $(byAttribute("type","email")).setValue("giorgi.gengashvili0@gmail.com");

        // by placeholder
        $("textarea[placeholder = 'Current Address']").setValue("tbilisi, georgia");

        // by index
        $("textarea",1).setValue("tbilisi, georgia");

        // by text
        $(byText("Submit")).scrollTo().click();

        List<String> excpectedResult = List.of(
                "Name:giorgi gengashvili",
                "Email:giorgi.gengashvili0@gmail.com",
                "Current Address :tbilisi, georgia",
                "Permananet Address :tbilisi, georgia"
        );

        ElementsCollection actualResult =  $$("#output p.mb-1");

        actualResult.shouldHave(exactTexts(excpectedResult));

    }
}
