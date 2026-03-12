package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options; // הייבוא החדש ל-Options
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SettingsTest {

    private AndroidDriver driver;


    @AndroidFindBy(id = "com.android.settings:id/search_action_bar")
    private WebElement searchBar;

    @AndroidFindBy(id = "com.android.settings:id/collapsing_toolbar")
    private WebElement collapsingToolbarHeader;


    public enum SettingsMenu {
        NETWORK_AND_INTERNET("Network & internet"),
        CONNECTED_DEVICES("Connected devices"),
        APPS("Apps"),
        NOTIFICATIONS("Notifications"),
        BATTERY("Battery"),
        DISPLAY("Display"),
        SOUND("Sound"),
        STORAGE("Storage"),
        PRIVACY("Privacy"),
        LOCATION("Location"),
        SECURITY("Security"),
        ACCOUNTS("Accounts"),
        ACCESSIBILITY("Accessibility"),
        DIGITAL_WELLBEING("Digital Wellbeing"),
        GOOGLE("Google"),
        SYSTEM("System"),
        ABOUT_EMULATED_DEVICE("About emulated device");

        private final String menuText;

        SettingsMenu(String menuText) {
            this.menuText = menuText;
        }

        public String getMenuText() {
            return menuText;
        }
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setPlatformVersion("16");
        options.setDeviceName("emulator-5554");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.android.settings");
        options.setAppActivity("com.android.settings.Settings");
        options.setNoReset(false);
        options.setFullReset(false);

        URL remoteUrl = new URL("http://127.0.0.1:4723/");
        driver = new AndroidDriver(remoteUrl, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Test
    public void testSettingsNavigation() throws InterruptedException {
        assertNotNull(searchBar, "אפליקציית ההגדרות לא עלתה");

        String systemText = SettingsMenu.SYSTEM.getMenuText();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + systemText + "\"))"
        )).click();

        assertNotNull(collapsingToolbarHeader, "לא הצלחנו להיכנס לתפריט System");

        String aboutText = SettingsMenu.ABOUT_EMULATED_DEVICE.getMenuText();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + aboutText + "\"))"
        )).click();

        assertNotNull(collapsingToolbarHeader, "דף האודות לא עלה בהצלחה");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}