Feature: Branch coverage of for statement
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of my for statements

  Scenario: for-in statement that is never hit
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x in res) {
        }
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: for-in statement where the condition is never true
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x in res) {
        }
      }
      t1({});
      """
    Then the negative branch coverage on line 2 should be 1
    And the positive branch coverage on line 2 should be 0

  Scenario: for-in statement where the condition is true once
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x in res) {
        }
      }
      t1({foo: 43});
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 1

  Scenario: for-in statement where the condition is true more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x in res) {
        }
      }
      t1({foo: 43, fox: 15, bar: 1});
      t1({foo: 43});
      t1({});
      """
    Then the positive branch coverage on line 2 should be 4
    And the negative branch coverage on line 2 should be 3

  Scenario: for statement that is never hit
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x=0;x<res;x++) {
        }
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: for statement where the condition is never true
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x=0;x<res;x++) {
        }
      }
      t1(0);
      """
    Then the negative branch coverage on line 2 should be 1
    And the positive branch coverage on line 2 should be 0

  Scenario: for statement where the condition is true once
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x=0;x<res;x++) {
        }
      }
      t1(1);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 1

  Scenario: for statement where the condition is true more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        for(var x=0;x<res;x++) {
        }
      }
      t1(4);
      t1(1);
      t1(0);
      """
    Then the positive branch coverage on line 2 should be 5
    And the negative branch coverage on line 2 should be 3
