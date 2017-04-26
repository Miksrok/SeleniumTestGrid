package miksrok.selenium;

import miksrok.selenium.utils.EventHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Залізний Мозок on 24.04.2017.
 */
public abstract class BaseTest {

    private final String CHROME_PATH = "src\\test\\resources\\chromedriver.exe";
    private final String FIREFOX_PATH = "src\\test\\resources\\geckodriver.exe";
    private final String PHANTOM_PATH = "src\\test\\resources\\phantomjs.exe";

    private EventFiringWebDriver driver;
    protected GeneralActions generalActions;
    protected boolean isMobileTesting;


    private WebDriver getDriver(String browser) {
        switch (browser){
            case "chrome":{
                System.setProperty("webdriver.chrome.driver", CHROME_PATH);
                return new ChromeDriver();
            }
            case "phantom":{
                System.setProperty("phantomjs.binary.path", PHANTOM_PATH);
                return new PhantomJSDriver();
            }
            default:{
                System.setProperty("webdriver.gecko.driver", FIREFOX_PATH);
                return new FirefoxDriver();
            }
        }
    }

    private WebDriver getDriver(String browser, String url) {
        switch (browser){
            case "chrome":{
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                try {
                    return new RemoteWebDriver(new URL(url), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            case "phantom":{
                DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
                try {
                    return new RemoteWebDriver(new URL(url), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            case "android":{
                DesiredCapabilities capabilities = DesiredCapabilities.android();
                try {
                    return new RemoteWebDriver(new URL(url), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            default:{
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                try {
                    return new RemoteWebDriver(new URL(url), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("chrome") String browser, @Optional("") String gridUrl)  {

        driver = new EventFiringWebDriver(getDriver(browser, gridUrl));
        driver.register(new EventHandler());

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        if (!isMobileTesting(browser))
            driver.manage().window().maximize();

        isMobileTesting = isMobileTesting(browser);

        generalActions = new GeneralActions(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private boolean isMobileTesting(String browser) {
        switch (browser) {
            case "android":
                return true;
            case "firefox":
            case "chrome":
            case "phantomjs":
            default:
                return false;
        }
    }

}
