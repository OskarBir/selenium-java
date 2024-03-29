import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ChromeLoginTest
{

    private static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setDefaultPage() throws Exception {
        driver.get("http://thedemosite.co.uk/");
    }

    @Test
    public void addUserTitleTest()
    {
        driver.findElement(By.xpath("//a[@href='addauser.php']")).click();
        assertEquals("Add a user - FREE PHP code and SQL", driver.getTitle());
    }

    @Test
    public void addUserUsernameTextBoxIsPresentTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        assertNotNull(usernameTextBox);
    }

    @Test
    public void addUserPasswordTextBoxIsPresentTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        assertNotNull(passwordTextBox);
    }

    @Test
    public void addUserSaveButtonIsPresentTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement saveButton = driver.findElement(By.name("FormsButton2"));
        assertNotNull(saveButton);
    }

    @Test
    public void loginTitleTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/login.php");
        assertEquals("Login example page to test the PHP MySQL online system", driver.getTitle());
    }

    @Test
    public void loginNoLoginAttempted()
    {
        driver.navigate().to("http://thedemosite.co.uk/login.php");
        assertTrue(driver.getPageSource().contains("**No login attempted**"));
    }

    @Test
    public void loginUsernameTextBoxIsPresentTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/login.php");
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        assertNotNull(usernameTextBox);
    }

    @Test
    public void loginPasswordTextBoxIsPresentTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/login.php");
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        assertNotNull(passwordTextBox);
    }

    @Test
    public void loginTestLoginButtonIsPresentTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/login.php");
        WebElement saveButton = driver.findElement(By.name("FormsButton2"));
        assertNotNull(saveButton);
    }

    @Test
    public void addUserUsernameTooShortTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        WebElement saveButton = driver.findElement(By.name("FormsButton2"));
        usernameTextBox.sendKeys("abc");
        passwordTextBox.sendKeys("qwerty");
        saveButton.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        assertEquals("Username too short.  The username must be at least 4 characters in length.", alertText);
    }

        @Test
    public void addUserPasswordTooShortTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        WebElement saveButton = driver.findElement(By.name("FormsButton2"));
        usernameTextBox.sendKeys("abcd");
        passwordTextBox.sendKeys("qw");
        saveButton.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        assertEquals("Password too short.  The password must be at least 4 characters in length.", alertText);
    }

        @Test
    public void addUserTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        WebElement saveButton = driver.findElement(By.name("FormsButton2"));
        usernameTextBox.sendKeys("abcd");
        passwordTextBox.sendKeys("qwerty");
        saveButton.click();
        assertTrue(driver.getPageSource().contains("<b>The username:</b> abcd<br><b>The password:</b> qwerty"));
    }

        @Test
    public void loginTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/addauser.php");
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        WebElement saveButton = driver.findElement(By.name("FormsButton2"));
        usernameTextBox.sendKeys("abcd");
        passwordTextBox.sendKeys("qwerty");
        saveButton.click();
        driver.findElement(By.xpath("//a[@href='login.php']")).click();
        WebElement usernameTextBoxLogin = driver.findElement(By.name("username"));
        WebElement passwordTextBoxLogin = driver.findElement(By.name("password"));
        WebElement saveButtonLogin = driver.findElement(By.name("FormsButton2"));
        usernameTextBoxLogin.sendKeys("abcd");
        passwordTextBoxLogin.sendKeys("qwerty");
        saveButtonLogin.click();
        assertTrue(driver.getPageSource().contains("**Successful Login**"));
    }

        @Test
    public void failedLoginTest()
    {
        driver.navigate().to("http://thedemosite.co.uk/login.php");
        WebElement usernameTextBoxLogin = driver.findElement(By.name("username"));
        WebElement passwordTextBoxLogin = driver.findElement(By.name("password"));
        WebElement saveButtonLogin = driver.findElement(By.name("FormsButton2"));
        usernameTextBoxLogin.sendKeys("abcde");
        passwordTextBoxLogin.sendKeys("qwerty");
        saveButtonLogin.click();
        assertTrue(driver.getPageSource().contains("**Failed Login**"));
    }

    @Test
    public void searchOneResultOnGoogleTest()
    {
        driver.get("http://google.com");
        WebElement searchbar = driver.findElement(By.name("q"));
        searchbar.sendKeys("inurl:What-Google-searches-only-give-one-result-at-the-time-of-writing-your-answer");
        searchbar.sendKeys(Keys.RETURN);
        assertTrue(driver.getPageSource().contains("This question a superset of the sport called Googlewhacking. Ironically, the moment you post the words or phrase that form a single Google"));
    }

    @Test
    public void searchTwoResultOnGoogleTest()
    {
        driver.get("http://google.com");
        WebElement searchbar = driver.findElement(By.name("q"));
        searchbar.sendKeys("BCICNSFD");
        searchbar.sendKeys(Keys.RETURN);
        assertTrue(driver.getPageSource().contains("Około 2 wyników"));
    }

    @Test
    public void searchNotFoundResultOnGoogleTest()
    {
        driver.get("http://google.com");
        WebElement searchbar = driver.findElement(By.name("q"));
        searchbar.sendKeys("enfoenpfonseofsofnsoenfsoenfsoenfsoenf");
        searchbar.sendKeys(Keys.RETURN);
        assertTrue(driver.getPageSource().contains("Podana fraza - <em>enfoenpfonseofsofnsoenfsoenfsoenfsoenf</em> - nie została odnaleziona."));
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }
}