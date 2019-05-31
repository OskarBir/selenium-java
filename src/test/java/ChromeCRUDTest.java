import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChromeCRUDTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setDefaultPage() throws Exception {
        driver.get("http://a.testaddressbook.com/sign_in");
        driver.manage().window().maximize();
        WebElement email = driver.findElement(By.id("session_email"));
        WebElement password = driver.findElement(By.name("session[password]"));
        WebElement saveButton = driver.findElement(By.name("commit"));
        email.sendKeys("oskar@gmail.com");
        password.sendKeys("qwerty");
        saveButton.click();
    }

    @Test
    public void AddressesTitlePageTest()
    {
        driver.navigate().to("http://a.testaddressbook.com/addresses");
        assertEquals("Address Book", driver.getTitle());
    }

    @Test
    public void NewAddressTest()
    {
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.id("address_first_name"));
        WebElement lastName = driver.findElement(By.id("address_last_name"));
        WebElement address = driver.findElement(By.id("address_street_address"));
        WebElement city = driver.findElement(By.id("address_city"));
        Select state = new Select(driver.findElement(By.id("address_state")));
        WebElement zipcode = driver.findElement(By.id("address_zip_code"));
        WebElement countryUs = driver.findElement(By.id("address_country_us"));
        WebElement countryCanada = driver.findElement(By.id("address_country_canada"));
        WebElement birthday = driver.findElement(By.id("address_birthday"));
        WebElement age = driver.findElement(By.id("address_age"));
        WebElement website = driver.findElement(By.id("address_website"));
        WebElement phone = driver.findElement(By.id("address_phone"));
        WebElement interestClimb = driver.findElement(By.id("address_interest_climb"));
        WebElement interestDance= driver.findElement(By.id("address_interest_dance"));
        WebElement interestRead= driver.findElement(By.id("address_interest_read"));
        WebElement note= driver.findElement(By.id("address_note"));
        WebElement saveButton = driver.findElement(By.name("commit"));
        firstName.sendKeys("Imię");
        lastName.sendKeys("Nazwisko");
        address.sendKeys("ul.Grunwaldzka 12/14");
        city.sendKeys("Gdańsk");
        state.selectByVisibleText("Texas");
        zipcode.sendKeys("80-404");
        countryUs.click();
        birthday.sendKeys("28021996");
        age.sendKeys("24");
        website.sendKeys("http://www.wp.pl");
        phone.sendKeys("555888646");
        interestClimb.click();
        interestDance.click();
        interestRead.click();
        note.sendKeys("XD");
        saveButton.click();
        assertTrue(driver.getPageSource().contains("Address was successfully created"));
    }

    @Test
    public void FirstNameLastNameAddressCityZipCodeBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        assertTrue(driver.getPageSource().contains("<ul>\n" +
                "        <li>First name can't be blank</li>\n" +
                "        <li>Last name can't be blank</li>\n" +
                "        <li>Address1 can't be blank</li>\n" +
                "        <li>City can't be blank</li>\n" +
                "        <li>Zip code can't be blank</li>\n" +
                "      </ul>"));
    }

    @Test
    public void FirstNameNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement firstName = driver.findElement(By.id("address_first_name"));
        firstName.sendKeys("Imię");
        firstName.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("First name can't be blank"));
    }

    @Test
    public void LastNameNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement lastName = driver.findElement(By.id("address_last_name"));
        lastName.sendKeys("Nazwisko");
        lastName.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("Last name can't be blank"));

    }

    @Test
    public void AdressNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement address = driver.findElement(By.id("address_street_address"));
        address.sendKeys("ul.Grunwaldzka 12/14");
        address.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("Address1 can't be blank"));

    }

    @Test
    public void CityNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement city = driver.findElement(By.id("address_city"));
        city.sendKeys("Gdańsk");
        city.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("City can't be blank"));

    }

    @Test
    public void ZipcodeNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement zipcode = driver.findElement(By.id("address_zip_code"));
        zipcode.sendKeys("80-404");
        zipcode.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("Zip code can't be blank"));

    }

    @AfterEach
    public void LogOut(){
        driver.findElement(By.xpath(".//a[contains(@href,'/sign_out')]")).click();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
