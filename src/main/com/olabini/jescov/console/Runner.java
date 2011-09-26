/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.console;

import java.io.FileReader;

import com.olabini.jescov.Coverage;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import static com.olabini.jescov.Coverage.on;

/**
 * @author <a href="mailto:ola.bini@gmail.com">Ola Bini</a>
 */
public class Runner {
    public static void main(final String[] args) throws Exception {
        Context ctx = Context.enter();
        try {
            Scriptable scope = ctx.initStandardObjects();
            Coverage coverage = on(ctx, scope);
            for(String file : args) {
                ctx.evaluateReader(scope, new FileReader(file), file, 0, null);
            }
            coverage.done();
        } finally {
            Context.exit();
        }
    }
}// Runner
