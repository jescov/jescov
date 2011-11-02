package com.olabini.jescov;

import java.util.*;

public class FileCoverage {
    private final String filename;
    private final Collection<LineCoverage> allLineCoverage;
    private final Collection<BranchCoverage> allBranchCoverage;
    private final Map<Integer, LineCoverage> lineCoverage;
    private final Map<Integer, Collection<BranchCoverage>> branchCoverage;

    public FileCoverage(String filename, Collection<LineCoverage> lineCoverage, Collection<BranchCoverage> branchCoverage) {
        this.filename = filename;
        this.allLineCoverage = lineCoverage;
        this.allBranchCoverage = branchCoverage;
        this.lineCoverage = generateLineCoverage();
        this.branchCoverage = generateBranchCoverage();
    }

    private Map<Integer, LineCoverage> generateLineCoverage() {
        Map<Integer, LineCoverage> result = new TreeMap<Integer, LineCoverage>();
        for(LineCoverage lc : allLineCoverage) {
            result.put(lc.getLine(), lc);
        }
        return result;
    }

    private Map<Integer, Collection<BranchCoverage>> generateBranchCoverage() {
        Map<Integer, Collection<BranchCoverage>> result = new TreeMap<Integer, Collection<BranchCoverage>>();
        for(BranchCoverage bc : allBranchCoverage) {
            Collection<BranchCoverage> lbc = result.get(bc.getLine());
            if(null == lbc) {
                lbc = new LinkedList<BranchCoverage>();
                result.put(bc.getLine(), lbc);
            }
            lbc.add(bc);
        }
        return result;
    }

    public Collection<BranchCoverage> getBranchCoverageFor(Integer line) {
        return branchCoverage.get(line);
    }

    public LineCoverage getLineCoverageFor(Integer line) {
        return lineCoverage.get(line);
    }

    public Collection<Integer> getLines() {
        Set<Integer> allLines = new HashSet<Integer>();
        allLines.addAll(lineCoverage.keySet());
        allLines.addAll(branchCoverage.keySet());
        return allLines;
    }

    public String getFilename() {
        return filename;
    }

    public int getLinesValid() {
        int sum = 0;
        for(LineCoverage lc : allLineCoverage) {
            sum += lc.getLinesValid();
        }
        return sum;
    }

    public int getBranchesValid() {
        int sum = 0;
        for(BranchCoverage bc : allBranchCoverage) {
            sum += bc.getBranchesValid();
        }
        return sum;
    }

    public int getLinesCovered() {
        int sum = 0;
        for(LineCoverage lc : allLineCoverage) {
            sum += lc.getLinesCovered();
        }
        return sum;
    }

    public int getBranchesCovered() {
        int sum = 0;
        for(BranchCoverage bc : allBranchCoverage) {
            sum += bc.getBranchesCovered();
        }
        return sum;
    }

    public double getLineRate() {
        return getLinesCovered() / (double)getLinesValid();
    }

    public double getBranchRate() {
        return getBranchesCovered() / (double)getBranchesValid();
    }
}
