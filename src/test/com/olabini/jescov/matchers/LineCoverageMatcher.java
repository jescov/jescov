/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.matchers;

import com.olabini.jescov.LineCoverage;
import com.olabini.jescov.CoverageData;
import com.olabini.jescov.console.Runner;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class LineCoverageMatcher extends TypeSafeMatcher<CoverageData> {
    private final int expected;
    private final Integer line;

    public LineCoverageMatcher(int expected) {
        this(expected, null);
    }

    public LineCoverageMatcher(int expected, Integer line) {
        this.expected = expected;
        this.line = line;
    }

    private LineCoverage lc;

    @Override
    protected boolean matchesSafely(CoverageData cd) {
        lc = cd.getFileCoverageFor("<test input>").getLineCoverageFor(line);
        if(expected == -1) {
            return lc == null;
        } else {
            return lc != null && lc.getHits() == expected;
        }
    }

    public void describeTo(Description description) {
        if(expected == -1) {
            description.appendText("didn't have blank line coverage on line " + line);
        } else {
            description.appendText("didn't have line coverage="+expected+" on line " + line);
        }
    }

    @Override
    protected void describeMismatchSafely(CoverageData cd, Description mismatchDescription) {
        String was = lc == null ? "blank" : String.valueOf(lc.getHits());
        mismatchDescription.appendText("was " + was);
    }

    public LineCoverageMatcher onLine(int line) {
        return new LineCoverageMatcher(expected, line);
    }

    public static LineCoverageMatcher hasLineCoverage(int expected) {
        return new LineCoverageMatcher(expected);
    }

    public static LineCoverageMatcher haveBlankLineCoverage() {
        return new LineCoverageMatcher(-1);
    }
}// LineCoverageMatcher
