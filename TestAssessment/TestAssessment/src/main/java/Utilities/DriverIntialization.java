package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import Constants.Constants;
import cucumber.api.Scenario;

public class DriverIntialization {
	public static WebDriver driver;
	public static Properties ppty;
	public static Logger logger;
	public static Scenario messageHandler;
	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();

	public void intializeDriver(String drivernm) throws Exception {
		setWebDriver(driver, drivernm);
	}

	public static WebDriver getDriver() {
		return dr.get();
	}

	public void setWebDriver(WebDriver driver, String drivernm) throws IOException {
		CommonFunctions.loadClassLoader();
		logger = Logger.getLogger(DriverIntialization.class.getName());
		ppty = CommonFunctions.getObjDetails();

		switch (drivernm) {
		case "Chrome":
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				WebDriverManager.chromedriver().setup();
			} else {
				System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			}
			String downloadFilepath = Constants.DOWNLOADFILEPATH;
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			options.addArguments("--start-maximized");
			options.addArguments("window-size=1920,1080");
			options.setCapability("applicationCacheEnabled", false);
			options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			driver = new ChromeDriver(options);
		}
		dr.set(driver);
		maximiseWindow();
		setUpImplicitWait();
	}

	private void setUpImplicitWait() {

		getDriver().manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT, TimeUnit.SECONDS);
	}

	private void maximiseWindow() {

		getDriver().manage().window().maximize();
	}
}
