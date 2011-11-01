
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

              function branchInc(coverage) {
                return function(branchId, ix, ret) {
                  coverage[branchId][ix]++;
                  return ret;
                };
              }

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
                coverage['branchInc'] = branchInc(coverage);
                coverage['branchFalseInc'] = branchFalseInc(coverage);
                coverage['branchTrueInc'] = branchTrueInc(coverage);
                collectedCoverageData.push(coverage);
                for(var i = 0; i < foundBranches.length; i++) {
                  var arr = [];
                  for(var j = 0; j < foundBranches[i][2]; j++) {
                    arr.push(0);
                  }
                  coverage[foundBranches[i][1]] = arr;
                }
                return coverage;
              }

              var self = {
                collectedCoverageData: collectedCoverageData,
                initNoop: initNoop
              };

              return self;
})();
