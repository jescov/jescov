package com.olabini.jescov;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.MozillaPackageProxy;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.debug.DebuggableScript;

public class CoverageRewriter {
    private final CoverageNameMapper nameMapper;
    private final Context context;

    public CoverageRewriter(CoverageNameMapper nameMapper, Context context) {
        this.nameMapper = nameMapper;
        this.context = context;
    }

    public void rewrite(DebuggableScript input, String source) {
        CodeInstrumentor ci = new CodeInstrumentor(nameMapper);
        String ic = ci.instrument(input.getSourceName(), source);
        //        System.err.println("New code: " + ic);
        Script s = context.compileString(ic, input.getSourceName(), 0, null);
        MozillaPackageProxy.copyInterpreterInternals(s, input);
    }
}
