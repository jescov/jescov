package com.olabini.jescov;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

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

    public int getLinesValid() {
        int sum = 0;
        for(FileCoverage fc : fileCoverage.values()) {
            sum += fc.getLinesValid();
        }
        return sum;
    }

    public int getBranchesValid() {
        int sum = 0;
        for(FileCoverage fc : fileCoverage.values()) {
            sum += fc.getBranchesValid();
        }
        return sum;
    }

    public int getLinesCovered() {
        int sum = 0;
        for(FileCoverage fc : fileCoverage.values()) {
            sum += fc.getLinesCovered();
        }
        return sum;
    }

    public int getBranchesCovered() {
        int sum = 0;
        for(FileCoverage fc : fileCoverage.values()) {
            sum += fc.getBranchesCovered();
        }
        return sum;
    }

    public double getLineRate() {
        return getLinesCovered() / (double)getLinesValid();
    }

    public double getBranchRate() {
        return getBranchesCovered() / (double)getBranchesValid();
    }

    public CoverageData plus(CoverageData other) {
        List<FileCoverage> fcs = new ArrayList<FileCoverage>();
        Set<String> files = new HashSet<String>();
        files.addAll(this.getFileNames());
        files.addAll(other.getFileNames());
        for(String file : files) {
            FileCoverage left = this.getFileCoverageFor(file);
            FileCoverage right = other.getFileCoverageFor(file);

            if(left == null || right == null) {
                if(left == null) {
                    fcs.add(right);
                } else {
                    fcs.add(left);
                }
            } else {
                fcs.add(left.plus(right));
            }
        }

        return new CoverageData(fcs);
    }
}
