/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.util.Collection;

public class LineInfo {
    private final int lineNumber;
    private final String code;
    private final int hits;
    private final Collection<int[]> branches;

    public LineInfo(int lineNumber, String code, int hits, Collection<int[]> branches) {
        this.lineNumber = lineNumber;
        this.code = code;
        this.hits = hits;
        this.branches = branches;
    }

    public boolean getCanBeCovered() {
        return hits != -1;
    }

    public boolean isCompletelyCovered() {
        return hits > 0 && allBranchesCovered();
    }

    public boolean isBranchCoverage() {
        return branches.size() > 0;
    }

    public String getBranchDescription() {
        if(branches.size() > 1) {
            return blendedCoverage() + " " + separateCoveragePercentage();
        } else {
            return blendedCoverage();
        }
    }

    private String separateCoveragePercentage() {
        StringBuilder sb = new StringBuilder();
        sb.append("[each condition: ");
        String sep = "";
        for(int[] branch : branches) {
            int valid = 0;
            int covered = 0;
            for(int hits : branch) {
                valid++;
                if(hits > 0) {
                    covered++;
                }
            }
            sb.append(sep).append(percentage(covered, valid));
            sep = ", ";
        }
        sb.append("].");
        return sb.toString();
    }

    private String blendedCoverage() {
        int valid = 0;
        int covered = 0;
        for(int[] branch : branches) {
            for(int hits : branch) {
                valid++;
                if(hits > 0) {
                    covered++;
                }
            }
        }

        return percentage(covered, valid) + " (" + covered + "/" + valid + ")";
    }

    private String percentage(int covered, int valid) {
        int percentage = (int)((((double)covered) / ((double)valid)) * 100);
        return "" + percentage + "%";
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getCode() {
        return this.code;
    }

    public int getHits() {
        return this.hits;
    }

    private boolean allBranchesCovered() {
        for(int[] branch : branches) {
            for(int hits : branch) {
                if(hits == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
