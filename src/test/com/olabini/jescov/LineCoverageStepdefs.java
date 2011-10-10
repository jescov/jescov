/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.olabini.jescov.matchers.LineCoverageMatcher.hasLineCoverage;
import static com.olabini.jescov.matchers.LineCoverageMatcher.haveBlankLineCoverage;

import cucumber.annotation.en.Then;

import java.util.List;

public class LineCoverageStepdefs {
    private CucumberData data;

    public LineCoverageStepdefs(CucumberData data) {
        this.data = data;
    }

    @Then("^the line coverage on line (\\d+) should be (\\d+)$")
    public void the_line_coverage_on_line_should_be_(int line, int expectedCoverage) {
        assertThat(data.getCoverageData(), hasLineCoverage(expectedCoverage).onLine(line));
    }

    @Then("^the line coverage on line (\\d+) should be blank$")
    public void the_line_coverage_on_line_should_be_blank(int line) {
        assertThat(data.getCoverageData(), haveBlankLineCoverage().onLine(line));
    }


    public static class LineExpectation {
        public String coverage;
        public String code;
    }

    @Then("^the line coverage is this:$")
    public void the_line_coverage_is_this(List<LineExpectation> expectations) {
        CoverageData cd = data.getCoverageData();
        int line = 1;
        for(LineExpectation le : expectations) {
            if(le.coverage.trim().length() == 0) {
                assertThat(cd, haveBlankLineCoverage().onLine(line++));
            } else {
                assertThat(cd, hasLineCoverage(Integer.parseInt(le.coverage)).onLine(line++));
            }
        }
    }

    @Then("^I get the following line coverage for the JavaScript:$")
    public void I_get_the_following_line_coverage_for_the_JavaScript_(List<LineExpectation> expectations) {
        JavaScriptStepdefs jss = new JavaScriptStepdefs(data);
        StringBuilder jsCode = new StringBuilder();
        for(LineExpectation le : expectations) {
            jsCode.append(le.code).append("\n");
        }
        jss.I_execute_this_JavaScript(jsCode.toString());
        the_line_coverage_is_this(expectations);
    }
}// LineCoverageStepdefs
