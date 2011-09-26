package com.olabini.jescov;

import com.google.jstestdriver.coverage.Code;
import com.google.jstestdriver.coverage.CodeInstrumentor;
import com.google.jstestdriver.coverage.CoverageNameMapper;
import com.google.jstestdriver.coverage.InstrumentedCode;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.MozillaPackageProxy;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.debug.DebuggableScript;

import java.util.HashSet;
import java.util.Set;

public class CoverageRewriter {
    private final CoverageNameMapper nameMapper;
    private final Context context;

    public CoverageRewriter(CoverageNameMapper nameMapper, Context context) {
        this.nameMapper = nameMapper;
        this.context = context;
    }

    public void rewrite(DebuggableScript input, String source) {
        CodeInstrumentor ci = new CodeInstrumentor(nameMapper);
        InstrumentedCode ic = ci.instrument(new Code(input.getSourceName(), source));
        Script s = context.compileString(ic.getInstrumentedCode(), input.getSourceName(), 0, null);
        MozillaPackageProxy.copyInterpreterInternals(s, input);
    }
}
