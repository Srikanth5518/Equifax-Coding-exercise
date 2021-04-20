package StepDefinition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import PageFactoryElements.APISupportFile;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class APISD {
	private APISupportFile restTester;
	private static String generatedEmployeeId;

	@Before(order = 1)
	public void loginInit() throws Exception {

	}

	@Given("User hit dummy REST API to get employee details")
	public void user_hit_dummy_REST_API() {
		restTester = new APISupportFile();
		restTester.getAllEmployeesRecords();
		System.out.println(restTester.returnStatusCode());
		System.out.println(restTester.returnResponseAsString());
		System.out.println(restTester.returnResponseHeaders());
		System.out.println(restTester.lengthOfResponse());
		assertEquals(restTester.returnStatusCode(), 200);
	}

	@Given("User hit dummy REST API to Create Employee and to delete Employee")
	public void user_hit_dummy_REST_API_to_Create_Employee() {
		restTester = new APISupportFile();
		String employeeName = "Auto21211";
		String salary = "42101";
		int age = 38;
		// Create a new employee record
		restTester.createEmployee(employeeName, salary, age);
		// System.out.println(restTester.returnResponseHeaders());
		assertEquals(restTester.returnStatusCode(), 200);
		System.out.println(restTester.returnResponseAsString());
		String employeeId = restTester.response.jsonPath().getString("id");
		System.out.println(employeeId);

		// Get all employee records and extract the total no of records - the
		// posted record will be the last entry
		restTester.getAllEmployeesRecords();
		System.out.println(restTester.lengthOfResponse());
		// Extract the generated Employee Id
		generatedEmployeeId = restTester.getGeneratedEmployeeIdOfLatestRecord();
		System.out.println(generatedEmployeeId);

		// Validate the contents created in the POST request in the response of
		// GET /employee/{id}
		assertTrue(restTester.verifyValueOfAFieldForEmployeeId(generatedEmployeeId, "employee_name", employeeName));
		assertTrue(restTester.verifyValueOfAFieldForEmployeeId(generatedEmployeeId, "employee_salary", salary));
		assertTrue(restTester.verifyValueOfAFieldForEmployeeId(generatedEmployeeId, "employee_age",
				Integer.toString(age)));

		// Delete the contents for the current record DELETE /delete/{id}
		restTester.deleteEmployeeRecord(employeeId);
		assertEquals(restTester.returnStatusCode(), 200);
		System.out.println(restTester.returnResponseAsString());
		assertTrue(restTester.returnResponseAsString().contains("deleted"));

	}

	@Then("List Employee specific details")
	public void list_Employee_specific_details() {
		// Retrieve the same record it should not be present in the system (404)
		// or body should be "false"
		restTester.getSpecificEmployeeIdRecord(generatedEmployeeId);
		System.out.println(restTester.returnResponseAsString());
		assertTrue(restTester.returnResponseAsString().contains("false"));
	}

}
