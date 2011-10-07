Feature: Branch coverage of ternary operator
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of a ternary operator

  Scenario: Never hit
    When I execute this JavaScript:
      """
      function t1(res) {
        return res ? 1 : 2;
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: Only hits negative branch
    When I execute this JavaScript:
      """
      function t1(res) {
        return res ? 1 : 2;
      }
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 1
    And the positive branch coverage on line 2 should be 0

  Scenario: Hits negative branch more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        return res ? 1 : 2;
      }
      t1(false);
      t1(false);
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 0

  Scenario: Only hits positive branch
    When I execute this JavaScript:
      """
      function t1(res) {
        return res ? 1 : 2;
      }
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: Hits positive branch more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        return res ? 1 : 2;
      }
      t1(true);
      t1(true);
      t1(true);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 5
    And the negative branch coverage on line 2 should be 0

  Scenario: Hits both positive and negative branches
    When I execute this JavaScript:
      """
      function t1(res) {
        return res ? 1 : 2;
      }
      t1(true);
      t1(true);
      t1(false);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 4
    And the negative branch coverage on line 2 should be 1
