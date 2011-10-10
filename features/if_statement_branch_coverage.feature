Feature: Branch coverage of if statement
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of my if statements

  Scenario: Never hit statement with only if-branch
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        }
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: Never hit statement with if and else-branch
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        } else {
          return 2;
        }
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: Only hits negative branch once
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        }
      }
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 1
    And the positive branch coverage on line 2 should be 0

  Scenario: Only hits negative branch more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        }
      }
      t1(false);
      t1(false);
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 0

  Scenario: Only hits positive branch once
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        }
      }
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: Only hits positive branch more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        }
      }
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 0

  Scenario: Hits both negative and positive branches
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        }
      }
      t1(true);
      t1(true);
      t1(false);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the negative branch coverage on line 2 should be 1

  Scenario: Only hits negative branch once with else statement
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        } else {
          return 2;
        }
      }
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 1
    And the positive branch coverage on line 2 should be 0

  Scenario: Only hits negative branch more than once with else statement
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        } else {
          return 2;
        }
      }
      t1(false);
      t1(false);
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 0

  Scenario: Only hits positive branch once with else statement
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        } else {
          return 2;
        }
      }
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: Only hits positive branch more than once with else statement
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        } else {
          return 2;
        }
      }
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 0

  Scenario: Hits both negative and positive branches with else statement
    When I execute this JavaScript:
      """
      function t1(res) {
        if(res) {
          return 1;
        } else {
          return 2;
        }
      }
      t1(true);
      t1(true);
      t1(false);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the negative branch coverage on line 2 should be 1
