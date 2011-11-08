/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.io.IOException;
import java.io.Writer;

import java.util.*;

import com.olabini.jescov.CoverageData;
import com.olabini.jescov.FileCoverage;
import com.olabini.jescov.LineCoverage;
import com.olabini.jescov.BranchCoverage;

public class XmlGenerator implements Generator {
    private final Writer writer;

    public XmlGenerator(Writer writer) {
        this.writer = writer;
    }

    public void generate(CoverageData data) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        writer.write("<!DOCTYPE coverage SYSTEM \"http://cobertura.sourceforge.net/xml/coverage-04.dtd\">\n");
        writer.write("<coverage line-rate=\"" + data.getLineRate() + "\" branch-rate=\"" + data.getBranchRate() + "\" lines-covered=\"" + data.getLinesCovered() + "\" lines-valid=\"" + data.getLinesValid() + "\" branches-covered=\"" + data.getBranchesCovered() + "\" branches-valid=\"" + data.getBranchesValid() + "\" complexity=\"0.0\" version=\"1.9.4.1\" timestamp=\"0\">\n");
        writer.write("  <sources>\n");
        for(String fileName : data.getFileNames()) {
            writer.write("    <source>"+fileName+"</source>\n");
        }
        writer.write("  </sources>\n");
        writer.write("  <packages>\n");
        writer.write("    <package name=\"all\" line-rate=\"" + data.getLineRate() + "\" branch-rate=\"" + data.getBranchRate() + "\" complexity=\"0.0\">\n");
        writer.write("      <classes>\n");
        for(String fileName : data.getFileNames()) {
            FileCoverage fc = data.getFileCoverageFor(fileName);
            writer.write("        <class name=\""+fileName+"\" filename=\""+fileName+"\" line-rate=\""+fc.getLineRate()+"\" branch-rate=\""+fc.getBranchRate()+"\" complexity=\"0.0\">\n");
            writer.write("          <methods>\n");
            writer.write("          </methods>\n");
            writer.write("          <lines>\n");
            for(int line : fc.getLines()) {
                LineCoverage lc = fc.getLineCoverageFor(line);
                Collection<BranchCoverage> bcs = fc.getBranchCoverageFor(line);
                if(bcs == null) {
                    writer.write("            <line number=\"" + line + "\" hits=\"" + lc.getHits() + "\" branch=\"false\"/>\n");
                } else {
                    String coverageString = calculateCoverage(bcs);
                    writer.write("            <line number=\"" + line + "\" hits=\"" + lc.getHits() + "\" branch=\"true\" condition-coverage=\"" + coverageString + "\">\n");
                    writer.write("              <conditions>\n");
                    int i = 0;
                    for(BranchCoverage bc : bcs) {
                        writer.write("                <condition number=\"" + (i++) + "\" type=\"jump\" coverage=\"" + calculateCoverage(bc) + "\"/>\n");
                    }
                    writer.write("              </conditions>\n");
                    writer.write("            </line>\n");
                }
            }
            writer.write("          </lines>\n");
            writer.write("        </class>\n");
        }
        writer.write("      </classes>\n");
        writer.write("    </package>\n");
        writer.write("  </packages>\n");
        writer.write("</coverage>");
    }

    private String calculateCoverage(Collection<BranchCoverage> bcs) {
        int allBranches = 0;
        int coveredBranches = 0;
        
        for(BranchCoverage bc : bcs) {
            allBranches += bc.getBranchesValid();
            coveredBranches += bc.getBranchesCovered();
        }
        if(allBranches == 0) {
            return "100% (0/0)";
        } else {
            return ""+((int)(((double)coveredBranches / (double)allBranches) * 100))+"% (" + coveredBranches + "/" + allBranches + ")";
        }
    }

    private String calculateCoverage(BranchCoverage bc) {
        int allBranches = bc.getBranchesValid();
        int coveredBranches = bc.getBranchesCovered();
        
        if(allBranches == 0) {
            return "100%";
        } else {
            return ""+((int)(((double)coveredBranches / (double)allBranches) * 100))+"%";
        }
    }
}
