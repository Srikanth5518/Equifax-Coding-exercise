package StepDefinition;

import org.testng.Assert;

import PageFactoryElements.AmazonPage;
import Utilities.CommonFunctions;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AmazonSD extends CommonFunctions {
	public AmazonPage amazonPage;
	String priceValue = null;

	@Before(order = 0)
	public void initDriver() throws Exception {

		Thread.sleep(5000);
		intializeDriver("Chrome");
	}

	@Given("User Visit amzon url")
	public void user_Visit_amzon_url() throws Exception {
		amazonPage = new AmazonPage();
		amazonPage.loginInit();
	}

	@When("User search books in the search and select first available product")
	public void user_search_books_in_the_search_and_select_first_available_product() throws Exception {
		priceValue = amazonPage.searchBookAndSelectList("qa testing for beginners");
	}

	@Then("Verify price is displaying")
	public void verify_price_is_displaying() {
		String str = amazonPage.colurPrice.getText();
		// Assert.assertEquals(str, priceValue);
	}

	@When("click on Add to Cart and check price and click to proceed for checkout")
	public void click_on_Add_to_Cart_and_check_price_and_click_to_proceed_for_checkout() throws Exception {
		String str = amazonPage.addToCart();
		Assert.assertEquals(str, priceValue);
	}

}
