Feature: Calculating averages
  In order to find out how well I'm doing
  As a developer
  I want to have averages per file or even over different files calculated

  Scenario: Executing simple JavaScript
    When I execute this JavaScript:
      """
      function t1(res, res2, res3) {
        if(res) {
          return res2 && res3 && 42;
        } else {
          return res2 || res3 && 43;
        }
      }
      t1(true, true, false);
      """
    Then the file lines-valid should be 5
    Then the file branches-valid should be 10
    Then the file lines-covered should be 4
    Then the file branches-covered should be 3
    Then the file line-rate should be 0.8
    Then the file branch-rate should be 0.3

  Scenario: Executing more than one JavaScript file
    When I execute this JavaScript named hello.js:
      """
      function t1(res, res2, res3) {
        if(res) {
          return res2 && res3 && 42;
        } else {
          return res2 || res3 && 43;
        }
      }
      t1(true, true, false);
      """
    And I execute this JavaScript named foo.js:
      """
      function t1(res, res2, res3) {
        var x = 13;
        if(res) {
          return res2 && res3 && 42;
        } else {
          return res2 || res3 && 43;
        }
      }
      t1(false, false, false);
      """
    Then the complete lines-valid should be 11
    Then the complete branches-valid should be 20
    Then the complete lines-covered should be 9
    Then the complete branches-covered should be 6
    Then the complete line-rate should be 0.8181
    Then the complete branch-rate should be 0.3

