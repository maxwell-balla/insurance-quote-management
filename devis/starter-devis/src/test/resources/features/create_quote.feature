Feature: Creating an insurance quote when pricing service is available
  In order to provide a price to a customer
  As an insurance agent
  I want to create a quote and receive the calculated price

  Background:
    Given the pricing service is available

  Scenario: Create a car insurance quote successfully
    Given the agent enter the following information's:
      | customerId                           | productType | age | capital | duration |
      | 123e4567-e89b-12d3-a456-426614174000 | AUTO        | 30  | 20000   | 12       |
    When the agent makes a new quote
    Then the agent receives the following quote details:
      | field       | expected value                       |
      | quoteId     | GENERATED                            |
      | customerId  | 123e4567-e89b-12d3-a456-426614174000 |
      | productType | AUTO                                 |
      | capital     | 20000.0                              |
      | duration    | 12                                   |
      | status      | CREATED                              |
      | tarif       | 300.0                                |