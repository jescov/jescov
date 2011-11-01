Feature: Branch coverage of short circuiting and expression
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of my short circuiting and expression

  Scenario: and expression that is never executed
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && 42;
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: and expression where the left hand side is always false
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && 42;
      }
      t1(false);
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 1

  Scenario: and expression where the left hand side is true once
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && 42;
      }
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: and expression where the left hand is both false and true at different times
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && 42;
      }
      t1(false);
      t1(false);
      t1(true);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the negative branch coverage on line 2 should be 2

  Scenario: and expression where the left hand value is used
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && 42;
      }
      if(t1(undefined) !== undefined) {
        throw "Expected and expression to correctly return left hand side";
      }
      if(t1(null) !== null) {
        throw "Expected and expression to correctly return left hand side";
      }
      if(t1(false) !== false) {
        throw "Expected and expression to correctly return left hand side";
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 3


  Scenario: and expression where the left right hand value is used
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && 42;
      }
      if(t1(true) !== 42) {
        throw "Expected and expression to correctly return right hand side";
      }
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: chained and expressions
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && false && 42;
      }
      t1(false);
      t1(false);
      t1(true);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 3

  Scenario: chained and expressions with parenthesis
    When I execute this JavaScript:
      """
      function t1(res) {
        return (res && false) && 42;
      }
      t1(false);
      t1(false);
      t1(true);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 5

  Scenario: chained and expressions with other parenthesis
    When I execute this JavaScript:
      """
      function t1(res) {
        return res && (false && 42);
      }
      t1(false);
      t1(false);
      t1(true);
      t1(true);
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 3
    And the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 2
    And the negative branch coverage on line 2 should be 3
