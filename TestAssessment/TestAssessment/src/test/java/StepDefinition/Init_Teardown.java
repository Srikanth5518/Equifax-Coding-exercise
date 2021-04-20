package StepDefinition;

import java.util.Map.Entry;
import java.util.TreeMap;

import Utilities.CommonFunctions;
import cucumber.api.java.Before;

public class Init_Teardown extends CommonFunctions {
	static TreeMap<String, String> map = null;
	static Entry<String, String> lastfile;

	@Before(order = 0)
	public void initDriver() throws Exception {
		Thread.sleep(5000);
	}

}
