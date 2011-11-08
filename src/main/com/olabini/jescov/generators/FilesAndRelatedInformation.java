/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

public class FilesAndRelatedInformation {
    private final boolean link;
    private final String filename;
    private final int validLines;
    private final int coveredLines;
    private final int validBranches;
    private final int coveredBranches;

    public FilesAndRelatedInformation(boolean link, String filename, int validLines, int coveredLines, int validBranches, int coveredBranches) {
        this.link = link;
        this.filename = filename;
        this.validLines = validLines;
        this.coveredLines = coveredLines;
        this.validBranches = validBranches;
        this.coveredBranches = coveredBranches;
    }

    public boolean getLink() {
        return this.link;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getPhysicalFilename() {
        return this.filename.replaceAll("/", ".");
    }

    public String getLinesCoveragePercent() {
        return coveragePercent(coveredLines, validLines);
    }

    public String getBranchesCoveragePercent() {
        return coveragePercent(coveredBranches, validBranches);
    }

    public String getLinesCoverageWidth() {
        return coverageWidth(coveredLines, validLines);
    }

    public String getBranchesCoverageWidth() {
        return coverageWidth(coveredBranches, validBranches);
    }

    public String getLinesCoverageFraction() {
        return coverageFraction(coveredLines, validLines);
    }

    public String getBranchesCoverageFraction() {
        return coverageFraction(coveredBranches, validBranches);
    }

    private String coveragePercent(int covered, int valid) {
        if(valid == 0) {
            return "N/A";
        } else {
            return ""+((int)(((double)covered / (double)valid) * 100))+"%";
        }
    }

    private String coverageWidth(int covered, int valid) {
        if(valid == 0) {
            return "0";
        } else {
            return ""+((int)(((double)covered / (double)valid) * 100));
        }
    }

    private String coverageFraction(int covered, int valid) {
        if(valid == 0) {
            return "N/A";
        } else {
            return "" + covered + "/" + valid;
        }
    }
}
