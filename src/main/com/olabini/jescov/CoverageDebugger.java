/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import org.mozilla.javascript.*;
import org.mozilla.javascript.debug.Debugger;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.DebugFrame;

import java.util.*;

public class CoverageDebugger implements Debugger {
    private final CoverageNameMapper nameMapper;
    private final CoverageRewriter coverageRewriter;
    private final Set<String> mappedSourceCode = new HashSet<String>();
    private final Set<String> currentlyMapping = new HashSet<String>();
    private final Configuration configuration;

    public CoverageDebugger(Context context, Configuration configuration) {
        this.nameMapper = new CoverageNameMapper();
        this.coverageRewriter = new CoverageRewriter(nameMapper, context);
        this.configuration = configuration;
    }

    void evaluateCoverageDependencies(Context cx, Scriptable scope) {
        if(configuration.isEnabled()) {
            mappedSourceCode.add(LcovDefinition.SOURCE);
            cx.evaluateString(scope, LcovDefinition.SOURCE, LcovDefinition.SOURCE_NAME, 0, null);
        }
    }

    public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript) {
        return null;
    }

    public void handleCompilationDone(Context cx, DebuggableScript fnOrScript, String source) {
        if(configuration.isEnabled() && configuration.allow(fnOrScript.getSourceName())) {
            if(mappedSourceCode.add(source)) {
                String sourceName = fnOrScript.getSourceName();
                if(currentlyMapping.add(sourceName)) {
                    try {
                        coverageRewriter.rewrite(fnOrScript, source);
                    } finally {
                        currentlyMapping.remove(sourceName);
                    }
                }
            }
        }
    }

    private CoverageData coverageData;

    void generateCoverageData(Scriptable scope) {
        if(configuration.isEnabled()) {
            Map<String, Map<Integer, LineCoverage>> coverageResults = new HashMap<String, Map<Integer, LineCoverage>>();
            Map<String, Collection<BranchCoverage>> coverageResults2 = new HashMap<String, Collection<BranchCoverage>>();
            NativeArray na = (NativeArray)(((Scriptable)scope.get("LCOV", scope)).get("collectedCoverageData", scope));
            for(Object coverage : na) {
                generateLineCoverage((Scriptable) coverage, scope, coverageResults);
            }
            NativeArray na2 = (NativeArray)(((Scriptable)scope.get("BCOV", scope)).get("collectedCoverageData", scope));
            for(Object coverage : na2) {
                generateBranchCoverage((Scriptable) coverage, scope, coverageResults2);
            }

            Set<String> allFileNames = new HashSet<String>();
            allFileNames.addAll(coverageResults.keySet());
            allFileNames.addAll(coverageResults2.keySet());
            List<FileCoverage> result = new ArrayList<FileCoverage>();
            for(String fileName : allFileNames) {
                Map<Integer, LineCoverage> lineCoverage = coverageResults.get(fileName);
                Collection<BranchCoverage> branchCoverage = coverageResults2.get(fileName);
                result.add(new FileCoverage(fileName,
                                            lineCoverage == null ? Collections.<LineCoverage>emptySet() : lineCoverage.values(),
                                            branchCoverage == null ? Collections.<BranchCoverage>emptySet() : branchCoverage));
            }
            coverageData = new CoverageData(result);
        }
    }

    private void generateLineCoverage(Scriptable coverage, Scriptable scope, Map<String, Map<Integer, LineCoverage>> coverageResults) {
        Map<Integer, LineCoverage> lineResults = new HashMap<Integer, LineCoverage>();
        int functionId = (int)Context.toNumber(coverage.get("functionId", scope));
        String filename = nameMapper.unmap(functionId);
        coverageResults.put(filename, lineResults);
        List<?> availableLines = (NativeArray)coverage.get("foundLines", scope);
        for(Object o : availableLines) {
            int line = (int)Context.toNumber(o);
            int hits = (int)Context.toNumber(coverage.get(line, scope));
            lineResults.put(line, new LineCoverage(line, hits));
        }
    }

    private void generateBranchCoverage(Scriptable coverage, Scriptable scope, Map<String, Collection<BranchCoverage>> coverageResults) {
        Collection<BranchCoverage> branchResults = new LinkedList<BranchCoverage>();
        int functionId = (int)Context.toNumber(coverage.get("functionId", scope));
        String filename = nameMapper.unmap(functionId);
        coverageResults.put(filename, branchResults);
        List<?> availableLines = (NativeArray)coverage.get("foundBranches", scope);
        for(Object o : availableLines) {
            int line = (int)Context.toNumber(((List)o).get(0));
            int branch = (int)Context.toNumber(((List)o).get(1));
            List cov = (List)coverage.get(branch, scope);
            int[] covResult = new int[cov.size()];
            for(int i = 0; i<cov.size(); i++) {
                covResult[i] = (int)Context.toNumber(cov.get(i));
            }
            branchResults.add(new BranchCoverage(line, branch, covResult));
        }
    }

    public CoverageData getCoverageData() {
        return coverageData;
    }
}// CoverageDebugger
