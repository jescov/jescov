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

    public int getBranchId() {
        return branchId;
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
    
    public int getBranchesValid() {
        return branches.length;
    }

    public int getBranchesCovered() {
        int sum = 0;
        for(int hits : branches) {
            sum += (hits > 0 ? 1 : 0);
        }
        return sum;
    }

    public BranchCoverage plus(BranchCoverage other) {
        int[] newBranches = new int[this.branches.length];
        for(int i = 0; i<newBranches.length; i++) {
            newBranches[i] = this.branches[i] + other.branches[i];
        }
        return new BranchCoverage(this.line, this.branchId, newBranches);
    }
}
