Feature: RestAPIForAssessment.feature

  @TestAPI
  Scenario: RestAPIForAssessment_GET
    Given User hit dummy REST API to get employee details

  @TestAPI
  Scenario: RestAPIForAssessment_GET_Delete
    Given User hit dummy REST API to Create Employee and to delete Employee
    Then List Employee specific details
