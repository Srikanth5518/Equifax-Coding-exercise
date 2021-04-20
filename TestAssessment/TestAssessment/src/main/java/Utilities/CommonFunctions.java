package Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Constants.Constants;

public class CommonFunctions extends DriverIntialization {

	public static WebDriverWait drvWait;
	public static Actions actions;
	public static JavascriptExecutor jscript;
	public static ClassLoader basedir;
	public static int TimeoutValue = Constants.EXPLICITWAIT;
	public static Properties ppty;
	public static Properties dataSourcePpty;
	public static int count = 0;

	public static Properties getObjDetails() throws IOException {
		ppty = new Properties();
		ppty.load(loadFileAsStream("/config.properties"));
		return ppty;
	}

	public static Properties testDataProperties() throws IOException {
		dataSourcePpty = new Properties();
		dataSourcePpty.load(loadFileAsStream("/dataSource.properties"));
		return dataSourcePpty;
	}

	public static void loadBaseURL() throws Exception {
		getDriver().get("https://amazon.com");
		Thread.sleep(3000);
	}

	public static void javascriptInit() {
		jscript = ((JavascriptExecutor) getDriver());
	}

	public static void actionInit() {
		actions = new Actions(getDriver());
	}

	public static void webDriverWaitInit() {
		drvWait = new WebDriverWait(getDriver(), TimeoutValue * 3);
	}

	public static void drawHighlight(WebElement element) {
		jscript.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
				element);
	}

	public static void loadClassLoader() {
		basedir = CommonFunctions.class.getClassLoader();
	}

	public static InputStream loadFileAsStream(String file) {
		return CommonFunctions.class.getResourceAsStream(file);
	}

	public static String getTargetFilePath(String filename) {
		String path = basedir.getResource(filename).getPath();
		path = path.indexOf("/") == 0 ? path.substring(1, path.length()) : path;
		return path;
	}

	public static byte[] embedScreenshot() {
		byte[] srcBytes = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
		return srcBytes;
	}

	public static void javascriptClick(WebElement ele) {
		jscript.executeScript("arguments[0].click();", ele);
	}

	public static void expWaitElementToBeClickable(WebElement ele) {
		int count = 0;
		try {
			drvWait.until(ExpectedConditions.elementToBeClickable(ele));

		} catch (ElementClickInterceptedException e) {
			count++;
			if (count < 3) {
				expWaitElementToBeClickable(ele);
			}
			try {
				javascriptClick(ele);
			} catch (Exception ex) {
				try {
					throw new Exception("Navigating/Clicking is Failed");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}