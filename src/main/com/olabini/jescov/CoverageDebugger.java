/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import com.google.jstestdriver.coverage.Code;
import com.google.jstestdriver.coverage.CodeInstrumentor;
import com.google.jstestdriver.coverage.CoverageNameMapper;
import com.google.jstestdriver.coverage.InstrumentedCode;
import org.mozilla.javascript.*;
import org.mozilla.javascript.debug.Debugger;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.DebugFrame;

import javax.xml.ws.handler.MessageContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class CoverageDebugger implements Debugger {
    private final CoverageNameMapper nameMapper;
    private final CoverageRewriter coverageRewriter;
    private final Set<String> mappedSourceCode = new HashSet<String>();
    private final Set<String> currentlyMapping = new HashSet<String>();

    public CoverageDebugger(Context context) {
        this.nameMapper = new CoverageNameMapper();
        this.coverageRewriter = new CoverageRewriter(nameMapper, context);
    }

    void evaluateCoverageDependencies(Context cx, Scriptable scope) {
        mappedSourceCode.add(LcovDefinition.SOURCE);
        cx.evaluateString(scope, LcovDefinition.SOURCE, LcovDefinition.SOURCE_NAME, 0, null);
    }

    public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript) {
        return null;
    }

    public void handleCompilationDone(Context cx, DebuggableScript fnOrScript, String source) {
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

    void reportCoverage(Scriptable scope) {
        Map<String, Map<Integer, Integer>> coverageResults = new HashMap<String, Map<Integer, Integer>>();
        NativeArray na = (NativeArray)(((Scriptable)scope.get("LCOV", scope)).get("collectedCoverageData", scope));
        for(Object coverage : na) {
            reportSingleCoverage((Scriptable) coverage, scope, coverageResults);
        }
    }

    private void reportSingleCoverage(Scriptable coverage, Scriptable scope, Map<String, Map<Integer, Integer>> coverageResults) {
        Map<Integer, Integer> lineResults = new HashMap<Integer, Integer>();
        int functionId = (int)Context.toNumber(coverage.get("functionId", scope));
        String filename = nameMapper.unmap(functionId);
        coverageResults.put(filename, lineResults);
        List<?> availableLines = (NativeArray)coverage.get("foundLines", scope);
        for(Object o : availableLines) {
            int line = (int)Context.toNumber(o);
            int hits = (int)Context.toNumber(coverage.get(line, scope));
            lineResults.put(line, hits);
        }
    }
}// CoverageDebugger
