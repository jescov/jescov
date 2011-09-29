
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

var BCOV = (function() {
              var collectedCoverageData = [];

              function branchFalseInc(coverage) {
                return function(branchId) {
                  coverage[branchId][0]++;
                  return false;
                };
              }

              function branchTrueInc(coverage) {
                return function(branchId) {
                  coverage[branchId][1]++;
                  return true;
                };
              }

              function initNoop(functionId, foundBranches) {
                var coverage = {functionId: functionId, foundBranches: foundBranches};
                coverage['branchFalseInc'] = branchFalseInc(coverage);
                coverage['branchTrueInc'] = branchTrueInc(coverage);
                collectedCoverageData.push(coverage);
                for(var i = 0; i < foundBranches.length; i++) {
                  coverage[foundBranches[i][1]] = [0, 0];
                }
                return coverage;
              }

              var self = {
                collectedCoverageData: collectedCoverageData,
                initNoop: initNoop
              };

              return self;
})();
