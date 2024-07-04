package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        js = (JavascriptExecutor) driver;

    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01(){

        try {


            driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");

            Thread.sleep(2000);

            WebElement firstTestBox = driver.findElement(By.xpath("//input[@type='text']"));
            firstTestBox.sendKeys("Crio Learner");
            Thread.sleep(2000);

            WebElement practicingAutomation = driver.findElement(By.xpath("//textarea[@aria-label='Your answer']"));
            practicingAutomation.sendKeys("I want to be the best QA Engineer! " + System.currentTimeMillis());
//            Thread.sleep(2000);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='0 - 2']"))).click();


            driver.findElement(By.xpath("//span[text()='0 - 2']")).click();
           Thread.sleep(1000);

           WebElement test = driver.findElement(By.xpath("//span[text()='Java']"));

            js.executeScript("arguments[0].scrollIntoView();", test);

            test.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[text()='Selenium']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[text()='TestNG']")).click();
            Thread.sleep(1000);



            WebElement addressed = driver.findElement(By.xpath("//div[@jscontroller='sWGJ4b']/div/div[@role='listbox']"));
            addressed.click();
            Thread.sleep(5000);

            List<WebElement> tests = driver.findElements(By.xpath("//div[@role='option']"));
            for (WebElement e : tests){
                if (e.getAttribute("innerHTML").contains("Ms")){
                    e.click();
                    break;
                }
            }
              Thread.sleep(5000);

            WebElement date= driver.findElement(By.xpath("//input[@type='date']"));
            LocalDate currentDate = LocalDate.now();
            LocalDate dateSevenDaysAgo = currentDate.minusDays(7);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = dateSevenDaysAgo.format(formatter);
            date.sendKeys(formattedDate);
            Thread.sleep(5000);
            System.out.println("Current Date Inputed");


            List<WebElement> timeInputs= driver.findElements(By.xpath("//input[@role='combobox']"));
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("h");
            DateTimeFormatter minFormatter= DateTimeFormatter.ofPattern("mm");
            timeInputs.get(0).sendKeys( currentTime.format(timeFormatter));
            timeInputs.get(1).sendKeys( currentTime.format(minFormatter));
            Thread.sleep(5000);
            System.out.println("Current Time Inputed");

            WebElement submit = driver.findElement(By.xpath("//span[text()='Submit']"));
            submit.click();
            Thread.sleep(5000);
            System.out.println("Submit Click");


            WebElement successfulMessage = driver.findElement(By.xpath("//div[text()='Thanks for your response, Automation Wizard!']"));
            String successMsg =successfulMessage.getText().toLowerCase();
            boolean messageContains = successMsg.contains("Thanks for your response, Automation Wizard!".toLowerCase());

            assert messageContains;
        }catch (Exception e){
            e.printStackTrace();
           assert false;
        }





    }
}