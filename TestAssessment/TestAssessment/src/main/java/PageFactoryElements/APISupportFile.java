package PageFactoryElements;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utilities.CommonFunctions;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.type.CollectionType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class APISupportFile extends CommonFunctions {
	private RequestSpecification requestSpec;
	private ResponseSpecification responseSpec;
	private static ValidatableResponse vResponse;
	public static Response response;
	private static String responseHeader;
	private static int statusCode;
	private static int responseCount;
	private final String resourcePathGetEmployees = "/employees";
	private final String resourcePathCreateEmployees = "/create";
	private final String resourcePathGetEmployeeId = "/employee/{id}";
	private final String resourcePathDeleteEmployeeId = "/delete/{id}";
	private final String apiUrl = "http://dummy.restapiexample.com/api/v1";
	String BASE_URL = "http://dummy.restapiexample.com/";

	public APISupportFile() {
		requestSpec = new RequestSpecBuilder().setBaseUri(apiUrl).setAccept(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}

	// GET /employees response
	public void getAllEmployeesRecords() {
		vResponse = given().log().all().spec(requestSpec).when().get(resourcePathGetEmployees).then();
		response = vResponse.extract().response();
		statusCode = response.statusCode();
		responseHeader = response.headers().toString();
	}

	// POST /create response
	public void createEmployee(String name, String salary, int age) {
		EmpData employee = new EmpData(name, salary, age);
		response = given().log().all().spec(requestSpec).contentType("application/json").body(employee).when()
				.post(resourcePathCreateEmployees).then().extract().response();
		System.out.println(response);
		statusCode = response.statusCode();
		String responseBody = response.getBody().asString();

	    JsonPath values = new JsonPath(responseBody);
		responseHeader = response.headers().toString();
		String employeeId = response.jsonPath().getString("id");
		System.out.println(values.getString("id"));

	}

	// GET /employees/{id} response
	public void getSpecificEmployeeIdRecord(String generatedEmployeeId) {
		response = given().log().all().spec(requestSpec).pathParam("id", generatedEmployeeId).when()
				.get(resourcePathGetEmployeeId).then().extract().response();
		statusCode = response.statusCode();
	}

	public String getGeneratedEmployeeIdOfLatestRecord() {
		List<Object> jsonList = response.jsonPath().getList("$");
		HashMap<?, ?> hMap = (HashMap<?, ?>) jsonList.get(responseCount - 1);
		System.out.println(hMap.entrySet());
		return (String) hMap.get("id");
	}

	public <T> List<T> jsonArrayToObjectList(String json, Class<T> tClass) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
		List<T> ts = mapper.readValue(json, listType);
		System.out.println(ts.get(0).getClass().getName());

		return ts;
	}

	public boolean verifyValueOfAFieldForEmployeeId(String employeeId, String fieldName, String fieldValue) {
		return given().log().all().spec(requestSpec).pathParam("id", employeeId).when().get(resourcePathGetEmployeeId)
				.then().extract().response().jsonPath().getString(fieldName).equalsIgnoreCase(fieldValue);
	}

	// DELETE /delete/{id} response
	public void deleteEmployeeRecord(String employeeId) {
		response = given().log().all().spec(requestSpec).pathParam("id", employeeId).when()
				.delete(resourcePathDeleteEmployeeId).then().extract().response();
		statusCode = response.statusCode();
	}

	public int returnStatusCode() {
		return statusCode;
	}

	public String returnResponseAsString() {
		return response.asString();
	}

	public String returnResponseHeaders() {
		return responseHeader;
	}

	public int lengthOfResponse() {
		return responseCount;
	}
}
