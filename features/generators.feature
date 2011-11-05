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

  Scenario: Generate internal structures from JSON
    When I ingest this JSON:
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
    Then the internal structures should be correct

  Scenario: Generate Cobertura-style XML
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
    Then the generated XML should be:
      """
      <?xml version="1.0" encoding="utf-8"?>
      <!DOCTYPE coverage SYSTEM "http://cobertura.sourceforge.net/xml/coverage-04.dtd">
      <coverage line-rate="0.8181818181818182" branch-rate="0.3" lines-covered="9" lines-valid="11" branches-covered="6" branches-valid="20" complexity="0.0" version="1.9.4.1" timestamp="0">
        <sources>
          <source>foo.js</source>
          <source>hello.js</source>
        </sources>
        <packages>
          <package name="all" line-rate="0.8181818181818182" branch-rate="0.3" complexity="0.0">
            <classes>
              <class name="foo.js" filename="foo.js" line-rate="0.8333333333333334" branch-rate="0.3" complexity="0.0">
                <methods>
                </methods>
                <lines>
                  <line number="1" hits="1" branch="false"/>
                  <line number="2" hits="1" branch="false"/>
                  <line number="3" hits="1" branch="true" condition-coverage="50% (1/2)">
                    <conditions>
                      <condition number="0" type="jump" coverage="50%"/>
                    </conditions>
                  </line>
                  <line number="4" hits="0" branch="true" condition-coverage="0% (0/4)">
                    <conditions>
                      <condition number="0" type="jump" coverage="0%"/>
                      <condition number="1" type="jump" coverage="0%"/>
                    </conditions>
                  </line>
                  <line number="6" hits="1" branch="true" condition-coverage="50% (2/4)">
                    <conditions>
                      <condition number="0" type="jump" coverage="50%"/>
                      <condition number="1" type="jump" coverage="50%"/>
                    </conditions>
                  </line>
                  <line number="9" hits="1" branch="false"/>
                </lines>
              </class>
              <class name="hello.js" filename="hello.js" line-rate="0.8" branch-rate="0.3" complexity="0.0">
                <methods>
                </methods>
                <lines>
                  <line number="1" hits="1" branch="false"/>
                  <line number="2" hits="1" branch="true" condition-coverage="50% (1/2)">
                    <conditions>
                      <condition number="0" type="jump" coverage="50%"/>
                    </conditions>
                  </line>
                  <line number="3" hits="1" branch="true" condition-coverage="50% (2/4)">
                    <conditions>
                      <condition number="0" type="jump" coverage="50%"/>
                      <condition number="1" type="jump" coverage="50%"/>
                    </conditions>
                  </line>
                  <line number="5" hits="0" branch="true" condition-coverage="0% (0/4)">
                    <conditions>
                      <condition number="0" type="jump" coverage="0%"/>
                      <condition number="1" type="jump" coverage="0%"/>
                    </conditions>
                  </line>
                  <line number="8" hits="1" branch="false"/>
                </lines>
              </class>
            </classes>
          </package>
        </packages>
      </coverage>
      """
