/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.console;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.olabini.jescov.Configuration;
import com.olabini.jescov.Coverage;
import com.olabini.jescov.CoverageData;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import static com.olabini.jescov.Coverage.on;

import com.olabini.jescov.generators.JsonGenerator;
import com.olabini.jescov.generators.HtmlGenerator;
import com.olabini.jescov.generators.CombinedGenerator;
import com.olabini.jescov.generators.JsonIngester;

/**
 * @author <a href="mailto:ola.bini@gmail.com">Ola Bini</a>
 */
public class Runner {
    private final Context ctx;
    private final Scriptable scope;
    private final Coverage coverage;
    private final Configuration configuration;

    public Runner(Configuration configuration) {
        ctx = Context.enter();
        scope = ctx.initStandardObjects();
        this.configuration = configuration;
        coverage = on(ctx, scope, configuration);
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
        Configuration c = new Configuration();
        String fileout = c.getJsonOutputFile();
        FileWriter fw = new FileWriter(fileout);
        c.setGenerator(new CombinedGenerator(new JsonGenerator(fw), new HtmlGenerator(c)));
        Runner r = new Runner(c);
        for(String file : args) {
            r.executeReader(file, new FileReader(file));
        }
        CoverageData data = r.done();

        if(c.isJsonOutputMerge() && new File(fileout).exists()) {
            FileReader fr = new FileReader(fileout);
            CoverageData moreData = new JsonIngester().ingest(fr);
            fr.close();
            data = moreData.plus(data);
        }
        
        c.getGenerator().generate(data);
        fw.close();
    }
}// Runner
