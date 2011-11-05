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

public class JsonGenerator {
    public void generate(CoverageData data, Writer writer) throws IOException {
        Map genData = convert(data);
        JSONValue.writeJSONString(genData, writer);
    }

    public CoverageData ingest(Reader reader) throws IOException {
        Map<String, Object> input = (Map<String, Object>)JSONValue.parse(reader);
        List<FileCoverage> fcs = new ArrayList<FileCoverage>();
        for(Map.Entry<String, Object> me : input.entrySet()) {
            Collection<LineCoverage> lcs = new ArrayList<LineCoverage>();
            Collection<BranchCoverage> bcs = new ArrayList<BranchCoverage>();
            
            List<List<Object>> lines = (List<List<Object>>)me.getValue();
            for(List<Object> lineInfo : lines) {
                int lineNumber = ((Long)lineInfo.get(0)).intValue();
                int hits = ((Long)lineInfo.get(1)).intValue();
                List<List<Object>> branches = (List<List<Object>>)lineInfo.get(2);
                lcs.add(new LineCoverage(lineNumber, hits));
                for(List<Object> bc : branches) {
                    int branchId = ((Long)bc.get(0)).intValue();
                    List<Long> actualCoverage = (List<Long>)bc.get(1);
                    int[] branchHits = new int[actualCoverage.size()];
                    int index = 0;
                    for(Long hit : actualCoverage) {
                        branchHits[index++] = hit.intValue();
                    }
                    bcs.add(new BranchCoverage(lineNumber, branchId, branchHits));
                }
            }

            fcs.add(new FileCoverage(me.getKey(), lcs, bcs));
        }
        return new CoverageData(fcs);
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
