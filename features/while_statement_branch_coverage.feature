Feature: Branch coverage of while statement
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of my while statements

  Scenario: while statement that is never hit
    When I execute this JavaScript:
      """
      function t1() {
        while(false) {
        }
      }
      """
    Then the positive branch coverage on line 2 should be 0
    And the negative branch coverage on line 2 should be 0

  Scenario: while statement where the condition is never true
    When I execute this JavaScript:
      """
      function t1(res) {
        while(res) {
          return 1;
        }
      }
      t1(false);
      """
    Then the negative branch coverage on line 2 should be 1
    And the positive branch coverage on line 2 should be 0

  Scenario: while statement where the condition is true once
    When I execute this JavaScript:
      """
      function t1(res) {
        while(res) {
          return 1;
        }
      }
      t1(true);
      """
    Then the positive branch coverage on line 2 should be 1
    And the negative branch coverage on line 2 should be 0

  Scenario: while statement where the condition is true more than once
    When I execute this JavaScript:
      """
      function t1(res) {
        var num = 3;
        while(res) {
          if(num == 0) {
            res = false;
          }
          num--;
        }
      }
      t1(true);
      """
    Then the positive branch coverage on line 3 should be 4
    And the negative branch coverage on line 3 should be 1
