Feature: Branch coverage of short circuiting or expression
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of my short circuiting or expression

  Scenario: or expression that is never executed
    When I execute this JavaScript:
      """
      function t1(res) {
        return res || 42;
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: or expression where the left hand side is always false
    When I execute this JavaScript:
      """
      function t1(res) {
        return res || 42;
      }
      t1(false);
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 1

  Scenario: or expression where the left hand side is true once
    When I execute this JavaScript:
      """
      function t1(res) {
        return res || 42;
      }
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: or expression where the left hand is both false and true at different times
    When I execute this JavaScript:
      """
      function t1(res) {
        return res || 42;
      }
      t1(false);
      t1(false);
      t1(true);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the negative branch coverage on line 2 should be 2

  Scenario: or expression where the left hand value is used
    When I execute this JavaScript:
      """
      function t1(res) {
        return res || 42;
      }
      if(t1(15) !== 15) {
        throw "Expected and expression to correctly return left hand side";
      }
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: or expression where the right hand value is used
    When I execute this JavaScript:
      """
      function t1(res) {
        return res || 42;
      }
      if(t1(false) !== 42) {
        throw "Expected and expression to correctly return right hand side";
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 1

  Scenario: chained or expressions
    When I execute this JavaScript:
      """
      function t1(res, res2) {
        return res || res2 || 42;
      }
      t1(false, false);
      t1(false, true);
      t1(true, false);
      t1(true, false);
      t1(true, true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 1

  Scenario: chained or expressions with parenthesis
    When I execute this JavaScript:
      """
      function t1(res, res2) {
        return (res || res2) || 42;
      }
      t1(false, false);
      t1(false, true);
      t1(true, false);
      t1(true, false);
      t1(true, true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 4
    And the negative branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 1

  Scenario: chained or expressions with parenthesis in the other direction
    When I execute this JavaScript:
      """
      function t1(res, res2) {
        return res || (res2 || 42);
      }
      t1(false, false);
      t1(false, true);
      t1(true, false);
      t1(true, false);
      t1(true, true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 1
