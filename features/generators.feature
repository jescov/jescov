Feature: Generating coverage output
  In order to see the results of running code coverage
  As a developer
  I would like to be able to generate output in different formats

  Scenario: Generate internal serialized format in JSON
    When I execute this JavaScript:
      """
      function t1(res, res2, res3) {
        if(res) {
          return res2 && res3 && 42;
        } else {
          return res2 || res3 && 43;
        }
      }
      t1(false, false, false);
      t1(false, false, true);
      t1(false, true, false);
      t1(false, true, true);
      t1(true, false, false);
      t1(true, false, true);
      t1(true, true, false);
      t1(true, true, true);
      """
    Then the generated JSON should be:
      """
      {"<test input>": 
        [
          [1, 1, []],
          [2, 8, [[0, [4,4]]]],
          [3, 4, [[11,[1,1]], [10,[2,2]]]],
          [5, 4, [[20,[1,1]], [14,[2,2]]]],
          [8, 1, []],
          [9, 1, []],
          [10, 1, []],
          [11, 1, []],
          [12, 1, []],
          [13, 1, []], 
          [14, 1, []],
          [15, 1, []]
        ]
      }
      """

  Scenario: Generate Cobertura-style XML from JSON format
  Scenario: Generate Cobertura-style HTML from JSON format
