package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeskClockTest {
    private AndroidDriver driver;


    @AndroidFindBy(id = "com.google.android.deskclock:id/action_bar_root")
    private WebElement clockMainLayout;

    @AndroidFindBy(id = "com.google.android.deskclock:id/navigation_bar_item_small_label_view")
    private WebElement timerTab;

    @AndroidFindBy(id = "com.google.android.deskclock:id/timer_setup_digit_1")
    private WebElement digit1;

    @AndroidFindBy(id = "com.google.android.deskclock:id/timer_setup_digit_0")
    private WebElement digit0;

    @AndroidFindBy(id = "com.google.android.deskclock:id/timer_setup_time")
    private WebElement timeSetupDisplay;

    @AndroidFindBy(id = "com.google.android.deskclock:id/fab")
    private WebElement startButton;

    @AndroidFindBy(id = "com.google.android.deskclock:id/timer_text")
    private WebElement timerTextDisplay;


    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setPlatformVersion("16");
        options.setDeviceName("emulator-5554");
        options.setAppPackage("com.google.android.deskclock");
        options.setAppActivity("com.android.deskclock.DeskClock");
        options.setNoReset(false);
        options.setFullReset(false);

        URL remoteUrl = new URL("http://127.0.0.1:4723/");
        driver = new AndroidDriver(remoteUrl, options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Test
    public void testSetAndVerifyTimer() throws InterruptedException {

        assertNotNull(clockMainLayout, "האפליקציה לא עלתה בהצלחה");

        timerTab.click();

        digit1.click();
        digit0.click();
        digit0.click();
        digit0.click();

        String timeDisplayed = timeSetupDisplay.getText();
        assertEquals("10:00", timeDisplayed, "השעה לא נקלטה כראוי");

        startButton.click();

        Thread.sleep(30000);

        String updatedTimeDisplayed = timerTextDisplay.getText();
        assertEquals("09:30", updatedTimeDisplayed, "השעה לא התעדכנה כראוי לאחר 30 שניות");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}