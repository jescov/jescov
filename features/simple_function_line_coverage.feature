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

  Scenario: Function that is called once
    * I get the following line coverage for the JavaScript: 
      | coverage | code            |
      |        1 | function t1() { |
      |        1 |   return 0;     |
      |          | }               |
      |        1 | t1();           |

  Scenario: Function that is called several times
    * I get the following line coverage for the JavaScript: 
      | coverage | code                    |
      |        1 | function t1() {         |
      |        4 |   return 0;             |
      |          | }                       |
      |        1 | t1(); t1(); t1(); t1(); |

  Scenario: Function defined using variable that is never called
    * I get the following line coverage for the JavaScript: 
      | coverage | code                  |
      |        1 | var t1 = function() { |
      |        0 |   return 0;           |
      |          | }                     |

  Scenario: Function defined using variable that is called once
    * I get the following line coverage for the JavaScript: 
      | coverage | code                  |
      |        1 | var t1 = function() { |
      |        1 |   return 0;           |
      |          | }                     |
      |        1 | t1();                 |

  Scenario: Function defined using variable that is called several times
    * I get the following line coverage for the JavaScript: 
      | coverage | code                    |
      |        1 | var t1 = function() {   |
      |        4 |   return 0;             |
      |          | }                       |
      |        1 | t1(); t1(); t1(); t1(); |

