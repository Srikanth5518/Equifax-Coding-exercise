package StepDefinition;

import org.testng.annotations.AfterClass;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "Regression/Feature"},tags={"@Test"},glue = {
		"StepDefinition" }, plugin = { "rerun:target/rerun.txt", "junit:target/Cucumberreports/Cucumber.xml", "pretty",
				"html:target/Cucumberreports", "json:target/Cucumberreports/Cucumber.json",
				"json:target/Cucumberreports/cucumber-report.json", "usage:target/Cucumberreports/cucumber-usage.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, monochrome = true, dryRun = false)

public class TestRunner extends AbstractTestNGCucumberTests {

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println("Execution is completed");
	}
}

