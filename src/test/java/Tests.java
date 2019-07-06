import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class Tests {
    private static WebDriver driver;
    private static WebElement element;
    private static TestSteps testSteps;


    @BeforeAll
    public void setup() throws TimeoutException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        testSteps = new TestSteps(driver);
    }

    @Test
    public void userCanSearchOnlyForColleague() {
        String userName;
        String companyId = "123";
        testSteps.openLoginPage();
        //Tom принадлежит компании 123
        testSteps.loginUser("Tom", "password");
        //Jack принадлежит компании 123
        userName = "Jack";
        testSteps.searchForUser(userName);
        //1)
        element = driver.findElement(By.id("name"));
        Assert.assertEquals(userName, element.getText());
        //2)
        element = driver.findElement(By.id("company"));
        String company = SomeMethodes.getCompanyNameById(companyId);
        Assert.assertEquals(company, element.getText());
        //Bob не принадлежит компании 123
        userName = "Bob";
        String curUrl = driver.getCurrentUrl();
        testSteps.searchForUser(userName);
        Assert.assertEquals(curUrl, driver.getCurrentUrl());
    }

    @Test
    public void gettingRightUrl(){
        String userName = "Tom";
        String companyId = "123";
        testSteps.openUserPage(companyId, userName);
        //1)
        element = driver.findElement(By.id("name"));
        Assert.assertEquals(userName, element.getText());
        //2)
        element = driver.findElement(By.id("company"));
        String company = SomeMethodes.getCompanyNameById(companyId);
        Assert.assertEquals(company, element.getText());
        //3)
        String[] userInfo = SomeMethodes.getUserInfo(userName);
        for(String info: userInfo){
            Assert.assertTrue(driver.getPageSource().contains(info));
        }
        //4)
        String userPhotoLink = SomeMethodes.getUserPhotoByName(userName);
        element = driver.findElement(By.id("profile_photo_link"));
        Assert.assertEquals(userPhotoLink, element.getAttribute("href"));
    }

    @Test
    public void gettingWrongUrl(){
        testSteps.openUserPage("777", "Izergil");
        Assert.assertTrue(driver.getPageSource().contains("Такой компании не существует"));
        testSteps.openUserPage("123", "anon");
        Assert.assertTrue(driver.getPageSource().contains("Такого пользователя не существует"));
    }

    @AfterAll
    public void quit(){
        testSteps.quit();
    }

}
