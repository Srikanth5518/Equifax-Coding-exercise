package PageFactoryElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.CommonFunctions;

public class AmazonPage extends CommonFunctions {

	@FindBy(xpath = "//input[@id= 'twotabsearchtextbox']")
	WebElement searchBar;

	@FindBy(xpath = "//span[@cel_widget_id='MAIN-SEARCH_RESULTS-0']//parent::div[@class = 'sg-col-inner']")
	WebElement firstProduct;

	@FindBy(xpath = "(//span[@class= 'a-offscreen'])[1]")
	WebElement priceRate;

	@FindBy(xpath = "(//span[@class = 'a-color-price'])[1]")
	public WebElement colurPrice;

	@FindBy(xpath = "//input[@value = 'Add to Cart']")
	public WebElement addToCart;

	@FindBy(xpath = "//span[contains(@class, 'a-color-price hlb-price a')]")
	public WebElement colourPriceProceed;

	@FindBy(xpath = "(//a[contains(text(), 'Proceed to checkout')])[1]")
	public WebElement ProceedToCheckout;

	public AmazonPage crtOpsAct;

	public AmazonPage() {
		PageFactory.initElements(new AjaxElementLocatorFactory(getDriver(), TimeoutValue), this);
	}

	public void loginInit() throws Exception {
		actionInit();
		webDriverWaitInit();
		javascriptInit();
		loadBaseURL();
		Thread.sleep(3000);
		drvWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id= 'twotabsearchtextbox']")));
	}

	public String searchBookAndSelectList(String str) throws InterruptedException {
		expWaitElementToBeClickable(searchBar);
		searchBar.clear();
		searchBar.sendKeys(str);
		Thread.sleep(3000);
		WebElement ele = getDriver().findElement(By.xpath("(//*[contains(text(), '" + str + "')])"));
		drawHighlight(ele);
		ele.click();
		Thread.sleep(3000);
		String str2 = priceRate.getText();
		System.out.println(str2);
		firstProduct.click();
		return str2;
	}

	public String addToCart() throws InterruptedException {
		expWaitElementToBeClickable(addToCart);
		addToCart.click();
		Thread.sleep(3000);
		String str2 = colourPriceProceed.getText();
		System.out.println(str2);
		return str2;
	}
}
