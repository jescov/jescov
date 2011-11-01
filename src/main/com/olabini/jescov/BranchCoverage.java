package com.olabini.jescov;

import java.util.Arrays;

public class BranchCoverage {
    private final int line;
    private final int branchId;
    private final int[] branches;

    public BranchCoverage(int line, int branchId, int[] branches) {
        this.line = line;
        this.branchId = branchId;
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "BranchCoverage{" +
                "line=" + line +
                ", branchId=" + branchId +
                ", branches=" + Arrays.toString(branches) +
                '}';
    }

    public int getLine() {
        return line;
    }

    public int[] getBranches() {
        return branches;
    }

    public int getNegativeBranch() {
        return branches[0];
    }

    public int getPositiveBranch() {
        return branches[1];
    }
}
