
var LCOV = (function() {
              var collectedCoverageData = [];

              function initNoop(functionId, ignored, foundLines) {
                var coverage = {functionId: functionId, foundLines: foundLines};
                collectedCoverageData.push(coverage);
                for(var i = 0; i < foundLines.length; i++) {
                  coverage[foundLines[i]] = 0;
                }
                return coverage;
              }

              var self = {
                collectedCoverageData: collectedCoverageData,
                initNoop: initNoop
              };

              return self;
})();
