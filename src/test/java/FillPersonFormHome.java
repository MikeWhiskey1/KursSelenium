import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FillPersonFormHome {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/miki/chromedriver_linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @After
    public void tearDown() {
        driver.quit();
    }



    @Test
    public void fillPersonForm() {

        driver.get("https://katalon-test.s3.amazonaws.com/demo-aut/dist/html/form.html");

        driver.findElement(By.id("first-name")).sendKeys("Karol");
        driver.findElement(By.id("last-name")).sendKeys("kowalski");
        List<WebElement> elements = driver.findElements(By.cssSelector(".radio-inline"));

//        for (WebElement element : elements) {
//            if (element.getText().equals("Female")) {
//                element.click();
//                break;
//            } // end if
//        } // end for

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().equals("Female")) {
                elements.get(i).click();
                break;
            } // enf if
        } // enf for

        driver.findElement(By.id("dob")).sendKeys("02/10/1995");
        driver.findElement(By.id("address")).sendKeys("Dobra 54");
        driver.findElement(By.id("email")).sendKeys("kowalski@gmail.com");
        driver.findElement(By.id("password")).sendKeys("33333");
        driver.findElement(By.id("company")).sendKeys("Coders Lab");

        Select roleDropDown = new Select(driver.findElement(By.name("role")));
        roleDropDown.selectByVisibleText("Manager");

        Select jobDropdown = new Select(driver.findElement(By.name("expectation")));
        jobDropdown.selectByVisibleText("Good teamwork");

        driver.findElement(By.xpath("//label[text() = 'Read books']")).click();

        driver.findElement(By.id("comment")).sendKeys(("To jest mój już nie pierwszy skrypt testowy"));
        driver.findElement(By.id("submit")).click();

        assertEquals("Successfully submitted!", driver.findElement(By.id("submit-msg")).getText());

    } // end fillPersonForm Test


    @Test
    public void checkErrors() {
        driver.get("https://katalon-test.s3.amazonaws.com/demo-aut/dist/html/form.html");
        driver.findElement(By.id("submit")).click();

//        List<String> listOfIds = getListOfIds();

        List<String> listOfIds = Arrays.asList("first-name", "last-name", "gender");
        int counter = 0;

        for (String elementLocator : listOfIds) {
            elementLocator = elementLocator + "-error";
            assertEquals("This field is required.", driver.findElement(By.id(elementLocator)).getText());
            counter++;
        }
        System.out.println(counter);
    }


//    private List<String> getListOfIds() {
//        List<String> listId = new ArrayList<>();
//        listId.add("first-name");
//        listId.add("last-name");
//        listId.add("gender");
//        return listId;
//    } // end checkErrors Test


} // end class FillPersonForm


