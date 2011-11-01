Feature: Branch coverage of switch statement
  In order to know what branches I have covered
  As a developer
  I want to see the branch coverage of my switch statements

  Scenario: switch statement that is never hit
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          case 2:
            break;
          default:
            break;
        }
      }
      """
    Then the branch coverage on line 2 should be 0 on axis 0
    And the branch coverage on line 2 should be 0 on axis 1
    And the branch coverage on line 2 should be 0 on axis 2

  Scenario: switch statement with one case and that case is always taken
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
        }
      }
      t1(1);
      """
    Then the branch coverage on line 2 should be 1 on axis 0

  Scenario: switch statement with one case and one default where the case is always taken
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          default:
            break;
        }
      }
      t1(1);
      """
    Then the branch coverage on line 2 should be 1 on axis 0
    And the branch coverage on line 2 should be 0 on axis 1

  Scenario: switch statement with one case and one default where the default is always taken
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          default:
            break;
        }
      }
      t1(2);
      """
    Then the branch coverage on line 2 should be 0 on axis 0
    And the branch coverage on line 2 should be 1 on axis 1

  Scenario: switch statement with one case and one default where both branches are taken
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          default:
            break;
        }
      }
      t1(1);
      t1(2);
      t1(1);
      t1(2);
      t1(1);
      """
    Then the branch coverage on line 2 should be 3 on axis 0
    And the branch coverage on line 2 should be 2 on axis 1

  Scenario: switch statement with more than one case and no default where one case is always taken 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          case 2:
            break;
          case 3:
            break;
          case 4:
            break;
        }
      }
      t1(1);
      t1(1);
      """
    Then the branch coverage on line 2 should be 2 on axis 0
    And the branch coverage on line 2 should be 0 on axis 1
    And the branch coverage on line 2 should be 0 on axis 2
    And the branch coverage on line 2 should be 0 on axis 3

  Scenario: switch statement with more than one case and no default where the default is always taken 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 4:
            break;
        }
      }
      t1(1);
      t1(1);
      """
    Then the branch coverage on line 2 should be 0 on axis 0

  Scenario: switch statement with more than one case and no default where both cases and the default is taken 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 2:
            break;
          case 4:
            break;
        }
      }
      t1(1);
      t1(2);
      t1(1);
      t1(4);
      """
    Then the branch coverage on line 2 should be 1 on axis 0
    And the branch coverage on line 2 should be 1 on axis 1

  Scenario: switch statement with more than one case and a default where one case is always taken 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 2:
            break;
          case 4:
            break;
          default:
            break;
        }
      }
      t1(4);
      t1(4);
      t1(4);
      """
    Then the branch coverage on line 2 should be 0 on axis 0
    And the branch coverage on line 2 should be 3 on axis 1
    And the branch coverage on line 2 should be 0 on axis 2

  Scenario: switch statement with more than one case and a default where more than one case is always taken 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 2:
            break;
          case 4:
            break;
          default:
            break;
        }
      }
      t1(4);
      t1(2);
      t1(4);
      """
    Then the branch coverage on line 2 should be 1 on axis 0
    And the branch coverage on line 2 should be 2 on axis 1
    And the branch coverage on line 2 should be 0 on axis 2

  Scenario: switch statement with more than one case and a default where the default is always taken 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          case 2:
            break;
          default:
            break;
        }
      }
      t1(4);
      t1(4);
      t1(4);
      """
    Then the branch coverage on line 2 should be 0 on axis 0
    And the branch coverage on line 2 should be 0 on axis 1
    And the branch coverage on line 2 should be 3 on axis 2

  Scenario: switch statement with more than one case and a default where all cases are executed 
    When I execute this JavaScript:
      """
      function t1(res) {
        switch(res) {
          case 1:
            break;
          case 2:
            break;
          default:
            break;
        }
      }
      t1(4);
      t1(4);
      t1(1);
      t1(4);
      t1(1);
      t1(2);
      """
    Then the branch coverage on line 2 should be 2 on axis 0
    And the branch coverage on line 2 should be 1 on axis 1
    And the branch coverage on line 2 should be 3 on axis 2

