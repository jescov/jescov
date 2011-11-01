package com.olabini.jescov.matchers;

import com.olabini.jescov.BranchCoverage;
import com.olabini.jescov.CoverageData;
import com.olabini.jescov.console.Runner;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

public class BranchCoverageMatcher extends TypeSafeMatcher<CoverageData> {
    private final int expected;
    private final boolean binary;
    private final Integer axis;
    private final Integer line;

    public BranchCoverageMatcher(int expected, boolean binary, Integer axis, Integer line) {
        this.expected = expected;
        this.binary = binary;
        this.line = line;
        this.axis = axis;
    }

    private Collection<BranchCoverage> bcs;

    @Override
    protected boolean matchesSafely(CoverageData cd) {
        bcs = cd.getFileCoverageFor("<test input>").getBranchCoverageFor(line);

        if(bcs != null) {
            for(BranchCoverage bc : bcs) {
                if(bc.getBranches()[axis] == expected) {
                    return true;
                }
            }
        }

        return false;
    }

    public void describeTo(Description description) {
        String type = binary ? (0 == axis.intValue() ? "positive" : "negative") : "axis " + axis;
        description.appendText("didn't have branch coverage[" + type + "=" + expected +"] on line " + line);
    }

    @Override
    protected void describeMismatchSafely(CoverageData cd, Description mismatchDescription) {
        mismatchDescription.appendText("was " + bcs);
    }

    public BranchCoverageMatcher onLine(int line) {
        return new BranchCoverageMatcher(expected, binary, axis, line);
    }

    public BranchCoverageMatcher onAxis(int axis) {
        return new BranchCoverageMatcher(expected, false, axis, line);
    }

    public static BranchCoverageMatcher hasBranchCoverage(int expected) {
        return new BranchCoverageMatcher(expected, false, null, null);
    }

    public static BranchCoverageMatcher hasNegativeBranchCoverage(int expected) {
        return new BranchCoverageMatcher(expected, true, 0, null);
    }

    public static BranchCoverageMatcher hasPositiveBranchCoverage(int expected) {
        return new BranchCoverageMatcher(expected, true, 1, null);
    }
}
