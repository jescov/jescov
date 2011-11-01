package com.olabini.jescov;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;

public class CoverageData {
    private final Map<String, FileCoverage> fileCoverage;

    public CoverageData(List<FileCoverage> fileCoverage) {
        this.fileCoverage = new TreeMap<String, FileCoverage>();
        for(FileCoverage fc : fileCoverage) {
            this.fileCoverage.put(fc.getFilename(), fc);
        }
    }

    public FileCoverage getFileCoverageFor(String filename) {
        return fileCoverage.get(filename);
    }

    public Collection<String> getFileNames() {
        return fileCoverage.keySet();
    }

}
