/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.*;

import com.olabini.jescov.CoverageData;
import com.olabini.jescov.FileCoverage;
import com.olabini.jescov.LineCoverage;
import com.olabini.jescov.BranchCoverage;

import org.json.simple.JSONValue;

public class JsonGenerator implements Generator {
    private final Writer writer;

    public JsonGenerator(Writer writer) {
        this.writer = writer;
    }

    public void generate(CoverageData data) throws IOException {
        Map genData = convert(data);
        JSONValue.writeJSONString(genData, writer);
    }

    private Map convert(CoverageData data) {
        Map result = new HashMap();
        for(String file : data.getFileNames()) {
            result.put(file, convert(data.getFileCoverageFor(file)));
        }
        return result;
    }

    private List convert(FileCoverage c) {
        List result = new LinkedList();
        for(Integer line : c.getLines()) {
            result.add(convert(c, line));
        }
        return result;
    }

    private List convert(FileCoverage c, Integer line) {
        List result = new LinkedList();
        result.add(line);
        LineCoverage lc = c.getLineCoverageFor(line); 
        if(lc == null) {
            result.add(0);
        } else {
            result.add(lc.getHits());
        }

        Collection<BranchCoverage> bcs = c.getBranchCoverageFor(line);
        if(null == bcs) {
            bcs = Collections.<BranchCoverage>emptySet();
        }
        result.add(convert(bcs));
        return result;
    }

    private List convert(Collection<BranchCoverage> bcs) {
        List result = new LinkedList();
        for(BranchCoverage bc : bcs) {
            result.add(convert(bc));
        }
        return result;
    }

    private List convert(BranchCoverage bc) {
        List result = new LinkedList();
        result.add(bc.getBranchId());
        result.add(convert(bc.getBranches()));
        return result;
    }

    private List convert(int[] list) {
        List result = new LinkedList();
        for(int i : list) {
            result.add(i);
        }
        return result;
    }
}
