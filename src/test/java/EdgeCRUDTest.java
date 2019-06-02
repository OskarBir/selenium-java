import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeCRUDTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        WebDriverManager.edgedriver().setup();
        System.setProperty("webdriver.edge.driver","D:\\Pobrane\\MicrosoftWebDriver.exe");
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setDefaultPage() {
        driver.get("http://a.testaddressbook.com/sign_in");
        driver.manage().window().maximize();
        WebElement email = driver.findElement(By.id("session_email"));
        WebElement password = driver.findElement(By.name("session[password]"));
        WebElement saveButton = driver.findElement(By.name("commit"));
        email.sendKeys("oskar@gmail.com");
        password.sendKeys("qwerty");
        saveButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Address Book"));
    }

    @Test
    public void addressesTitlePageTest()
    {
        driver.navigate().to("http://a.testaddressbook.com/addresses");
        assertEquals("Address Book", driver.getTitle());
    }

    @Test
    public void newAddressTest()
    {
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.id("address_first_name"));
        WebElement lastName = driver.findElement(By.id("address_last_name"));
        WebElement address = driver.findElement(By.id("address_street_address"));
        WebElement city = driver.findElement(By.id("address_city"));
        Select state = new Select(driver.findElement(By.id("address_state")));
        WebElement zipcode = driver.findElement(By.id("address_zip_code"));
        WebElement countryUs = driver.findElement(By.id("address_country_us"));
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
        birthday.sendKeys("1996-02-28");
        age.sendKeys("24");
        website.sendKeys("http://www.wp.pl");
        phone.sendKeys("555888646");
        interestClimb.click();
        interestDance.click();
        interestRead.click();
        note.sendKeys("XD");
        saveButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-test='notice']")));
        assertTrue(driver.getPageSource().contains("Address was successfully created"));
    }

    @Test
    public void firstNameLastNameAddressCityZipCodeBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error_explanation")));
        assertTrue(driver.getPageSource().contains("<ul>\n" +
                "        <li>First name can't be blank</li>\n" +
                "        <li>Last name can't be blank</li>\n" +
                "        <li>Address1 can't be blank</li>\n" +
                "        <li>City can't be blank</li>\n" +
                "        <li>Zip code can't be blank</li>\n" +
                "      </ul>"));
    }

    @Test
    public void firstNameNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement firstName = driver.findElement(By.id("address_first_name"));
        firstName.sendKeys("Imię");
        firstName.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("First name can't be blank"));
    }

    @Test
    public void lastNameNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement lastName = driver.findElement(By.id("address_last_name"));
        lastName.sendKeys("Nazwisko");
        lastName.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("Last name can't be blank"));

    }

    @Test
    public void addressNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement address = driver.findElement(By.id("address_street_address"));
        address.sendKeys("ul.Grunwaldzka 12/14");
        address.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("Address1 can't be blank"));

    }

    @Test
    public void cityNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement city = driver.findElement(By.id("address_city"));
        city.sendKeys("Gdańsk");
        city.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("City can't be blank"));

    }

    @Test
    public void zipcodeNotBlankTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        WebElement saveButton = driver.findElement(By.name("commit"));
        WebElement zipcode = driver.findElement(By.id("address_zip_code"));
        zipcode.sendKeys("80-404");
        zipcode.click();
        saveButton.click();
        assertFalse(driver.getPageSource().contains("Zip code can't be blank"));

    }

    @Test
    public void addCorrectStateTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement saveButton = driver.findElement(By.name("commit"));
        Select state = new Select(driver.findElement(By.id("address_state")));
        state.selectByVisibleText("Hawaii");
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='state']"));
        assertEquals("HI", field.getText());

    }

    @Test
    public void editStateTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement saveButton = driver.findElement(By.name("commit"));
        Select state = new Select(driver.findElement(By.id("address_state")));
        state.selectByVisibleText("Hawaii");
        saveButton.click();
        driver.findElement(By.xpath("//a[@data-test='edit']")).click();
        Select editState = new Select(driver.findElement(By.id("address_state")));
        editState.selectByVisibleText("Ohio");
        WebElement updateButton = driver.findElement(By.name("commit"));
        updateButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='state']"));
        assertEquals("OH", field.getText());

    }

    @Test
    public void addCorrectCountryTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement countryUs = driver.findElement(By.id("address_country_us"));
        WebElement countryCa = driver.findElement(By.id("address_country_canada"));
        countryUs.click();
        countryCa.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='country']"));
        assertEquals("canada", field.getText());

    }

    @Test
    public void editCountryTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement countryCa = driver.findElement(By.id("address_country_canada"));
        countryCa.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.findElement(By.xpath("//a[@data-test='edit']")).click();
        WebElement countryUs = driver.findElement(By.id("address_country_us"));
        countryUs.click();
        WebElement updateButton = driver.findElement(By.name("commit"));
        updateButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='country']"));
        assertEquals("us", field.getText());
    }

    @Test
    public void addColorTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        JavascriptExecutor jse=(JavascriptExecutor)driver;
        jse.executeScript("document.getElementById('address_color').value='#19d826'");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='color']"));
        assertEquals(Color.fromString("#19d826").asRgb(), field.getCssValue("background-color"));
    }

    @Test
    public void addCorrectWebsiteTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement website = driver.findElement(By.id("address_website"));
        website.sendKeys("http://wykop.pl");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='website']"));
        assertEquals("http://wykop.pl", field.getText());
    }

    @Test
    public void editWebsiteTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement website = driver.findElement(By.id("address_website"));
        website.sendKeys("http://wykop.pl");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.findElement(By.xpath("//a[@data-test='edit']")).click();
        WebElement website2 = driver.findElement(By.id("address_website"));
        website2.clear();
        website2.sendKeys("http://wp.pl");
        WebElement updateButton = driver.findElement(By.name("commit"));
        updateButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='website']"));
        assertEquals("http://wp.pl", field.getText());
    }

    @Test
    public void wrongWebsiteTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement website = driver.findElement(By.id("address_website"));
        website.sendKeys("nielink");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        assertEquals("Należy wpisać poprawny adres URL", website.getAttribute("validationMessage"));
    }


    @Test
    public void addClimbingTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement interestClimb = driver.findElement(By.id("address_interest_climb"));
        interestClimb.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='interest_climb']"));
        assertEquals("Yes", field.getText());
    }

    @Test
    public void editClimbingTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement interestClimb = driver.findElement(By.id("address_interest_climb"));
        interestClimb.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.findElement(By.xpath("//a[@data-test='edit']")).click();
        WebElement interestClimb2 = driver.findElement(By.id("address_interest_climb"));
        interestClimb2.click();
        WebElement updateButton = driver.findElement(By.name("commit"));
        updateButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='interest_climb']"));
        assertEquals("No", field.getText());
    }

    @Test
    public void addDancingTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement interestDance = driver.findElement(By.id("address_interest_dance"));
        interestDance.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='interest_dance']"));
        assertEquals("Yes", field.getText());

    }

    @Test
    public void editDancingTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement interestDance = driver.findElement(By.id("address_interest_dance"));
        interestDance.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.findElement(By.xpath("//a[@data-test='edit']")).click();
        WebElement interestDance2 = driver.findElement(By.id("address_interest_dance"));
        interestDance2.click();
        WebElement updateButton = driver.findElement(By.name("commit"));
        updateButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='interest_dance']"));
        assertEquals("No", field.getText());
    }

    @Test
    public void addReadingTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement interestRead= driver.findElement(By.id("address_interest_read"));
        interestRead.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='interest_read']"));
        assertEquals("Yes", field.getText());
    }

    @Test
    public void editReadingTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement interestRead= driver.findElement(By.id("address_interest_read"));
        interestRead.click();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.findElement(By.xpath("//a[@data-test='edit']")).click();
        WebElement interestRead2 = driver.findElement(By.id("address_interest_read"));
        interestRead2.click();
        WebElement updateButton = driver.findElement(By.name("commit"));
        updateButton.click();
        WebElement field = driver.findElement(By.xpath("//span[@data-test='interest_read']"));
        assertEquals("No", field.getText());
    }

    @Test
    public void deleteAddressTest() {
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.navigate().to("http://a.testaddressbook.com/addresses");
        driver.findElement(By.xpath("//a[contains(text(), 'Destroy')][1]")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-test='notice']")));
        assertTrue(driver.getPageSource().contains("Address was successfully destroyed."));

    }

    @Test
    public void cancelDeleteAddressTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        driver.navigate().to("http://a.testaddressbook.com/addresses");
        driver.findElement(By.xpath("//a[contains(text(), 'Destroy')][1]")).click();
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        assertFalse(driver.getPageSource().contains("Address was successfully destroyed."));
    }

    @Test
    public void addPicTest(){
        driver.navigate().to("http://a.testaddressbook.com/addresses/new");
        createBasicAddress();
        WebElement addPic = driver.findElement(By.id("address_picture"));
        addPic.sendKeys("C:\\Users\\Oskar\\Desktop\\projekt3-OskarBir\\zdj.jpg");
        WebElement saveButton = driver.findElement(By.name("commit"));
        saveButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-test='notice']")));
        assertTrue(driver.getPageSource().contains("Address was successfully created."));
    }

    public static void createBasicAddress(){
        WebElement firstName = driver.findElement(By.id("address_first_name"));
        WebElement lastName = driver.findElement(By.id("address_last_name"));
        WebElement address = driver.findElement(By.id("address_street_address"));
        WebElement city = driver.findElement(By.id("address_city"));
        WebElement zipcode = driver.findElement(By.id("address_zip_code"));
        firstName.sendKeys("Imię");
        lastName.sendKeys("Nazwisko");
        address.sendKeys("Adres");
        city.sendKeys("Miasto");
        zipcode.sendKeys("kodpocztowy");
    }

    @AfterEach
    public void logOut(){
        driver.findElement(By.xpath(".//a[contains(@href,'/sign_out')]")).click();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
