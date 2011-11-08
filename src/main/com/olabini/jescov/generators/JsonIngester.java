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

public class JsonIngester {
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
}
