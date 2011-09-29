package com.olabini.jescov.matchers;

import com.olabini.jescov.BranchCoverage;
import com.olabini.jescov.CoverageData;
import com.olabini.jescov.console.Runner;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

public class BranchCoverageMatcher extends TypeSafeMatcher<String> {
    private final int negativeExpected;
    private final int positiveExpected;
    private final Integer line;

    public BranchCoverageMatcher(int negativeExpected, int positiveExpected) {
        this(negativeExpected, positiveExpected, null);
    }

    public BranchCoverageMatcher(int negativeExpected, int positiveExpected, Integer line) {
        this.negativeExpected = negativeExpected;
        this.positiveExpected = positiveExpected;
        this.line = line;
    }

    private Collection<BranchCoverage> bcs;

    @Override
    protected boolean matchesSafely(String sourceCode) {
        Runner r = new Runner();
        r.executeSource("<test input>", sourceCode);
        CoverageData cd = r.done();
        bcs = cd.getFileCoverageFor("<test input>").getBranchCoverageFor(line);
        for(BranchCoverage bc : bcs) {
            if(bc.getNegativeBranch() == negativeExpected && bc.getPositiveBranch() == positiveExpected) {
                return true;
            }
        }

        return false;
    }

    public void describeTo(Description description) {
        description.appendText("didn't have branch coverage[negative=" + negativeExpected +", positive=" + positiveExpected + "] on line " + line);
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was " + bcs);
    }

    public BranchCoverageMatcher onLine(int line) {
        return new BranchCoverageMatcher(negativeExpected, positiveExpected, line);
    }

    public static BranchCoverageMatcher hasBranchCoverage(int negative, int positive) {
        return new BranchCoverageMatcher(negative, positive);
    }
}
