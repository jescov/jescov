/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.console;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.olabini.jescov.Coverage;
import com.olabini.jescov.CoverageData;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import static com.olabini.jescov.Coverage.on;

import com.olabini.jescov.generators.JsonGenerator;

/**
 * @author <a href="mailto:ola.bini@gmail.com">Ola Bini</a>
 */
public class Runner {
    private final Context ctx;
    private final Scriptable scope;
    private final Coverage coverage;

    public Runner() {
        ctx = Context.enter();
        scope = ctx.initStandardObjects();
        coverage = on(ctx, scope);
    }

    public CoverageData done() {
        coverage.done();
        Context.exit();
        return coverage.getCoverageData();
    }

    public void executeReader(String filename, Reader reader) throws IOException {
        ctx.evaluateReader(scope, reader, filename, 0, null);
    }

    public void executeSource(String filename, String sourceCode) {
        ctx.evaluateString(scope, sourceCode, filename, 0, null);
    }

    public static void main(final String[] args) throws Exception {
        Runner r = new Runner();
        for(String file : args) {
            r.executeReader(file, new FileReader(file));
        }
        CoverageData data = r.done();
        FileWriter fw = new FileWriter("jescov.json.ser");
        new JsonGenerator().generate(data, fw);
        fw.close();
    }
}// Runner
