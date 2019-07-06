import org.openqa.selenium.*;
import ru.yandex.qatools.allure.annotations.Step;

public class TestSteps {
    private WebDriver driver;
    private static String mainURL;
    private static WebElement element;

    public TestSteps(WebDriver driver) {
        this.driver = driver;
        mainURL = "http://some_domain.com:8080";
    }

    @Step
    public void openUserPage(String companyId, String userName){
        driver.get(mainURL+"/company/"+companyId+"/users?name="+userName+"/");
    }

    @Step
    public void openMainPage() {
        driver.get(mainURL);
    }

    @Step
    public void openLoginPage(){
        driver.get(mainURL+"/login/");
    }

    @Step
    public void loginUser(String userName, String password) {
        element = driver.findElement(By.id("username"));
        element.sendKeys(userName);
        element = driver.findElement(By.id("password"));
        element.sendKeys(password);
        element = driver.findElement(By.id("loginbutton"));
        element.click();
    }

    @Step
    public void searchForUser(String userName) {
        element = driver.findElement(By.id("search"));
        element.sendKeys(userName);
        element.submit();
    }

    @Step
    public void quit() {
        driver.quit();
    }

}
