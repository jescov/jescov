package com.olabini.jescov;

public class BranchCoverage {
    private final int line;
    private final int branchId;
    private final int negativeBranch;
    private final int positiveBranch;

    public BranchCoverage(int line, int branchId, int negativeBranch, int positiveBranch) {
        this.line = line;
        this.branchId = branchId;
        this.negativeBranch = negativeBranch;
        this.positiveBranch = positiveBranch;
    }

    @Override
    public String toString() {
        return "BranchCoverage{" +
                "line=" + line +
                ", branchId=" + branchId +
                ", negativeBranch=" + negativeBranch +
                ", positiveBranch=" + positiveBranch +
                '}';
    }

    public int getLine() {
        return line;
    }

    public int getNegativeBranch() {
        return negativeBranch;
    }

    public int getPositiveBranch() {
        return positiveBranch;
    }
}
