Feature: Simple function line coverage
  In order to know what lines have been executed
  As a developer
  I want to see what lines have been executed inside of a function

  Scenario: Function that is never called
    * I get the following line coverage for the JavaScript: 
      | coverage | code            |
      |        1 | function t1() { |
      |        0 |   return 0;     |
      |          | }               |


