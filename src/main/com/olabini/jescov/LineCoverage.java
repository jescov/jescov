package com.olabini.jescov;

public class LineCoverage {
    private final int line;
    private final int hits;

    public LineCoverage(int line, int hits) {
        this.line = line;
        this.hits = hits;
    }

    public int getLine() {
        return this.line;
    }
}
