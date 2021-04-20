Feature: RestAPIForAssessment.feature

  @Test
  Scenario: RestAPIForAssessment_GET
    Given User Visit amzon url 
    When User search books in the search and select first available product
    Then Verify price is displaying
    When click on Add to Cart and check price and click to proceed for checkout

  