package com.olabini.jescov.matchers;

import com.olabini.jescov.BranchCoverage;
import com.olabini.jescov.CoverageData;
import com.olabini.jescov.console.Runner;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

public class BranchCoverageMatcher extends TypeSafeMatcher<CoverageData> {
    private final int expected;
    private final boolean positive;
    private final Integer line;

    public BranchCoverageMatcher(int expected, boolean positive) {
        this(expected, positive, null);
    }

    public BranchCoverageMatcher(int expected, boolean positive, Integer line) {
        this.expected = expected;
        this.positive = positive;
        this.line = line;
    }

    private Collection<BranchCoverage> bcs;

    @Override
    protected boolean matchesSafely(CoverageData cd) {
        bcs = cd.getFileCoverageFor("<test input>").getBranchCoverageFor(line);

        if(bcs != null) {
            for(BranchCoverage bc : bcs) {
                if((positive ? bc.getPositiveBranch() : bc.getNegativeBranch()) == expected) {
                    return true;
                }
            }
        }

        return false;
    }

    public void describeTo(Description description) {
        String type = positive ? "positive" : "negative";
        description.appendText("didn't have branch coverage[" + type + "=" + expected +"] on line " + line);
    }

    @Override
    protected void describeMismatchSafely(CoverageData cd, Description mismatchDescription) {
        mismatchDescription.appendText("was " + bcs);
    }

    public BranchCoverageMatcher onLine(int line) {
        return new BranchCoverageMatcher(expected, positive, line);
    }

    public static BranchCoverageMatcher hasNegativeBranchCoverage(int expected) {
        return new BranchCoverageMatcher(expected, false);
    }

    public static BranchCoverageMatcher hasPositiveBranchCoverage(int expected) {
        return new BranchCoverageMatcher(expected, true);
    }
}
