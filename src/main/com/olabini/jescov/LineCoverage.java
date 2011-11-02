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

    public int getHits() {
        return this.hits;
    }

    public int getLinesValid() {
        return 1;
    }

    public int getLinesCovered() {
        return hits > 0 ? 1 : 0;
    }
}
