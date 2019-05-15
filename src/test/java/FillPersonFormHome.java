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

import static org.junit.Assert.*;

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


    // my function which includes delays after steps
    private void waitt(int timee) {
        try {Thread.sleep(timee);}
        catch (InterruptedException ex) {Thread.currentThread().interrupt();}
    }



    // checks if form has been successfully submitted -> "Successfully submitted!"
    @Test
    public void fillPersonForm() {

        driver.get("https://katalon-test.s3.amazonaws.com/demo-aut/dist/html/form.html");

        driver.findElement(By.id("first-name")).sendKeys("Karol");
        driver.findElement(By.id("last-name")).sendKeys("kowalski");
        List<WebElement> elements = driver.findElements(By.cssSelector(".radio-inline"));

//        // each for
//        for (WebElement element : elements) {
//            if (element.getText().equals("Female")) {
//                element.click();
//                break;
//            }
//        }

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().equals("Female")) {
                elements.get(i).click();
                break;
            }
        }


        // added tab to move to next edit box (and close calendar)
        driver.findElement(By.id("dob")).sendKeys("02/10/1995\t");
        waitt(3000);

        driver.findElement(By.id("address")).sendKeys("Dobra 54");
        driver.findElement(By.id("email")).sendKeys("kowalski@gmail.com");
        driver.findElement(By.id("password")).sendKeys("33333");
        driver.findElement(By.id("company")).sendKeys("Coders Lab");

        Select roleDropDown = new Select(driver.findElement(By.name("role")));
        roleDropDown.selectByVisibleText("Manager");

        Select jobDropdown = new Select(driver.findElement(By.name("expectation")));
        jobDropdown.selectByVisibleText("Good teamwork");

        driver.findElement(By.xpath("//label[text() = 'Read books']")).click();

        driver.findElement(By.id("comment")).sendKeys(("This is my script"));
        driver.findElement(By.id("submit")).click();

        assertEquals("Successfully submitted!", driver.findElement(By.id("submit-msg")).getText());

    }



    // checks if mandatory fields have been filled -> "This field is required."
    @Test
    public void checkErrors() {
        driver.get("https://katalon-test.s3.amazonaws.com/demo-aut/dist/html/form.html");
        driver.findElement(By.id("submit")).click();

//        List<String> listOfIds = getListOfIds();

        // Added to List missing Ids of mandatory fields
        List<String> listOfIds = Arrays.asList("first-name", "last-name", "gender", "dob", "address",
                "email", "password", "company");
        int counter = 0;

        for (String elementLocator : listOfIds) {
            elementLocator = elementLocator + "-error";
            assertEquals("This field is required.", driver.findElement(By.id(elementLocator)).getText());
            counter++;
        }
        waitt(2000);

        System.out.println(counter);
    }


    // checks if email entry is valid -> "Please enter a valid email address."
    @Test
    public void checkEmail() {

        driver.get("https://katalon-test.s3.amazonaws.com/demo-aut/dist/html/form.html");

        WebElement email = driver.findElement(By.id("email"));

        // correct email
        email.sendKeys("kowalski@gmail.com\t");
        try {assertTrue(driver.findElement(By.id("email-error")).equals(null));}
        catch (Exception e) {System.out.println("Upps. 'email-error' occurred although should not.");}


        // incorrect email - missing "@"
        waitt(2000);
        email.clear();
        email.sendKeys("kowalskigmail.com\t");
        try {assertEquals("Please enter a valid email address.", driver.findElement(By.id("email-error")).getText());}
        catch (Exception e) {System.out.println("Id not found");}


        // incorrect email - missing characters after "@"
        waitt(2000);
        email.clear();
        email.sendKeys("kowalski@\t");
        try {assertEquals("Please enter a valid email address.", driver.findElement(By.id("email-error")).getText());}
        catch (Exception e) {System.out.println("Id not found");}


        // incorrect email - missing characters before "@"
        waitt(2000);
        email.clear();
        email.sendKeys("@gmail.com\t");
        try {assertEquals("Please enter a valid email address.", driver.findElement(By.id("email-error")).getText());}
        catch (Exception e) {System.out.println("Id not found");}
        waitt(2000);

    }




//    // alternatively used in checkErrors()
//    private List<String> getListOfIds() {
//        List<String> listId = new ArrayList<>();
//        listId.add("first-name");
//        listId.add("last-name");
//        listId.add("gender");
//        return listId;
//    }


}


